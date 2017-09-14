package cn.keepfight.qsmanager.model;

/**
 * 送货供应表选择器模型
 * Created by tom on 2017/6/10.
 */
public class ReceiptSelection {
    private Long sid; // 供应商ID
    private String year;
    private String month;

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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(Long year) {
        this.year = year.toString();
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


    @Override
    public String toString() {
        return "sid:"+sid+";year:"+year+";month:"+month;
    }
}
