package cn.keepfight.qsmanager.just;

import cn.keepfight.utils.FXUtils;
import javafx.beans.property.*;

import java.math.BigDecimal;

/**
 * 送货单单元 Item
 * Created by tom on 2017/9/13.
 */
public class DeliveryItem{
    private StringProperty product = new SimpleStringProperty();
    private StringProperty detail = new SimpleStringProperty();
    private StringProperty unit = new SimpleStringProperty();
    private StringProperty note = new SimpleStringProperty();
    private ObjectProperty<BigDecimal> price = new SimpleObjectProperty<>(new BigDecimal(0));
    private ObjectProperty<BigDecimal> num = new SimpleObjectProperty<>(new BigDecimal(0));
    private ObjectProperty<BigDecimal> totalItem = new SimpleObjectProperty<>(new BigDecimal(0));
    private IntegerProperty yuan_u4 = new SimpleIntegerProperty();
    private IntegerProperty yuan_u3 = new SimpleIntegerProperty();
    private IntegerProperty yuan_u2 = new SimpleIntegerProperty();
    private IntegerProperty yuan_u1 = new SimpleIntegerProperty();
    private IntegerProperty yuan = new SimpleIntegerProperty();
    private IntegerProperty yuan_d1 = new SimpleIntegerProperty();
    private IntegerProperty yuan_d2 = new SimpleIntegerProperty();

    {
        FXUtils.bindProperties(totalItem, ()->{
            try {
                return price.get().multiply(num.get());
            }catch (Exception e){
                return new BigDecimal(0);
            }
        }, price, num);

        totalItem.addListener(e->{
            BigDecimal d = totalItem.get();
            yuan_u4.set(FXUtils.getNumAt(d, 5, true));
            yuan_u3.set(FXUtils.getNumAt(d, 4, true));
            yuan_u2.set(FXUtils.getNumAt(d, 3, true));
            yuan_u1.set(FXUtils.getNumAt(d, 2, true));
            yuan.set(FXUtils.getNumAt(d, 1, true));
            yuan_d1.set(FXUtils.getNumAt(d, 1, false));
            yuan_d2.set(FXUtils.getNumAt(d, 2, false));
        });
    }

    public DeliveryItem(){
    }

    public String getProduct() {
        return product.get();
    }

    public StringProperty productProperty() {
        return product;
    }

    public void setProduct(String product) {
        this.product.set(product);
    }

    public String getDetail() {
        return detail.get();
    }

    public StringProperty detailProperty() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail.set(detail);
    }

    public String getUnit() {
        return unit.get();
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public BigDecimal getNum() {
        return num.get();
    }

    public ObjectProperty<BigDecimal> numProperty() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num.set(num);
    }

    public BigDecimal getTotalItem() {
        return totalItem.get();
    }

    public ObjectProperty<BigDecimal> totalItemProperty() {
        return totalItem;
    }

    public int getYuan_u4() {
        return yuan_u4.get();
    }

    public IntegerProperty yuan_u4Property() {
        return yuan_u4;
    }

    public void setYuan_u4(int yuan_u4) {
        this.yuan_u4.set(yuan_u4);
    }

    public int getYuan_u3() {
        return yuan_u3.get();
    }

    public IntegerProperty yuan_u3Property() {
        return yuan_u3;
    }

    public void setYuan_u3(int yuan_u3) {
        this.yuan_u3.set(yuan_u3);
    }

    public int getYuan_u2() {
        return yuan_u2.get();
    }

    public IntegerProperty yuan_u2Property() {
        return yuan_u2;
    }

    public void setYuan_u2(int yuan_u2) {
        this.yuan_u2.set(yuan_u2);
    }

    public int getYuan_u1() {
        return yuan_u1.get();
    }

    public IntegerProperty yuan_u1Property() {
        return yuan_u1;
    }

    public void setYuan_u1(int yuan_u1) {
        this.yuan_u1.set(yuan_u1);
    }

    public int getYuan() {
        return yuan.get();
    }

    public IntegerProperty yuanProperty() {
        return yuan;
    }

    public void setYuan(int yuan) {
        this.yuan.set(yuan);
    }

    public int getYuan_d1() {
        return yuan_d1.get();
    }

    public IntegerProperty yuan_d1Property() {
        return yuan_d1;
    }

    public void setYuan_d1(int yuan_d1) {
        this.yuan_d1.set(yuan_d1);
    }

    public int getYuan_d2() {
        return yuan_d2.get();
    }

    public IntegerProperty yuan_d2Property() {
        return yuan_d2;
    }

    public void setYuan_d2(int yuan_d2) {
        this.yuan_d2.set(yuan_d2);
    }
}
