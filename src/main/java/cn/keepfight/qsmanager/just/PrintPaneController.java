package cn.keepfight.qsmanager.just;

import javafx.fxml.Initializable;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.scene.Node;

/**
 * 送货单表格打印控制器
 * Created by tom on 2017/6/23.
 */
public abstract class PrintPaneController<T extends Printable> implements Initializable {

    public abstract Node getRoot();

    public abstract String getTitle();

    public abstract T pack();

    public abstract T newBill();

    public abstract void fill(T data);

    public Paper getPaper(){
        return Paper.A4;
    }

    public PageOrientation getPageOrientation(){
        return PageOrientation.PORTRAIT;
    }

    public double getMargin(){
        return 30;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
