package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.*;

import java.util.List;

/**
 * 供应商服务接口
 * Created by tom on 2017/6/7.
 */
public interface ReceiptService {

    /**
     * 选择特定供应商、特定时间的全部送货记录
     */
    List<ReceiptModelFull> selectAll(Long sid, Long year, Long month) throws Exception;


    /**
     * 查询指定供应商和年份的全部月份的供应总额
     * @param sid 指定供应商
     * @param year 指定年份
     */
    List<AnnualTotalModel> supAnnualTotal(Long sid, Long year) throws Exception;


    /**
     * 查询指定年份的全部供应商采购总额
     * 这个查询用于购付汇总中的年度采购汇总
     */
    List<MonStatModel> takeStat(Long year) throws Exception;

    /**
     * 插入一条送货记录
     */
    void insert(ReceiptModelFull model) throws Exception;

    /**
     * 修改一条送货记录
     */
    void update(ReceiptModelFull model) throws Exception;

    /**
     * 删除指定 ID 的送货记录。
     */
    void delete(ReceiptModel model) throws Exception;

    /**
     * 获取供应商送货全部可用的年份
     */
    List<Long> selectYear() throws Exception;
}
