package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.*;

import java.util.List;

/**
 * 供应商年度对账表操作服务
 * Created by tom on 2017/6/15.
 */
public interface SupAnnualService {

    /**
     * 选择指定年份、指定供应商的年度对账表
     */
    SupAnnualModelFull selectAnnual(Long sid, Long year) throws Exception;

    /**
     * 更新年对账表
     */
    void updateAnnual(SupAnnualModel model) throws Exception;

    /**
     * 指定年份统计付款
     */
    List<MonStatModel> payStat(Long year) throws Exception;

    /**
     * 指定年份统计汇总
     */
    List<TPStatModel> tpStat(Long year) throws Exception;
    /**
     * 更新月明细
     */
    void updateMon(SupAnnualMonModel model) throws Exception;
}
