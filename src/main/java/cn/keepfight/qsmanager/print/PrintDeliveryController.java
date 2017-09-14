package cn.keepfight.qsmanager.print;

import cn.keepfight.qsmanager.PropertiesServer;
import cn.keepfight.qsmanager.just.DeliveryItem;
import cn.keepfight.qsmanager.just.DeliveryPrintModel;
import cn.keepfight.qsmanager.just.PrintPaneController;
import cn.keepfight.qsmanager.model.*;
import cn.keepfight.utils.*;
import javafx.beans.property.*;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 送货单表格打印控制器
 * Created by tom on 2017/6/23.
 */
public class PrintDeliveryController extends PrintPaneController<DeliveryPrintModel> {

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

    public TableView<DeliveryItem> table;
    public TableColumn<DeliveryItem, Number> id;
    public TableColumn<DeliveryItem, String> name;
    public TableColumn<DeliveryItem, String> detail;
    public TableColumn<DeliveryItem, String> unit;
    public TableColumn<DeliveryItem, BigDecimal> num;
    public TableColumn<DeliveryItem, BigDecimal> price;
    public TableColumn<DeliveryItem, BigDecimal> total;
    public TableColumn<DeliveryItem, String> note;
    public VBox root;
    public TextField all_total;

    private static final int SIZE_PER_PAGE = 8;
    private static Properties ps = PropertiesServer.getInstance().getPS();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.fixedCellSizeProperty().bind(table.heightProperty().subtract(27).divide(SIZE_PER_PAGE));

        id.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getTableView().getItems().indexOf(param.getValue()) + 1));

        table.setEditable(true);
        total.setEditable(false);
        table.getColumns().forEach(x->x.setSortable(false));
        FXWidgetUtil.cellStr(name, detail, unit, note);
        FXWidgetUtil.cellDecimal(price, num, total);

        price.setCellValueFactory(x -> x.getValue().priceProperty());
        num.setCellValueFactory(x -> x.getValue().numProperty());
        total.setCellValueFactory(x -> x.getValue().totalItemProperty());

        FXWidgetUtil.connect(name, DeliveryItem::productProperty);
        FXWidgetUtil.connect(detail, DeliveryItem::detailProperty);
        FXWidgetUtil.connect(unit, DeliveryItem::unitProperty);
        FXWidgetUtil.connect(note, DeliveryItem::noteProperty);

        table.getItems().setAll(IntStream.range(0, SIZE_PER_PAGE).mapToObj(x->new DeliveryItem()).collect(Collectors.toList()));
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
        return null;
    }

    @Override
    public DeliveryPrintModel newBill() {
        DeliveryPrintModel model = new DeliveryPrintModel();
        model.setSerial(PropertiesServer.getInstance().getNumStr());
        model.setStamp(System.currentTimeMillis());
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


        // 添加为表格
        List<DeliveryItem> items = data.getItems();
        for (int i=0; i<SIZE_PER_PAGE-items.size(); i++){
            items.add(new DeliveryItem());
        }
        table.getItems().setAll(items);
    }
}
