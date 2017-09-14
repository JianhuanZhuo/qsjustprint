package cn.keepfight.qsmanager.model;

/**
 * 订单表模型
 * Created by tom on 2017/6/6.
 */
public class OrderModel {
    private Long id;
    private Long cid;
    private String serial;
    private Long orderdate;
    private String note;
    private boolean deli = false;
    private boolean building = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Long orderdate) {
        this.orderdate = orderdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDeli() {
        return deli;
    }

    public void setDeli(boolean deli) {
        this.deli = deli;
    }

    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }
}
