package cn.keepfight.qsmanager.model;

import cn.keepfight.utils.FXUtils;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;

import java.math.BigDecimal;

/**
 * 供应商送货明细表模型
 * Created by tom on 2017/6/6.
 */
public class ReceiptDetailModel {
    private LongProperty id = new SimpleLongProperty();
    private LongProperty rid = new SimpleLongProperty();
    private StringProperty serial = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty color = new SimpleStringProperty();
    private StringProperty spec = new SimpleStringProperty();
    private ObjectProperty<BigDecimal> price = new SimpleObjectProperty<>();
    private StringProperty unit = new SimpleStringProperty();
    private ObjectProperty<BigDecimal> num = new SimpleObjectProperty<>();

    // 辅助属性
    private ObjectProperty<BigDecimal> totalProperty= new SimpleObjectProperty<>();

    {
        FXUtils.bindProperties(totalProperty, this::getTotal, price, num);
    }

    public ReceiptDetailModel(){}

    public ReceiptDetailModel(ReceiptDetailModel m){
        setId(m.getId());
        setRid(m.getRid());
        setSerial(m.getSerial());
        setName(m.getName());
        setColor(m.getColor());
        setSpec(m.getSpec());
        setPrice(m.getPrice());
        setUnit(m.getUnit());
        setNum(m.getNum());
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

    public long getRid() {
        return rid.get();
    }

    public LongProperty ridProperty() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid.set(rid);
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

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getSpec() {
        return spec.get();
    }

    public StringProperty specProperty() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec.set(spec);
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

    public String getUnit() {
        return unit.get();
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
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


    public BigDecimal getTotal(){
        try {
            return getPrice().multiply(getNum());
        }catch (Exception e){
            return new BigDecimal(0);
        }

    }

    public ObjectProperty<BigDecimal> totalProperty(){
        if (totalProperty==null){
            totalProperty = new SimpleObjectProperty<>();
            totalProperty.bind(new ObjectBinding<BigDecimal>() {
                {
                    bind(price, num);
                }
                @Override
                protected BigDecimal computeValue() {
                    return getTotal();
                }
            });
        }
        return totalProperty;
    }

    public void update(ReceiptDetailModel model){
        setRid(model.getRid());
        setSerial(model.getSerial());
        setName(model.getName());
        setColor(model.getColor());
        setSpec(model.getSpec());
        setPrice(model.getPrice());
        setUnit(model.getUnit());
        setNum(model.getNum());
    }
}
