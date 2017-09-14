package cn.keepfight.qsmanager.model;

/**
 * 年度选择
 * Created by tom on 2017/6/15.
 */
public class AnnualSelection {

    private Long sid; // 供应商ID
    private String year;

    public AnnualSelection(){}

    public AnnualSelection(Long sid, Long year){
        setSid(sid);
        setYear(year);
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setYear(Long year) {
        this.year = year.toString();
    }

    @Override
    public String toString() {
        return "sid:"+sid+";year:"+year;
    }
}
