package cn.keepfight.qsmanager.model;

import java.math.BigDecimal;

/**
 * 供应商年度对账表模型
 * Created by tom on 2017/6/6.
 */
public class SupAnnualModel {
    private Long id;
    private Long sid;
    private Long year;
    private BigDecimal remainder;

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

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public BigDecimal getRemainder() {
        return remainder;
    }

    public void setRemainder(BigDecimal remainder) {
        this.remainder = remainder;
    }
}
