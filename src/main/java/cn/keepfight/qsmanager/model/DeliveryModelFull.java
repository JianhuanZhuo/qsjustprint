package cn.keepfight.qsmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 送货单表模型，全模型
 * Created by tom on 2017/6/6.
 */
public class DeliveryModelFull implements ModelFull<DeliveryModel> {
    private Long id;
    private Long cid;
    private String serial;
    private Long ddate;
    private String order_serial;
    private String cust;


    private List<DeliveryItemModel> deliveryItemModels = new ArrayList<>();

    @Override
    public void set(DeliveryModel model) {
        setId(model.getId());
        setCid(model.getCid());
        setSerial(model.getSerial());
        setDdate(model.getDdate());
        setOrder_serial(model.getOrder_serial());
        setCust(model.getCust());
    }

    @Override
    public DeliveryModel get() {
        DeliveryModel res = new DeliveryModel();
        res.setId(getId());
        res.setCid(getCid());
        res.setSerial(getSerial());
        res.setDdate(getDdate());
        res.setOrder_serial(getOrder_serial());
        res.setCust(getCust());
        return res;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        if (deliveryItemModels != null) {
            deliveryItemModels.forEach(x -> x.setDid(id));
        }
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

    public List<DeliveryItemModel> getDeliveryItemModels() {
        return deliveryItemModels;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public void setDeliveryItemModels(List<DeliveryItemModel> deliveryItemModels) {
        this.deliveryItemModels = deliveryItemModels;
    }
}
