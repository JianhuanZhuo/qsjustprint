package cn.keepfight.qsmanager.print;

import cn.keepfight.qsmanager.PropertiesServer;
import cn.keepfight.qsmanager.just.DeliveryItem;
import cn.keepfight.qsmanager.just.DeliveryItemModel;
import cn.keepfight.qsmanager.just.DeliveryPrintModel;
import cn.keepfight.qsmanager.just.PrintPaneController;
import cn.keepfight.utils.EditCell;
import cn.keepfight.utils.FXWidgetUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
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

/**
 * 收据单表格打印控制器
 * Created by tom on 2017/6/23.
 */
public class PrintReceiptController extends PrintPaneController<DeliveryPrintModel> {

    @FXML
    private VBox root;

    public TextField head;
    public TextField my_addr;
    public TextField my_phone;

    @FXML
    private TextField serial;
    @FXML
    private TextField cust;
    @FXML
    private TextField addr;
    @FXML
    private TextField phone;
//    @FXML private TextField maker;
    @FXML
    private TextField mdate;

    @FXML
    private TableView<DeliveryItem> table;
    @FXML
    private TableColumn<DeliveryItem, Number> id;
    @FXML
    private TableColumn<DeliveryItem, String> name;
    @FXML
    private TableColumn<DeliveryItem, String> detail;
    @FXML
    private TableColumn<DeliveryItem, String> unit;
    @FXML
    private TableColumn<DeliveryItem, BigDecimal> num;
    @FXML
    private TableColumn<DeliveryItem, BigDecimal> price;
    @FXML
    private TableColumn<DeliveryItem, Integer> yuan_u4;
    @FXML
    private TableColumn<DeliveryItem, Integer> yuan_u3;
    @FXML
    private TableColumn<DeliveryItem, Integer> yuan_u2;
    @FXML
    private TableColumn<DeliveryItem, Integer> yuan_u1;
    @FXML
    private TableColumn<DeliveryItem, Integer> yuan;
    @FXML
    private TableColumn<DeliveryItem, Integer> yuan_d1;
    @FXML
    private TableColumn<DeliveryItem, Integer> yuan_d2;
    @FXML
    private TextField all_total;

    private static final int SIZE_PER_PAGE = 8;
    private long stamp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.fixedCellSizeProperty().bind(table.heightProperty().subtract(51).divide(SIZE_PER_PAGE));

        id.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getTableView().getItems().indexOf(param.getValue()) + 1));

        table.setEditable(true);
        table.getColumns().forEach(x -> x.setSortable(false));
        yuan_u4.setEditable(false);
        yuan_u3.setEditable(false);
        yuan_u2.setEditable(false);
        yuan_u1.setEditable(false);
        yuan.setEditable(false);
        yuan_d1.setEditable(false);
        yuan_d2.setEditable(false);

        EditCell.cell(name, DeliveryItem::setProduct, EditCell.strConv());
        EditCell.cell(detail, DeliveryItem::setDetail, EditCell.strConv());
        EditCell.cell(unit, DeliveryItem::setUnit, EditCell.strConv());

        EditCell.cell(price, DeliveryItem::setPrice, EditCell.DeciConv());
        EditCell.cell(num, DeliveryItem::setNum, EditCell.DeciConv());

        name.setCellValueFactory(x -> x.getValue().productProperty());
        FXWidgetUtil.connect(detail, DeliveryItem::detailProperty);
        FXWidgetUtil.connect(unit, DeliveryItem::unitProperty);
        num.setCellValueFactory(x -> x.getValue().numProperty());
        price.setCellValueFactory(x -> x.getValue().priceProperty());

        yuan_u4.setCellValueFactory(x -> x.getValue().yuan_u4Property().asObject());
        yuan_u3.setCellValueFactory(x -> x.getValue().yuan_u3Property().asObject());
        yuan_u2.setCellValueFactory(x -> x.getValue().yuan_u2Property().asObject());
        yuan_u1.setCellValueFactory(x -> x.getValue().yuan_u1Property().asObject());
        yuan.setCellValueFactory(x -> x.getValue().yuanProperty().asObject());
        yuan_d1.setCellValueFactory(x -> x.getValue().yuan_d1Property().asObject());
        yuan_d2.setCellValueFactory(x -> x.getValue().yuan_d2Property().asObject());

        FXWidgetUtil.calculate(table.getItems(), DeliveryItem::getTotalItem, all_total::setText);
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public String getTitle() {
        return "收款收据 二分联";
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
//        model.setCust_contract(contract.getText());
//        model.setMaker(maker.getText());
        model.setDate_delivery(mdate.getText());

        model.setType(QSPrintType.RECEIPT);

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
        model.setType(QSPrintType.RECEIPT);
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
//        maker.setText(data.getMaker());
        mdate.setText(data.getDate_delivery());

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
