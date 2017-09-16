package cn.keepfight.qsmanager;

import cn.keepfight.qsmanager.just.Printable;
import cn.keepfight.utils.WarningBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 列表服务
 * Created by tom on 2017/9/14.
 */
public class BillListServer {
    private static BillListServer instance = new BillListServer();

    public static BillListServer getInstance() {
        return instance;
    }

    private Comparator<Printable> comparator = (o1, o2) -> {
        if (o1 == null){
            return 1;
        }else if (o2 == null){
            return -1;
        }
        long x = o1.getStamp();
        long y = o2.getStamp();
        if (x==y){
            return 0;
        }else if (x>y){
            return -1;
        }else {
            return 1;
        }
    };

    private BillListServer() {

    }

    private ObservableList<Printable> printableList = FXCollections.observableArrayList();

    public ObservableList<Printable> getPrintableList() {
        return printableList;
    }

    public void setPrintableList(ObservableList<Printable> printableList) {
        this.printableList = printableList;
        FXCollections.sort(printableList, comparator);
    }

    public void addPritable(Printable p) {
        printableList.add(p);
        FXCollections.sort(printableList, comparator);
    }

    public void replacePrintable(Printable target, Printable p){
        printableList.remove(target);
        printableList.add(p);
        FXCollections.sort(printableList, comparator);
    }

    public void removePrintable(Printable p){
        printableList.remove(p);
//        FXCollections.sort(printableList, comparator);
    }

        public void loadFromFile(){
            try(ObjectInputStream is = new ObjectInputStream(new FileInputStream("./data/wxp.objs"))){
                printableList.setAll((List<Printable>) is.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                WarningBuilder.build("订单列表读取失败！");
        }
    }

    public void storeToFile(){
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("./data/wxp.objs"))) {
            os.writeObject(new ArrayList<>(printableList));
        } catch (IOException e) {
            e.printStackTrace();
            WarningBuilder.build("订单列表保存失败！");
        }
    }
}
