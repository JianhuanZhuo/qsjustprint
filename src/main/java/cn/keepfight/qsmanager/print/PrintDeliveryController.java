package cn.keepfight.qsmanager.print;

import cn.keepfight.qsmanager.PropertiesServer;
import cn.keepfight.qsmanager.just.DeliveryItem;
import cn.keepfight.qsmanager.just.DeliveryItemModel;
import cn.keepfight.qsmanager.just.DeliveryPrintModel;
import cn.keepfight.qsmanager.just.PrintPaneController;
import cn.keepfight.utils.EditCell;
import cn.keepfight.utils.FXWidgetUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 送货单表格打印控制器
 * Created by tom on 2017/6/23.
 */
public class PrintDeliveryController extends PrintPaneController<DeliveryPrintModel> {

    public VBox root;

    public TextField head;
    public TextField my_addr;
    public TextField my_phone;

    public TextField serial;
    public TextField cust;
    public TextField addr;
    public TextField phone;
    public TextField contract;
    public TextField maker;
    public TextField ddate;
    public TextField all_total;

    public TableView<DeliveryItem> table;
    public TableColumn<DeliveryItem, Number> id;
    public TableColumn<DeliveryItem, String> name;
    public TableColumn<DeliveryItem, String> detail;
    public TableColumn<DeliveryItem, String> unit;
    public TableColumn<DeliveryItem, BigDecimal> num;
    public TableColumn<DeliveryItem, BigDecimal> price;
    public TableColumn<DeliveryItem, BigDecimal> total;
    public TableColumn<DeliveryItem, String> note;

    private static final int SIZE_PER_PAGE = 8;
    private long stamp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.fixedCellSizeProperty().bind(table.heightProperty().subtract(27).divide(SIZE_PER_PAGE));

        id.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getTableView().getItems().indexOf(param.getValue()) + 1));

        table.setEditable(true);
        total.setEditable(false);
        table.getColumns().forEach(x -> x.setSortable(false));
        FXWidgetUtil.cellDecimal(total);

        EditCell.cell(name, DeliveryItem::setProduct, EditCell.strConv());
        EditCell.cell(detail, DeliveryItem::setDetail, EditCell.strConv());
        EditCell.cell(unit, DeliveryItem::setUnit, EditCell.strConv());
        EditCell.cell(note, DeliveryItem::setNote, EditCell.strConv());

        EditCell.cell(price, DeliveryItem::setPrice, EditCell.DeciConv());
        EditCell.cell(num, DeliveryItem::setNum, EditCell.DeciConv());

        price.setCellValueFactory(x -> x.getValue().priceProperty());
        num.setCellValueFactory(x -> x.getValue().numProperty());
        total.setCellValueFactory(x -> x.getValue().totalItemProperty());

        FXWidgetUtil.connect(name, DeliveryItem::productProperty);
        FXWidgetUtil.connect(detail, DeliveryItem::detailProperty);
        FXWidgetUtil.connect(unit, DeliveryItem::unitProperty);
        FXWidgetUtil.connect(note, DeliveryItem::noteProperty);

        table.getItems().setAll(IntStream.range(0, SIZE_PER_PAGE).mapToObj(x -> new DeliveryItem()).collect(Collectors.toList()));

        FXWidgetUtil.calculate(table.getItems(), DeliveryItem::getTotalItem, all_total::setText);
    }

    public Node getRoot() {
        return root;
    }

    @Override
    public String getTitle() {
        return "普通送货单 二分联";
    }

    @Override
    public DeliveryPrintModel pack() {
        DeliveryPrintModel model = new DeliveryPrintModel();
        model.setHead(head.getText());
        model.setMy_addr(my_addr.getText());
        model.setMy_phone(my_phone.getText());

        model.setSerial(serial.getText());
        model.setCust_name(cust.getText());
        model.setCust_addr(addr.getText());
        model.setCust_phone(phone.getText());
        model.setCust_contract(contract.getText());
        model.setMaker(maker.getText());
        model.setDate_delivery(ddate.getText());

        model.setType(QSPrintType.DELIVERY);

        model.setStamp(stamp, false);

        model.getItems().addAll(table.getItems().stream()
                .map(DeliveryItem::cloneItem)
                .map(DeliveryItem::toItem)
                .collect(Collectors.toList()));

        return model;
    }

    @Override
    public DeliveryPrintModel newBill() {
        DeliveryPrintModel model = new DeliveryPrintModel();
        model.setSerial(PropertiesServer.getInstance().getNumStr());
        model.setStamp(System.currentTimeMillis(), true);
        model.setType(QSPrintType.DELIVERY);
        return model;
    }

    @Override
    public void fill(DeliveryPrintModel data) {
        head.setText(data.getHead());
        my_addr.setText(data.getMy_addr());
        my_phone.setText(data.getMy_phone());

        serial.setText(data.getSerial());
        cust.setText(data.getCust_name());
        addr.setText(data.getCust_addr());
        phone.setText(data.getCust_phone());
        contract.setText(data.getCust_contract());
        maker.setText(data.getMaker());
        ddate.setText(data.getDate_delivery());

        this.stamp = data.getStamp();

        // 添加为表格
        List<DeliveryItem> items = data.getItems().stream()
                .map(DeliveryItemModel::toItem)
                .collect(Collectors.toList());
        int k = SIZE_PER_PAGE-items.size();
        for (int i=0; i<k; i++){
            items.add(new DeliveryItem());
        }
        table.getItems().setAll(items);
    }
}
