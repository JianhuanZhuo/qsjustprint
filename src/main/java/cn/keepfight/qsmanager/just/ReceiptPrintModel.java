package cn.keepfight.qsmanager.just;

import cn.keepfight.qsmanager.print.QSPrintType;

/**
 * 专用收据
 * Created by tom on 2017/9/13.
 */
public class ReceiptPrintModel implements Printable {
    private String head = "";
    private String year = "";
    private String month = "";
    private String day = "";
    private String serial;
    private String line1;
    private String line2;
    private String line3;
    private int total;
    private long stamp;
    @Override
    public String getTitle() {
        return year+month+day+" "+head+"专用收据";
    }

    @Override
    public QSPrintType getType() {
        return QSPrintType.RECEIPT_P;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }
}
