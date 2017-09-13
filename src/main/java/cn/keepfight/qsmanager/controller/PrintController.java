package cn.keepfight.qsmanager.controller;

import cn.keepfight.qsmanager.QSApp;
import cn.keepfight.utils.DialogContent;
import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.FXWidgetUtil;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * 打印界面控制器
 * <p>
 * 打印件类型有：
 * <li>
 * <ol>送货单</ol>
 * <ol>收据单</ol>
 * <ol>安利专用送货单</ol>
 * <ol>客户月对账单</ol>
 * <ol>客户年度对账单</ol>
 * <ol>供应商月对账单</ol>
 * <ol>供应商年度对账单</ol>
 * </li>
 * <p>
 * Created by tom on 2017/6/23.
 */
public class PrintController implements DialogContent<PrintSelection>, Initializable {
    @FXML
    private VBox root;

    @FXML
    private ChoiceBox<Printer> print_sel;
    //    @FXML
//    private Button set_layout;
//    @FXML
//    private Button set_print;
    @FXML
    private Label printHit;

    @FXML
    private ChoiceBox<QSPrintType> type_sel;
    @FXML
    private ChoiceBox<Pair<Long, String>> obj; // Long 为ID，String 为显示的名字
    @FXML
    private ChoiceBox<Long> year;
    @FXML
    private ChoiceBox<Long> month;
    @FXML
    private ChoiceBox<Pair<Long, String>> serial;
    //    @FXML private RadioButton edit;
//    @FXML private RadioButton preview;
//    @FXML private Button export_excel;
//    @FXML private Button export_pic;
    @FXML
    private Button prev;
    @FXML
    private Button next;
    @FXML
    private Button action;
    @FXML
    private Button compute;
//    @FXML
//    private Button reset;

    @FXML
    private ScrollPane printScrollPane;

    /**
     * 打印中标志位
     */
    private static AtomicBoolean busy = new AtomicBoolean(false);
    private static BooleanProperty printing = new SimpleBooleanProperty(false);
    private List<Pair<Long, String>> custList;
    private List<Pair<Long, String>> supList;

    private ObjectProperty<PrintTemplate> printController = new SimpleObjectProperty<>();
    private ObjectProperty<PrintSourceLocate> locate = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化打印类型下拉列表
        initPrintTypeList();

        initPrinterList();

        initLocate();
        // 打印按钮使能绑定
        action.disableProperty().bind(printScrollPane.contentProperty().isNull()
                .or(print_sel.getSelectionModel().selectedItemProperty().isNull())
                .or(type_sel.getSelectionModel().selectedItemProperty().isNull())
                .or(printHit.textProperty().isNotEqualTo(""))
                .or(printing));

