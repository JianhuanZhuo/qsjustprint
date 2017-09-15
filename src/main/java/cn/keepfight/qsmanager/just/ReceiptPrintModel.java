package cn.keepfight.qsmanager.just;

import cn.keepfight.qsmanager.PropertiesServer;
import cn.keepfight.qsmanager.print.QSPrintType;
import cn.keepfight.utils.FXUtils;

import java.io.Serializable;

/**
 * 专用收据
 * Created by tom on 2017/9/13.
 */
public class ReceiptPrintModel implements Printable, Serializable {
    private String head = "";
    private String date = "";
    private String serial;
    private String line1;
    private String line2;
    private String line3;
    private int total;
    private long stamp;

    @Override
    public String getTitle() {
        return date + " " + head + "专用收据";
    }

    @Override
    public QSPrintType getType() {
        return QSPrintType.RECEIPT_P;
    }

    @Override
    public void setStamp(long stamp, boolean applyStamp) {
        this.stamp = stamp;
        if (applyStamp) {
            date = FXUtils.stampToDateCH(stamp);
        }
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getStamp() {
        return stamp;
    }

    @Override
    public Printable cloneBill() {
        ReceiptPrintModel res = new ReceiptPrintModel();
        res.head = head;
        res.date = date;
        res.serial = PropertiesServer.getInstance().getNumStr();
        res.line1 = line1;
        res.line2 = line2;
        res.line3 = line3;
        res.total = total;
        res.stamp = System.currentTimeMillis();
        return res;
    }

    @Override
    public boolean lookup(String s) {
        return (head+" "+date+" "+serial+" "+line1+" "+line2+" "+line3+" "+total).contains(s);
    }
}
