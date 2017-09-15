package cn.keepfight.qsmanager.just;

import cn.keepfight.qsmanager.print.QSPrintType;

/**
 * 可打印对象标记
 * Created by tom on 2017/9/13.
 */
public interface Printable {

    String getTitle();

    QSPrintType getType();

    void setStamp(long stamp, boolean applyStamp);

    long getStamp();

    Printable cloneBill();

    boolean lookup(String s);
}
