package cn.keepfight.qsmanager.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 供应商年度对账表的全模型，即添加了月明细的引用
 * Created by tom on 2017/6/15.
 */
public class SupAnnualModelFull implements ModelFull<SupAnnualModel>{

    private Long id;
    private Long sid;
    private Long year;
    private BigDecimal remainder;

    private List<SupAnnualMonModel> mons = new ArrayList<>();

    @Override
    public void set(SupAnnualModel supAnnualModel) {
        setId(supAnnualModel.getId());
        setSid(supAnnualModel.getSid());
        setYear(supAnnualModel.getYear());
        setRemainder(supAnnualModel.getRemainder());
    }

    @Override
    public SupAnnualModel get() {
        SupAnnualModel model = new SupAnnualModel();
        model.setId(getId());
        model.setSid(getSid());
        model.setYear(getYear());
        model.setRemainder(getRemainder());
        return model;
    }

    public void addMon(SupAnnualMonModel... ms){
        for(SupAnnualMonModel m :ms){
            if (m!=null){
                m.setSaid(getId());
            }
            mons.add(m);
        }
    }

    public void addMon(Collection<SupAnnualMonModel> ms){
        ms.forEach(m->{
            if (m!=null){
                m.setSaid(getId());
            }
            mons.add(m);
        });
    }

    public List<SupAnnualMonModel> getMons() {
        return mons;
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
