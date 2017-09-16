package cn.keepfight.qsmanager.just;

import cn.keepfight.qsmanager.BillListServer;
import cn.keepfight.qsmanager.QSJustPrint;
import cn.keepfight.qsmanager.print.QSPrintType;
import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.FXWidgetUtil;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 秋霞打印软件
 * Created by tom on 2017/9/13.
 */
public class JustPrintController implements Initializable {
    public HBox root;

    public TextField text_search;
    public Button btn_search;
    public Button btn_all;
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
                setText("");
                if (item != null && !empty) {
                    setText(item.getTitle());
                }
            }
        });
        list_printable.setItems(BillListServer.getInstance().getPrintableList());
        list_printable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                loadData(newValue);
            }
        });

        // 打印按钮使能绑定
        btn_print.disableProperty().bind(printScrollPane.contentProperty().isNull()
                .or(sel_printer.getSelectionModel().selectedItemProperty().isNull())
                .or(lab_hit.textProperty().isNotEqualTo(""))
                .or(typeProperty.isNull())
                .or(printing));

        btn_save.disableProperty().bind(printScrollPane.contentProperty().isNull()
                .or(typeProperty.isNull())
                .or(printing)
                .or(list_printable.getSelectionModel().selectedItemProperty().isNull()));

        btn_del.disableProperty().bind(list_printable.getSelectionModel().selectedItemProperty().isNull());
        btn_copy.disableProperty().bind(list_printable.getSelectionModel().selectedItemProperty().isNull());
        btn_reset.disableProperty().bind(list_printable.getSelectionModel().selectedItemProperty().isNull());
        btn_search.disableProperty().bind(text_search.textProperty().isEmpty());

        // 打印动作
        btn_print.setOnAction(event -> printAction());
        btn_new.setOnAction(event -> newBill());
        btn_save.setOnAction(event -> {
            Printable p = typeProperty.get().getController().pack();
            BillListServer.getInstance().replacePrintable(list_printable.getSelectionModel().getSelectedItem(), p);
            list_printable.getSelectionModel().select(p);
        });

        btn_del.setOnAction(event -> BillListServer.getInstance().removePrintable(list_printable.getSelectionModel().getSelectedItem()));
        btn_copy.setOnAction(event -> BillListServer.getInstance().addPritable(list_printable.getSelectionModel().getSelectedItem().cloneBill()));
        btn_reset.setOnAction(event -> loadData(list_printable.getSelectionModel().getSelectedItem()));
        btn_search.setOnAction(event -> searchFor(text_search.getText()));
        text_search.setOnAction(event -> searchFor(text_search.getText()));
        btn_all.setOnAction(event -> list_printable.setItems(BillListServer.getInstance().getPrintableList()));
        btn_export.setOnAction(event -> saveAs());
        btn_import.setOnAction(event -> recover());

        checkSupport();
    }

    /**
     * 加载单子
     */
    public void loadData(Printable data) {
        typeProperty.set(data.getType());
        printScrollPane.setContent(data.getType().getController().getRoot());
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
        rootNode.requestFocus();

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
                        FXWidgetUtil.printNodeNew(rootNode, printer, pageLayout, type.getController().getFolder());
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
            Printable p = letter.getController().newBill();
            System.out.println("letter.getController().newBill:"+p.getType());
            BillListServer.getInstance().addPritable(p);
            list_printable.getSelectionModel().select(p);
        });

    }
    private void searchFor(String s){
        list_printable.setItems(new FilteredList<>(BillListServer.getInstance().getPrintableList(), (x) -> x.lookup(s)));
    }

    /**
     * 打印记录另存为
     */
    private void saveAs(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择要备份到哪个目录下");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("打印记录备份文件 (*.objs)", "*.objs"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fileChooser.setInitialFileName("打印记录备份.objs");
        File backup = fileChooser.showSaveDialog(QSJustPrint.getPrimaryStage());
        if (backup!=null) {
            BillListServer.getInstance().storeToFile(backup);
        }
    }

    /**
     * 打印记录另存为
     */
    private void recover(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择要需要恢复的备份");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("打印记录备份文件 (*.objs)", "*.objs"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fileChooser.setInitialFileName("打印记录备份.objs");
        File backup = fileChooser.showOpenDialog(QSJustPrint.getPrimaryStage());
        if (backup!=null) {
            BillListServer.getInstance().loadFromFile(backup);
        }
    }
}
