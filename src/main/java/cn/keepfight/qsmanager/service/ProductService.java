package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.ProductModel;

import java.util.List;

/**
 * 产品服务接口
 * Created by tom on 2017/6/7.
 */
public interface ProductService {

    /**
     * 选择全部产品即 <code>select *</code>
     */
    List<ProductModel> selectAll() throws Exception;

    /**
     * 更新指定产品，以ID为准
     */
    void update(ProductModel model) throws Exception;

    /**
     * 插入指定产品，插入后，该产品的ID将得到更新
     */
    void insert(ProductModel model) throws Exception;

    /**
     * 删除指定产品，以ID为准。
     */
    void delete(ProductModel model) throws Exception;
}
