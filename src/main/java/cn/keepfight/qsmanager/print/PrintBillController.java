package cn.keepfight.qsmanager.print;

import cn.keepfight.qsmanager.PropertiesServer;
import cn.keepfight.qsmanager.just.DeliveryItem;
import cn.keepfight.qsmanager.just.DeliveryPrintModel;
import cn.keepfight.qsmanager.just.PrintPaneController;
import cn.keepfight.qsmanager.just.ReceiptPrintModel;
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
public class PrintBillController extends PrintPaneController<ReceiptPrintModel> {

    @FXML
    private VBox root;

    public TextField head;

    @FXML
    private TextField serial;
    @FXML
    private TextField mdate;

    public TextField line1;
    public TextField line2;
    public TextField line3;

    public TextField text_supper;
    @FXML
    private TextField text_total;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public String getTitle() {
        return "专用收据 三分联";
    }

    @Override
    public ReceiptPrintModel pack() {
        return null;
    }

    @Override
    public ReceiptPrintModel newBill() {
        ReceiptPrintModel model = new ReceiptPrintModel();
        model.setSerial(PropertiesServer.getInstance().getNumStr());
        model.setStamp(System.currentTimeMillis());
        return model;
    }

    @Override
    public void fill(ReceiptPrintModel data) {
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
