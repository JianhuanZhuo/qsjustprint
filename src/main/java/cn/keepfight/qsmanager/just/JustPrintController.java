package cn.keepfight.qsmanager.just;

import cn.keepfight.qsmanager.BillListServer;
import cn.keepfight.qsmanager.print.QSPrintType;
import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.FXWidgetUtil;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 秋霞打印软件
 * Created by tom on 2017/9/13.
 */
public class JustPrintController implements Initializable {
    public HBox root;

    public TextField text_search;
    public Button btn_search;
    public Button btn_export;
    public Button btn_import;
    public Button btn_new;
    public Button btn_copy;
    public ListView<Printable> list_printable;

    public ChoiceBox<Printer> sel_printer;
    public Label lab_hit;
    public Button btn_print;
    public ScrollPane printScrollPane;
    public Button btn_del;
    public Button btn_reset;
    public Button btn_save;

    /**
     * 打印中标志位
     */
    private static AtomicBoolean busy = new AtomicBoolean(false);
    private static BooleanProperty printing = new SimpleBooleanProperty(false);
    private ObjectProperty<QSPrintType> typeProperty = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化打印类型下拉列表
        initPrinterList();

        list_printable.setCellFactory(param -> new ListCell<Printable>() {
            @Override
            protected void updateItem(Printable item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getTitle());
                }
            }
        });
        list_printable.setItems(BillListServer.getInstance().getPrintableList());
        list_printable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Printable>() {
            @Override
            public void changed(ObservableValue<? extends Printable> observable, Printable oldValue, Printable newValue) {
                if (newValue!=null){
                    loadData(newValue);
                }
            }
        });

        // 打印按钮使能绑定
        btn_print.disableProperty().bind(printScrollPane.contentProperty().isNull()
                .or(sel_printer.getSelectionModel().selectedItemProperty().isNull())
                .or(lab_hit.textProperty().isNotEqualTo(""))
                .or(typeProperty.isNull())
                .or(printing));

        // 打印动作
        btn_print.setOnAction(event -> printAction());

        btn_new.setOnAction(event -> newBill());

        checkSupport();
    }

    public void changePrintType(QSPrintType type) {
        typeProperty.set(type);
        printScrollPane.setContent(type.getController().getRoot());
    }

    public void loadData(Printable data) {
        changePrintType(data.getType());
        data.getType().getController().fill(data);
    }

    /**
     * 初始化打印机选择列表
     */
    private void initPrinterList() {
        sel_printer.disableProperty().bind(printing);
        sel_printer.setConverter(FXUtils.converter(Printer::getName));
        sel_printer.getItems().setAll(Printer.getAllPrinters());
        Printer defaultPrinter = Printer.getDefaultPrinter();
        if (defaultPrinter != null) {
            sel_printer.getSelectionModel().select(defaultPrinter);
        }
        sel_printer.setOnAction(event -> checkSupport());
    }

    /**
     * 检查打印机和所需打印件类型的支持情况，如果不支持那么就该给予提示
     *
     * @return 返回 true 表示全部支持
     */
    private boolean checkSupport() {
        // 初始化为空字符串
        lab_hit.setText("");
        Printer printer = sel_printer.getSelectionModel().getSelectedItem();
        if (printer == null) {
            lab_hit.setText("请选择打印机！");
            return false;
        }
        return true;
    }

    private void printAction() {
        // 防多次重入
        if (busy.getAndSet(true)) {
            return;
        }
        printing.set(true);
        btn_print.setText("打印中");

        // 获得打印数据与参数
        Printer printer = sel_printer.getValue();
        QSPrintType type = typeProperty.get();
        Node rootNode = type.getController().getRoot();

        // 转换为打印截图样式
        FXUtils.addStyle("snap", rootNode);
        PageLayout pageLayout = printer.createPageLayout(
                type.getPaper(),
                type.getOrientation(),
                type.getMarginReqire(), type.getMarginReqire(), type.getMarginReqire(), type.getMarginReqire());

        new Thread(() -> {
            try {
                // 使用新线程以保证可以延迟一段时间进行打印，这样节点就有时间可以正确渲染了
                Thread.sleep(1000);
                // 使用Platfor.runLater 是为了保证在打印完成前，不会先去除样式
                Platform.runLater(() -> {
                    try {
                        FXWidgetUtil.printNode(rootNode, printer, pageLayout);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Platform.runLater(() -> FXUtils.delStyle("snap", rootNode));
                        printing.set(false);
                        busy.set(false);
                        btn_print.setText("打印");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void newBill() {
        List<QSPrintType> choices = Arrays.asList(QSPrintType.values());
        ChoiceDialog<QSPrintType> dialog = new ChoiceDialog<>(QSPrintType.RECEIPT_P, choices);
        dialog.setHeaderText("新增打印的单子");
        dialog.setContentText("请选择新增单子的类型：");

        Optional<QSPrintType> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            //@TODO 待刷新
            BillListServer.getInstance().addPritable(letter.getController().newBill());
        });
    }
}
