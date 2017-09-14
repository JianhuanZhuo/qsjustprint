package cn.keepfight.qsmanager.model;

import java.math.BigDecimal;

/**
 * 供应对账表总额查询模型
 * Created by tom on 2017/6/15.
 */
public class AnnualTotalModel {
    private Long mon;
    private BigDecimal total;

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
}
