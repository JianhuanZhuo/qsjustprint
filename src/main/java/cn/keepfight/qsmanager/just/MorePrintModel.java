package cn.keepfight.qsmanager.just;

import cn.keepfight.qsmanager.print.QSPrintType;
import cn.keepfight.utils.FXUtils;

import java.io.Serializable;

/**
 * 送货单表模型，全模型
 * Created by tom on 2017/6/6.
 */
public class MorePrintModel implements Printable, Serializable {

    private String head;
    private String date;
    private String host;
    private String bank;
    private String house_squre;
    private String house_num;
    private String house_price;
    private String note;
    private String time;

    private String ele_last;
    private String ele_now;
    private String ele_price;

    private String water_last;
    private String water_now;
    private String water_price;

    private String gb_num;
    private String gb_price;
    private String pele_num;
    private String pele_price;
    private String pwater_num;
    private String pwater_price;
    private String other;
    private String other_total;

    private long stamp;

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }


    @Override
    public String getTitle() {
        return date + "-" + head;
    }

    public QSPrintType getType() {
        return QSPrintType.RECEIPT_M;
    }

    @Override
    public void setStamp(long stamp, boolean applyStamp) {
        this.stamp = stamp;
        if (applyStamp) {
            date = FXUtils.stampToDateCH(stamp);
        }
    }

    @Override
    public long getStamp() {
        return stamp;
    }


    @Override
    public Printable cloneBill() {
        MorePrintModel res = new MorePrintModel();
        res.note = note;
        res.time = time;
        res.head = head;
        res.date = date;
        res.host = host;
        res.bank = bank;
        res.house_squre = house_squre;
        res.house_num = house_num;
        res.house_price = house_price;
        res.ele_last = ele_last;
        res.ele_now = ele_now;
        res.ele_price = ele_price;
        res.water_last = water_last;
        res.water_now = water_now;
        res.water_price = water_price;
        res.gb_num = gb_num;
        res.gb_price = gb_price;
        res.pele_num = pele_num;
        res.pele_price = pele_price;
        res.pwater_num = pwater_num;
        res.pwater_price = pwater_price;
        res.other = other;
        res.other_total = other_total;
        return res;
    }

    @Override
    public boolean lookup(String s) {
        String content = time + note + head + date + host + bank + house_squre + house_num + house_price + ele_last + ele_now
                + ele_price + water_last + water_now + water_price + gb_num + gb_price + pele_num + pele_price
                + pwater_num + pwater_price + other + other_total;
        return content.contains(s);
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getHouse_squre() {
        return house_squre;
    }

    public void setHouse_squre(String house_squre) {
        this.house_squre = house_squre;
    }

    public String getHouse_num() {
        return house_num;
    }

    public void setHouse_num(String house_num) {
        this.house_num = house_num;
    }

    public String getHouse_price() {
        return house_price;
    }

    public void setHouse_price(String house_price) {
        this.house_price = house_price;
    }

    public String getEle_last() {
        return ele_last;
    }

    public void setEle_last(String ele_last) {
        this.ele_last = ele_last;
    }

    public String getEle_now() {
        return ele_now;
    }

    public void setEle_now(String ele_now) {
        this.ele_now = ele_now;
    }

    public String getEle_price() {
        return ele_price;
    }

    public void setEle_price(String ele_price) {
        this.ele_price = ele_price;
    }

    public String getWater_last() {
        return water_last;
    }

    public void setWater_last(String water_last) {
        this.water_last = water_last;
    }

    public String getWater_now() {
        return water_now;
    }

    public void setWater_now(String water_now) {
        this.water_now = water_now;
    }

    public String getWater_price() {
        return water_price;
    }

    public void setWater_price(String water_price) {
        this.water_price = water_price;
    }

    public String getGb_num() {
        return gb_num;
    }

    public void setGb_num(String gb_num) {
        this.gb_num = gb_num;
    }

    public String getGb_price() {
        return gb_price;
    }

    public void setGb_price(String gb_price) {
        this.gb_price = gb_price;
    }

    public String getPele_num() {
        return pele_num;
    }

    public void setPele_num(String pele_num) {
        this.pele_num = pele_num;
    }

    public String getPele_price() {
        return pele_price;
    }

    public void setPele_price(String pele_price) {
        this.pele_price = pele_price;
    }

    public String getPwater_num() {
        return pwater_num;
    }

    public void setPwater_num(String pwater_num) {
        this.pwater_num = pwater_num;
    }

    public String getPwater_price() {
        return pwater_price;
    }

    public void setPwater_price(String pwater_price) {
        this.pwater_price = pwater_price;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOther_total() {
        return other_total;
    }

    public void setOther_total(String other_total) {
        this.other_total = other_total;
    }
}
