package cn.keepfight.qsmanager.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

/**
 * 原料表模型
 * Created by tom on 2017/6/6.
 */
public class MaterialModel {
    private ObjectProperty<String> color = new SimpleObjectProperty<>();
    private Long id;
    private Long sid;
    private String serial;
    private ObjectProperty<String> name = new SimpleObjectProperty<>();
    private String spec;
    private BigDecimal price;
    private ObjectProperty<String> unit = new SimpleObjectProperty<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObjectProperty<String> nameProperty() {
        return this.name;
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
//        this.color = color;
    }

    public ObjectProperty<String> colorProperty() {
        return this.color;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public ObjectProperty<String> unitProperty() {
        return this.unit;
    }
}
