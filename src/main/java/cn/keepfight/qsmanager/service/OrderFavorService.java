package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.ProductModel;
import cn.keepfight.qsmanager.model.ProductModel;

import java.util.List;

/**
 * 常见订购列表服务接口
 * Created by tom on 2017/6/7.
 */
public interface OrderFavorService {

    /**
     * 指定客户选择全部常见订购列表
     */
    List<ProductModel> selectAll(Long cid) throws Exception;


    void insert(long cid, long pid) throws Exception;

    /**
     * 删除指定常见订购列表，以ID为准。
     */
    void delete(long cid, long pid) throws Exception;
}
