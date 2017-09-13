package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.SupplyModel;

import java.util.List;

/**
 * 供应商服务接口
 * Created by tom on 2017/6/7.
 */
public interface SupplyService {

    /**
     * 选择全部供应商即 <code>select *</code>
     */
    List<SupplyModel> selectAll() throws Exception;

    /**
     * 根据ID选择供应商
     */
    SupplyModel selectByID(Long ID) throws Exception;

    /**
     * 更新指定供应商，以ID为准
     */
    void update(SupplyModel model) throws Exception;

    /**
     * 插入指定供应商，插入后，该用户的ID将得到更新
     */
    void insert(SupplyModel model) throws Exception;

    /**
     * 删除指定供应商，以ID为准。
     */
    void delete(SupplyModel model) throws Exception;
}
