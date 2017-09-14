package cn.keepfight.qsmanager.model;

import java.math.BigDecimal;

/**
 * 客户年表模型
 * Created by tom on 2017/6/6.
 */
public class CustAnnualModel {
    private Long id;
    private Long cid;
    private Long year;
    private BigDecimal remainder;

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
