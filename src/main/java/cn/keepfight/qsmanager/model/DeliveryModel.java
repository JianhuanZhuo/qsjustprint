package cn.keepfight.qsmanager.model;

/**
 * 送货单表模型
 * Created by tom on 2017/6/6.
 */
public class DeliveryModel {
    private Long id;
    private Long cid;
    private String serial;
    private Long ddate;
    private String order_serial;
    private String cust;

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

    public Long getDdate() {
        return ddate;
    }

    public void setDdate(Long ddate) {
        this.ddate = ddate;
    }

    public String getOrder_serial() {
        return order_serial;
    }

    public void setOrder_serial(String order_serial) {
        this.order_serial = order_serial;
    }

    public String getCust() {
        return cust;
    }

    public void setCust(String cust) {
        this.cust = cust;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }
}
