package cn.keepfight.qsmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 供应商送货表模型
 * 这个模型带有全部的明细
 * Created by tom on 2017/6/6.
 */
public class ReceiptModelFull {

    private Long id;
    private Long sid;
    private String serial;
    private Long rdate;

    private String supply;

    private List<ReceiptDetailModel> detailList = new ArrayList<>();

    public ReceiptModelFull() {

    }

    public ReceiptModelFull(ReceiptModel model) {
        setId(model.getId());
        setSid(model.getSid());
        setSerial(model.getSerial());
        setRdate(model.getRdate());
    }

    public ReceiptModel getReceiptModel() {
        ReceiptModel model = new ReceiptModel();
        model.setId(getId());
        model.setSid(getSid());
        model.setRdate(getRdate());
        model.setSerial(getSerial());
        return model;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public List<ReceiptDetailModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ReceiptDetailModel> detailList) {
        this.detailList = detailList;
        if (detailList != null)
            detailList.forEach(d -> d.setRid(id));
    }

    public void addDetail(ReceiptDetailModel detailModel) {
        // 设置关联的ID
        detailModel.setRid(getId());
        detailList.add(detailModel);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        System.out.println("full 设置ID：" + id);
        this.id = id;
        // 更新ID
        if (detailList != null)
            detailList.forEach(d -> d.setRid(id));
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

    public Long getRdate() {
        return rdate;
    }

    public void setRdate(Long rdate) {
        this.rdate = rdate;
    }


}
