package cn.keepfight.qsmanager.model;

/**
 * 客户表模型
 * Created by tom on 2017/6/6.
 */
public class CustomModel {
    private Long id;
    private String serial;
    private String name;
    private String namefull;
    private String phone;
    private String fax;
    private String accpb;
    private String accpv;
    private String bccpv;
    private String bccpb;
    private String addr;
    private String note;

    private String acc;
    private String psw;
    private Long utype;

    public String getBccpv() {
        return bccpv;
    }

    public void setBccpv(String bccpv) {
        this.bccpv = bccpv;
    }

    public String getBccpb() {
        return bccpb;
    }

    public void setBccpb(String bccpb) {
        this.bccpb = bccpb;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public Long getUtype() {
        return utype;
    }

    public void setUtype(Long utype) {
        this.utype = utype;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAccpb() {
        return accpb;
    }

    public void setAccpb(String accpb) {
        this.accpb = accpb;
    }

    public String getAccpv() {
        return accpv;
    }

    public void setAccpv(String accpv) {
        this.accpv = accpv;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNamefull() {
        return namefull;
    }

    public void setNamefull(String namefull) {
        this.namefull = namefull;
    }
}
