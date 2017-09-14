package cn.keepfight.qsmanager.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

/**
 * 产品表模型
 * Created by tom on 2017/6/6.
 */
public class ProductModel {
    private Long id;
    private String serial;
    private String name;
    private String detail;
    private Long pack;
    private BigDecimal price;
    private String unit;
    private String picurl;

    // 非数据库存储对象，而是计算辅助对象
//    private ObjectProperty<BigDecimal> num = new SimpleObjectProperty<>(new BigDecimal(0));
//    private LongProperty packSel = new SimpleLongProperty(-1L);

//    public long getPackSel() {
//        return packSel.get();
//    }
//
//    public LongProperty packSelProperty() {
//        return packSel;
//    }
//
//    public void setPackSel(long packSel) {
//        this.packSel.set(packSel);
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getPack() {
        return pack;
    }

    public void setPack(Long pack) {
        this.pack = pack;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

//    public BigDecimal getNum() {
//        return num.get();
//    }
//
//    public void setNum(BigDecimal num) {
//        this.num.set(num);
//    }
//
//    public ObjectProperty<BigDecimal> getNumProperty(){
//        return num;
//    }
}
