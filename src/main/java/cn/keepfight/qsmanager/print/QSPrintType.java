package cn.keepfight.qsmanager.print;

import cn.keepfight.qsmanager.just.PrintPaneController;
import cn.keepfight.utils.ViewPathUtil;
import javafx.print.PageOrientation;
import javafx.print.Paper;

import java.io.IOException;

/**
 * 打印件类型有：
 * <li>
 * <ol>送货单》》》客户》年份》月份》账单</ol>
 * <ol>收据单》》》客户》年份》月份》账单</ol>
 * <ol>安利专用送货单》》》！客户！》年份》月份》</ol>
 * <ol>客户月对账单</ol>
 * <ol>客户年度对账单</ol>
 * <ol>供应商月对账单</ol>
 * <ol>供应商年度对账单</ol>
 * </li>
 * Created by tom on 2017/6/26.
 */
public enum QSPrintType {
    DELIVERY("print_delivery.fxml"),
    RECEIPT("print_receipt.fxml"),
    RECEIPT_P("print_receipt_p.fxml"),
    RECEIPT_M("print_more.fxml");

    private PrintPaneController controller;
    QSPrintType(String viewPath) {
        try {
            controller = ViewPathUtil.loadViewForController(viewPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return controller.getTitle();
    }

    public Paper getPaper() {
        return controller.getPaper();
    }

    public PageOrientation getOrientation() {
        return controller.getPageOrientation();
    }

    public double getMarginReqire() {
        return controller.getMargin();
    }

    public PrintPaneController getController() {
        return controller;
    }

    public int getFolder(){
        return controller.getFolder();
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
