package cn.keepfight.qsmanager.print;

import cn.keepfight.qsmanager.PropertiesServer;
import cn.keepfight.qsmanager.just.DeliveryItem;
import cn.keepfight.qsmanager.just.DeliveryPrintModel;
import cn.keepfight.qsmanager.just.PrintPaneController;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.fixedCellSizeProperty().bind(table.heightProperty().subtract(51).divide(SIZE_PER_PAGE));

        id.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getTableView().getItems().indexOf(param.getValue()) + 1));

        table.setEditable(true);
        table.getColumns().forEach(x -> x.setSortable(false));
        FXWidgetUtil.cellStr(detail, unit, name);
        FXWidgetUtil.cellDecimal(price, num);

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
        return null;
    }

    @Override
    public DeliveryPrintModel newBill() {
        DeliveryPrintModel model = new DeliveryPrintModel();
        model.setSerial(PropertiesServer.getInstance().getNumStr());
        model.setStamp(System.currentTimeMillis());
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


        // 添加为表格
        List<DeliveryItem> items = data.getItems();
        for (int i=0; i<SIZE_PER_PAGE-items.size(); i++){
            items.add(new DeliveryItem());
        }
        table.getItems().setAll(items);
    }

//    @Override
//    public void fill(OrderModelFull datas) {
//        this.datas = datas;
//
//        serial.setText(datas.getSerial());
//
//        // 填充客户信息
//        try {
//            CustomModel c = QSApp.service.getCustomService().selectAllByID(datas.getCid());
//            cust.setText(c.getNamefull());
//            addr.setText(c.getAddr());
//            phone.setText(c.getPhone());
//
//            // 加载默认记忆选项并添加默认下拉
//            FXWidgetUtil.defaultList(
//                    new Pair<>(addr, "custom.info.addr."+c.getSerial())
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 填写其他信息
//        maker.setText(ConfigUtil.load("fxapp.properties").getProperty("print.maker"));
//        mdate.setText(FXUtils.stampToDate(System.currentTimeMillis()));
//
//        // 添加为表格
//        List<DeliveryItem> items = datas.getOrderItemModels().stream().map(DeliveryItem::new).collect(Collectors.toList());
//        for (int i=0; i<SIZE_PER_PAGE-datas.getOrderItemModels().size(); i++){
//            items.add(new DeliveryItem());
//        }
//        table.getItems().setAll(items);
//
//        // 添加表格可选的菜单下拉
//        try {
//            List<ProductModel> plist = QSApp.service.getOrderFavorService().selectAll(datas.getCid());
//            productList = plist
//                    .stream()
//                    .collect(Collectors.toMap(p->p.getSerial()+"-"+p.getName(), p->p));
//            List<String> ss = plist.stream().map(p->p.getSerial()+"-"+p.getName()).collect(Collectors.toList());
//            ss.add(null);
//            name.setCellFactory(ChoiceBoxTableCell.forTableColumn(ss.toArray(new String[ss.size()])));
//        } catch (Exception e) {
//            //@TODO 这里如果有毛病了，那么该怎么处理？
//            e.printStackTrace();
//            productList = new HashMap<>();
//        }
//    }
//    public void autoCalculate() {
//        Optional<BigDecimal> t = table.getItems().stream()
//                .peek(x -> {
//                    BigDecimal d = x.getTakeTotal();
//                    //@TODO 搞完回来弄
//                    x.yuan_u4.set(FXUtils.getNumAt(d, 5, true));
//                    x.yuan_u3.set(FXUtils.getNumAt(d, 4, true));
//                    x.yuan_u2.set(FXUtils.getNumAt(d, 3, true));
//                    x.yuan_u1.set(FXUtils.getNumAt(d, 2, true));
//                    x.yuan.set(FXUtils.getNumAt(d, 1, true));
//                    x.yuan_d1.set(FXUtils.getNumAt(d, 1, false));
//                    x.yuan_d2.set(FXUtils.getNumAt(d, 2, false));
//                })
//                .map(OrderItemModel::getTakeTotal)
//                .reduce(BigDecimal::add);
//        String text = "0";
//        if (t.isPresent()) {
//            text = t.get().stripTrailingZeros().toPlainString();
//        }
//        all_total.setText(text);
//    }
}
