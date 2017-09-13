package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.*;

import java.util.List;

/**
 * 客户年度对账表操作服务
 * Created by tom on 2017/6/15.
 */
public interface CustAnnualService {

    /**
     * 选择指定年份、指定客户的年度对账表
     */
    CustAnnualModelFull selectAnnual(Long cid, Long year) throws Exception;

    /**
     * 更新年对账表
     */
    void updateAnnual(CustAnnualModel model) throws Exception;

    /**
     * 更新月明细
     */
    void updateMon(CustAnnualMonModel model) throws Exception;
}