        // 打印动作
        action.setOnAction(event -> printAction());
    }

    @Override
    public void init() {
        // 每次进来都初始化一次打印机列表，以更新
        print_sel.getItems().setAll(Printer.getAllPrinters());
        Printer defaultPrinter = Printer.getDefaultPrinter();
        if (defaultPrinter != null) {
            print_sel.getSelectionModel().select(defaultPrinter);
        }
        // 默认检查一次打印支持机制
        checkSupport();
    }

    @Override
    public void fill(PrintSelection s) {
        type_sel.getSelectionModel().select(s.getType());
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 非空时设置模板
        //
        type_sel.getSelectionModel().getSelectedItem().getController(c -> {
            printScrollPane.setContent(c.getRoot());
            printController.set(c);
            compute.disableProperty().bind(c.autoComputable().not());
            compute.setOnAction(e -> c.autoCalculate());
            prev.disableProperty().bind(c.hasPrev().not());
            next.disableProperty().bind(c.hasNext().not());


            prev.setOnAction(e -> c.prev());
            next.setOnAction(e -> c.next());
        });
        locate.set(type_sel.getValue().getLocate());
        //
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Platform.runLater(() -> s.getType().getController(c -> {
            try {
                c.fill(s.getType().getLocate().query(s.getPrintSource()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        // 清空选择，避免引起歧义
        year.getSelectionModel().clearSelection();
        month.getSelectionModel().clearSelection();
        obj.getSelectionModel().clearSelection();
        serial.getSelectionModel().clearSelection();
    }

    @Override
    public Node getContent() {
        return root;
    }

    @Override
    public BooleanProperty allValid() {
        return new SimpleBooleanProperty(false);
    }

    /**
     * 初始化打印机选择列表
     */
    private void initPrinterList() {
        print_sel.disableProperty().bind(printing);
        print_sel.setConverter(FXUtils.converter(Printer::getName));
        print_sel.getItems().setAll(Printer.getAllPrinters());
        Printer defaultPrinter = Printer.getDefaultPrinter();
        if (defaultPrinter != null) {
            print_sel.getSelectionModel().select(defaultPrinter);
        }
        print_sel.setOnAction(event -> checkSupport());
    }

    private void initLocate() {
        try {
            //@TODO 动态加载
            custList = QSApp.service.getCustomService().selectAll()
                    .stream()
                    .map(x -> new Pair<>(x.getId(), x.getSerial() + "-" + x.getName()))
                    .collect(Collectors.toList());
            supList = QSApp.service.getSupplyService().selectAll()
                    .stream()
                    .map(x -> new Pair<>(x.getId(), x.getSerial() + "-" + x.getName()))
                    .collect(Collectors.toList());
            Set<Long> years = new HashSet<>();
            years.addAll(QSApp.service.getOrderService().selectYear());
            years.addAll(QSApp.service.getReceiptService().selectYear());
            year.getItems().setAll(years);
        } catch (Exception e) {
            e.printStackTrace();
        }
        locate.addListener((ob, ov, nv) -> {
            if (nv == null) {
                obj.setDisable(true);
                year.setDisable(true);
                month.setDisable(true);
                serial.setDisable(true);
            } else {
                obj.setDisable(false);
                year.setDisable(false);
                month.setDisable(!nv.reqMon());
                serial.setDisable(!nv.reqSerial());
                if (ov == null || (nv.reqCust() != ov.reqCust())) {
                    // 相同则不设置
                    if (nv.reqCust()) {
                        obj.getItems().setAll(custList);
                    } else {
                        obj.getItems().setAll(supList);
                    }
                }
                serial.getItems().clear();
            }
        });
        obj.setDisable(true);
        year.setDisable(true);
        month.setDisable(true);
        serial.setDisable(true);

        obj.setConverter(FXUtils.converter(Pair::getValue, "选择客户"));
        year.setConverter(FXUtils.converter(x -> x + "年", "选择年份"));
        month.setItems(FXCollections.observableList(LongStream.range(1, 13).boxed().collect(Collectors.toList())));
        month.setConverter(FXUtils.converter(x -> x + "月", "全部月份"));
        serial.setConverter(FXUtils.converter(Pair::getValue, "选择编号"));

        obj.setOnAction(e -> {
            serial.getItems().clear();
            checkForDataLoad();
        });
        year.setOnAction(e -> {
            serial.getItems().clear();
            checkForDataLoad();
        });
        month.setOnAction(e -> {
            serial.getItems().clear();
            checkForDataLoad();
        });
        serial.setOnAction(e -> checkForDataLoad());
    }

    /**
     * 初始化打印类型列表
     */
    private void initPrintTypeList() {
        // 必须是打印机已选才可以有打印类型选择
        type_sel.disableProperty().bind(
                print_sel.getSelectionModel().selectedItemProperty().isNull()
                        .or(print_sel.disabledProperty())
                        .or(printing));

        type_sel.setConverter(FXUtils.converter(QSPrintType::getDesc));
        type_sel.getItems().setAll(QSPrintType.values());
        type_sel.setOnAction(event -> {
            if (checkSupport()) {
                // 非空时设置模板
                type_sel.getSelectionModel().getSelectedItem().getController(c -> {
                    printScrollPane.setContent(c.getRoot());
                    printController.set(c);
                    compute.disableProperty().bind(c.autoComputable().not());
                    compute.setOnAction(e -> c.autoCalculate());
                    prev.disableProperty().bind(c.hasPrev().not());
                    next.disableProperty().bind(c.hasNext().not());


                    prev.setOnAction(e -> c.prev());
                    next.setOnAction(e -> c.next());
                });
                locate.set(type_sel.getValue().getLocate());
            } else {
                printController.set(null);
                locate.set(null);
                // 上下页使能
                compute.disableProperty().unbind();
                compute.setOnAction(null);
                prev.disableProperty().unbind();
                next.disableProperty().unbind();
                prev.setDisable(true);
                next.setDisable(true);
                serial.getItems().clear();
                checkForDataLoad();
            }
        });
        compute.disableProperty().unbind();
        prev.disableProperty().unbind();
        next.disableProperty().unbind();
        // 上下页使能
        prev.setDisable(true);
        next.setDisable(true);
    }

    private void printAction() {
        // 防多次重入
        if (busy.getAndSet(true)) {
            return;
        }
        printing.set(true);
        action.setText("打印中");

        // 获得打印数据与参数
        QSPrintType type = type_sel.getValue();
        Printer printer = print_sel.getValue();
        Node rootNode = printController.get().getRoot();

        // 打印前调用
        type.getController(PrintTemplate::printBefore);

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
                        action.setText("执行打印");

                        // 打印后调用
                        type.getController(PrintTemplate::printAfter);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    /**
     * 检查打印机和所需打印件类型的支持情况，如果不支持那么就该给予提示
     *
     * @return 返回 true 表示全部支持
     */
    private boolean checkSupport() {
        // 初始化为空字符串
        printHit.setText("");
        Printer printer = print_sel.getSelectionModel().getSelectedItem();
        if (printer == null) {
            printHit.setText("请选择打印机！");
            return false;
        }
        QSPrintType printType = type_sel.getSelectionModel().getSelectedItem();
        if (printType == null) {
            printHit.setText("请选择打印件类型");
            return false;
        }
        PrinterAttributes att = printer.getPrinterAttributes();
        if (!att.getSupportedPapers().contains(printType.getPaper())) {
            printHit.setText("该打印机不支持所选打印件类型的纸大小：" + printType.getPaper());
            return false;
        }
        if (!att.getSupportedPageOrientations().contains(printType.getOrientation())) {
            printHit.setText("该打印机不支持所选打印件类型的纸朝向：" + printType.getOrientation());
            return false;
        }

        PageLayout pageLayout = printer.createPageLayout(printType.getPaper(),
                printType.getOrientation(),
                Printer.MarginType.HARDWARE_MINIMUM);
        double maxMargin = Math.max(
                Math.max(pageLayout.getTopMargin(), pageLayout.getBottomMargin()),
                Math.max(pageLayout.getRightMargin(), pageLayout.getLeftMargin()));
        if (maxMargin > printType.getMarginReqire()) {
            printHit.setText("该打印机的最小支持边距(" + maxMargin + ")大于所需边距(" + printType.getMarginReqire() + ")");
            return false;
        }
        return true;
    }


    private void checkForDataLoad() {
        PrintSource ps = new PrintSource();
        if (locate.get() == null) {
            return;
        }
        if ((locate.get().reqCust() || locate.get().reqSup())) {
            if (obj.getSelectionModel().isEmpty()) {
                return;
            }
            ps.setSup(obj.getSelectionModel().getSelectedItem().getKey());
            ps.setCust(obj.getSelectionModel().getSelectedItem().getKey());
        }
        if (locate.get().reqYear()) {
            if (year.getSelectionModel().isEmpty())
                return;
            ps.setYear(year.getSelectionModel().getSelectedItem());
        }
        if (locate.get().reqMon()) {
            if (month.getSelectionModel().isEmpty())
                return;
            ps.setMonth(month.getSelectionModel().getSelectedItem());
        }
        if (locate.get().reqSerial()) {
            if (serial.getSelectionModel().isEmpty()) {
                Long s_obj = obj.getSelectionModel().getSelectedItem().getKey();
                Long s_year = year.getSelectionModel().getSelectedItem();
                Long s_mon = month.getSelectionModel().getSelectedItem();
                try {
                    serial.getItems().setAll(locate.get().getSerialPair(s_obj, s_year, s_mon));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            ps.setItem(serial.getSelectionModel().getSelectedItem().getKey());
        }
        type_sel.getSelectionModel().getSelectedItem().getController(c -> {
            try {
                c.fill(locate.get().query(ps));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
