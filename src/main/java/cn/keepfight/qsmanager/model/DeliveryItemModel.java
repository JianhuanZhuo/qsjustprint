package cn.keepfight.qsmanager.model;

import cn.keepfight.utils.FXUtils;
import javafx.beans.property.*;

import java.math.BigDecimal;

/**
 * 送货历史记录数
 * Created by tom on 2017/6/6.
 */
public class DeliveryItemModel {
    private LongProperty id = new SimpleLongProperty();
    private LongProperty did = new SimpleLongProperty();
    private StringProperty serial = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty detail = new SimpleStringProperty();
    private ObjectProperty<BigDecimal> num = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> price = new SimpleObjectProperty<>();
    private LongProperty pack = new SimpleLongProperty();
    private StringProperty unit = new SimpleStringProperty();
    private StringProperty note = new SimpleStringProperty();

    public DeliveryItemModel(){}

    public DeliveryItemModel(DeliveryItemModel m){
        setId(m.getId());
        setDid(m.getDid());
        setSerial(m.getSerial());
        setName(m.getName());
        setDetail(m.getDetail());
        setNum(m.getNum());
        setPrice(m.getPrice());
        setPack(m.getPack());
        setUnit(m.getUnit());
        setNote(m.getNote());
    }

    // 非数据库对象
    private ObjectProperty<BigDecimal> total = new SimpleObjectProperty<>();

    {
        FXUtils.bindProperties(total, this::getTotal, pack, num, price);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public long getDid() {
        return did.get();
    }

    public LongProperty didProperty() {
        return did;
    }

    public void setDid(long did) {
        this.did.set(did);
    }

    public String getSerial() {
        return serial.get();
    }

    public StringProperty serialProperty() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial.set(serial);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public BigDecimal getNum() {
        return num.get();
    }

    public ObjectProperty<BigDecimal> numProperty() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num.set(num);
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

    public long getPack() {
        return pack.get();
    }

    public LongProperty packProperty() {
        return pack;
    }

    public void setPack(long pack) {
        this.pack.set(pack);
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

    public BigDecimal getTotal(){
        try {
            return price.get()
                    .multiply(num.get())
                    .multiply(new BigDecimal(pack.get()));
        }catch (Exception e){
            return new BigDecimal(0);
        }
    }

    public ObjectProperty<BigDecimal> totalProperty() {
        return total;
    }

    public void update(DeliveryItemModel model) {
        setDid(model.getDid());
        setSerial(model.getSerial());
        setName(model.getName());
        setDetail(model.getDetail());
        setPrice(model.getPrice());
        setNum(model.getNum());
        setPack(model.getPack());
        setUnit(model.getUnit());
        setNote(model.getNote());
    }
}
