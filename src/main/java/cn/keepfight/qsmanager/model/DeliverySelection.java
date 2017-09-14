package cn.keepfight.qsmanager.model;

/**
 * 送货单选择器
 * Created by tom on 2017/6/21.
 */
public class DeliverySelection {
    private Long cid; // 客户ID
    private String year;
    private String month;
    private String orderStr; // 订单号匹配选择

    public DeliverySelection(){}
    public DeliverySelection(Long cid, Long year, Long month, String orderStr){
        setCid(cid);
        setYear(year);
        setMonth(month);
        setOrderStr(orderStr);
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

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
