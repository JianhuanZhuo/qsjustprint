package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.MaterialModel;

import java.util.List;

/**
 * 原料服务接口
 * Created by tom on 2017/6/7.
 */
public interface MaterialService {

    /**
     * 指定供应商选择全部原料即 <code>select * where supply==#{supply}</code>
     */
    List<MaterialModel> selectAll(Long supply) throws Exception;

    /**
     * 更新指定原料，以ID为准
     */
    void update(MaterialModel model) throws Exception;

    /**
     * 插入指定原料，插入后，该用户的ID将得到更新
     */
    void insert(MaterialModel model) throws Exception;

    /**
     * 删除指定原料，以ID为准。
     */
    void delete(MaterialModel model) throws Exception;
}
