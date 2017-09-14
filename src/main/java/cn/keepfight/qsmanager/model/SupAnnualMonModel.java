package cn.keepfight.qsmanager.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 供应商年度对账月明细表模型
 * Created by tom on 2017/6/6.
 */
public class SupAnnualMonModel {

    // 这不是模型的数据属性，用于表示该模型对象的是否为数据库读取的
    // 如果该值为 false，则表示更新时需要新建一个数据
    private boolean valid;

    private Long said;
    private Long mon;
    private BigDecimal total = new BigDecimal(0);
    private String billunit;
    private Long billdate;
    private BigDecimal billtotal;
    private BigDecimal rate;
    private String remitunit;
    private String pattern;
    private Long remitdate;
    private BigDecimal paytotal;
    private String note;


    public SupAnnualMonModel(){}

    public SupAnnualMonModel(SupAnnualMonModel m){
//        setId(m.getId());
        setSaid(m.getSaid());
        setMon(m.getMon());
        setTotal(m.getTotal());
        setBillunit(m.getBillunit());
        setBilldate(m.getBilldate());
        setBilltotal(m.getBilltotal());
        setRate(m.getRate());
        setRemitunit(m.getRemitunit());
        setPattern(m.getPattern());
        setRemitdate(m.getRemitdate());
        setPaytotal(m.getPaytotal());
        setNote(m.getNote());
    }


    public Long getSaid() {
        return said;
    }

    public void setSaid(Long said) {
        this.said = said;
    }

    public Long getMon() {
        return mon;
    }

    public void setMon(Long mon) {
        this.mon = mon;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getTotalStr() {
        return total==null?"":total.toString();
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getBillunit() {
        return billunit;
    }

    public void setBillunit(String billunit) {
        this.billunit = billunit;
    }

    public Long getBilldate() {
        return billdate;
    }

    public void setBilldate(Long billdate) {
        this.billdate = billdate;
    }

    public BigDecimal getBilltotal() {
        return billtotal;
    }

    public void setBilltotal(BigDecimal billtotal) {
        this.billtotal = billtotal;
    }

    public String getBilltotalStr() {
        return billtotal==null?"":billtotal.toString();
    }


    public BigDecimal getRate() {
        return rate;
    }

    public String getRateStr() {
        return rate==null?"":rate.toString();
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getRemitunit() {
        return remitunit;
    }

    public void setRemitunit(String remitunit) {
        this.remitunit = remitunit;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Long getRemitdate() {
        return remitdate;
    }

    public void setRemitdate(Long remitdate) {
        this.remitdate = remitdate;
    }

    public BigDecimal getPaytotal() {
        return paytotal;
    }

    public String getPaytotalStr() {
        return paytotal==null?"":paytotal.toString();
    }

    public void setPaytotal(BigDecimal paytotal) {
        this.paytotal = paytotal;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
