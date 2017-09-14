package cn.keepfight.qsmanager.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 供应商年度对账表的全模型，即添加了月明细的引用
 * Created by tom on 2017/6/15.
 */
public class CustAnnualModelFull implements ModelFull<CustAnnualModel>{

    private Long id;
    private Long cid;
    private Long year;
    private BigDecimal remainder;

    private List<CustAnnualMonModel> mons = new ArrayList<>();

    @Override
    public void set(CustAnnualModel custAnnualModel) {
        setId(custAnnualModel.getId());
        setCid(custAnnualModel.getCid());
        setYear(custAnnualModel.getYear());
        setRemainder(custAnnualModel.getRemainder());
    }

    @Override
    public CustAnnualModel get() {
        CustAnnualModel model = new CustAnnualModel();
        model.setId(getId());
        model.setCid(getCid());
        model.setYear(getYear());
        model.setRemainder(getRemainder());
        return model;
    }

    public void addMon(CustAnnualMonModel... ms){
        for(CustAnnualMonModel m :ms){
            if (m!=null){
                m.setCaid(getId());
            }
            mons.add(m);
        }
    }

    public void addMon(Collection<CustAnnualMonModel> ms){
        ms.forEach(m->{
            if (m!=null){
                m.setCaid(getId());
            }
            mons.add(m);
        });
    }

    public List<CustAnnualMonModel> getMons() {
        return mons;
    }

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
