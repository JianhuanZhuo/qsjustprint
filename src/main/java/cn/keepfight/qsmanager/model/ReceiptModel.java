package cn.keepfight.qsmanager.model;

/**
 * 供应商送货表模型
 * Created by tom on 2017/6/6.
 */
public class ReceiptModel {

    private Long id;
    private Long sid;
    private String serial;
    private Long rdate;

    public ReceiptModel(){

    }

    public ReceiptModel(ReceiptModelFull modelFull) {
        id = modelFull.getId();
        sid = modelFull.getSid();
        serial = modelFull.getSerial();
        rdate = modelFull.getRdate();
    }

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

    public Long getRdate() {
        return rdate;
    }

    public void setRdate(Long rdate) {
        this.rdate = rdate;
    }
}
