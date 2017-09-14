package cn.keepfight.qsmanager.just;

import cn.keepfight.qsmanager.print.QSPrintType;
import cn.keepfight.utils.FXUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 送货单表模型，全模型
 * Created by tom on 2017/6/6.
 */
public class DeliveryPrintModel implements Printable {
    private String head = "";
    private String serial;
    private String my_phone;
    private String my_addr;
    private String cust_name;
    private String cust_addr;
    private String cust_phone;
    private String cust_contract;
    private String maker;
    private String date_delivery;
    private long stamp;
    private QSPrintType type;

    private List<DeliveryItem> items = new ArrayList<>();

    @Override
    public String getTitle() {
        return date_delivery+"-"+head;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_addr() {
        return cust_addr;
    }

    public void setCust_addr(String cust_addr) {
        this.cust_addr = cust_addr;
    }

    public String getCust_phone() {
        return cust_phone;
    }

    public void setCust_phone(String cust_phone) {
        this.cust_phone = cust_phone;
    }

    public String getCust_contract() {
        return cust_contract;
    }

    public void setCust_contract(String cust_contract) {
        this.cust_contract = cust_contract;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getDate_delivery() {
        return date_delivery;
    }

    public List<DeliveryItem> getItems() {
        return items;
    }

    public void setItems(List<DeliveryItem> items) {
        this.items = items;
    }

    public String getMy_phone() {
        return my_phone;
    }

    public void setMy_phone(String my_phone) {
        this.my_phone = my_phone;
    }

    public String getMy_addr() {
        return my_addr;
    }

    public void setMy_addr(String my_addr) {
        this.my_addr = my_addr;
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
        this.date_delivery = FXUtils.stampToDate(stamp);
    }

    public QSPrintType getType() {
        return type;
    }

    public void setType(QSPrintType type) {
        this.type = type;
    }
}
