package cn.keepfight.qsmanager.model;

/**
 * 订单选择器
 * Created by tom on 2017/6/20.
 */
public class OrderSelection {
    private Long cid; // 客户ID
    private String year;
    private String month;
    private String day;

    public OrderSelection(){}
    public OrderSelection(Long cid, Long year, Long month, Long s){
        setCid(cid);
        setYear(year);
        setMonth(month);
        setDay(s);
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(Long year) {
        this.year = year==null?null:""+year;
    }

    public void setMonth(Long month) {
        if (month==null){
            this.month=null;
            return;
        }
        if (month < 10) {
            this.month = "0" + month;
        } else {
            this.month = month.toString();
        }
    }

    public String getDay() {
        return day;
    }

    public void setDay(Long s) {
        if (s==null){
            this.day=null;
            return;
        }
        this.day = s.toString();
    }
}
