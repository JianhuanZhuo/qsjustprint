package cn.keepfight.qsmanager;

import cn.keepfight.qsmanager.just.Printable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 2017/9/14.
 */
public class BillListServer {
    private static BillListServer instance = new BillListServer();

    public static BillListServer getInstance() {
        return instance;
    }

    private BillListServer() {

    }

    private ObservableList<Printable> printableList = FXCollections.observableArrayList();

    public ObservableList<Printable> getPrintableList() {
        return printableList;
    }

    public void setPrintableList(ObservableList<Printable> printableList) {
        this.printableList = printableList;
    }

    public void addPritable(Printable p) {
        printableList.add(p);
    }

}
