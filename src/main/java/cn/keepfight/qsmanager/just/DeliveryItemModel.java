package cn.keepfight.qsmanager.just;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 送货单单元 Item
 * Created by tom on 2017/9/13.
 */
public class DeliveryItemModel implements Serializable{
    private String product = "";
    private String detail = "";
    private String unit = "";
    private String note = "";
    private BigDecimal price = new BigDecimal(0);
    private BigDecimal num = new BigDecimal(0);

    public DeliveryItemModel(){
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public DeliveryItemModel cloneItem(){
        DeliveryItemModel res = new DeliveryItemModel();
        res.setProduct(product);
        res.setProduct(product);
        res.setDetail(detail);
        res.setUnit(unit);
        res.setNote(note);
        res.setPrice(price);
        res.setNum(num) ;
        return res;
    }

    public DeliveryItem toItem(){
        DeliveryItem res = new DeliveryItem();
        res.setProduct(product);
        res.setProduct(product);
        res.setDetail(detail);
        res.setUnit(unit);
        res.setNote(note);
        res.setPrice(price);
        res.setNum(num) ;
        return res;
    }
}
