package cn.keepfight.qsmanager.model;

import java.math.BigDecimal;

/**
 * 供应商年度对账月明细表模型
 * Created by tom on 2017/6/6.
 */
public class CustAnnualMonModel {
    private Long id;
    private Long caid;
    private Long mon;
    private BigDecimal total;
    private String billunit;
    private Long billdate;
    private BigDecimal billtotal;
    private BigDecimal rate;
    private String remitunit;
    private String pattern;
    private Long remitdate;
    private BigDecimal paytotal;
    private String note;

    public CustAnnualMonModel(){}
    
    public CustAnnualMonModel(CustAnnualMonModel m){
         setId(m.getId());
         setCaid(m.getCaid());
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
    
    private boolean valid = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCaid() {
        return caid;
    }

    public void setCaid(Long caid) {
        this.caid = caid;
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

    public BigDecimal getRate() {
        return rate;
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

    public BigDecimal getRateTotal(){
        try {
            return getRate().multiply(getBilltotal());
        }catch (Exception e){
            return new BigDecimal(0);
        }
    }

    /**
     * 获得实际需要支付的金额
     */
    public BigDecimal getNeedPayTotal(){
        // 金额 + 税金 - 付款
        try {
            return getTotal().add(getRateTotal()).subtract(getPaytotal());
        }catch (Exception e){
            return new BigDecimal(0);
        }
    }
}
