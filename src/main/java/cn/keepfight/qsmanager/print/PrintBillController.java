package cn.keepfight.qsmanager.print;

import cn.keepfight.qsmanager.PropertiesServer;
import cn.keepfight.qsmanager.just.DeliveryItem;
import cn.keepfight.qsmanager.just.DeliveryPrintModel;
import cn.keepfight.qsmanager.just.PrintPaneController;
import cn.keepfight.qsmanager.just.ReceiptPrintModel;
import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.FXWidgetUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private BigDecimal total;
    private long stamp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text_total.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                total = new BigDecimal(newValue);
                text_supper.setText(FXUtils.getSupperNum(total));
            }catch (Exception e){
                text_supper.setText(FXUtils.getSupperNum(new BigDecimal(0)));
            }
        });
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
        ReceiptPrintModel model = new ReceiptPrintModel();
        model.setHead(head.getText());
        model.setSerial(serial.getText());
        model.setStamp(stamp, false);
        model.setDate(mdate.getText());
        model.setLine1(line1.getText());
        model.setLine2(line2.getText());
        model.setLine3(line3.getText());
        model.setTotal(total.movePointRight(2).intValue());
        return model;
    }

    @Override
    public ReceiptPrintModel newBill() {
        ReceiptPrintModel model = new ReceiptPrintModel();
        model.setSerial(PropertiesServer.getInstance().getNumStr());
        model.setStamp(System.currentTimeMillis(), true);
        return model;
    }

    @Override
    public void fill(ReceiptPrintModel data) {
        head.setText(data.getHead());
        mdate.setText(data.getDate());
        serial.setText(data.getSerial());
        line1.setText(data.getLine1());
        line2.setText(data.getLine2());
        line3.setText(data.getLine3());
        text_total.setText(new BigDecimal(data.getTotal()).movePointLeft(2).toPlainString());

        stamp = data.getStamp();
    }
}
