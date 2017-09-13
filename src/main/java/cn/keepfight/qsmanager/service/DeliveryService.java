package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.DeliveryModel;
import cn.keepfight.qsmanager.model.DeliveryModelFull;
import cn.keepfight.qsmanager.model.DeliverySelection;
import cn.keepfight.qsmanager.model.ProductModel;

import java.util.List;

/**
 * 送货单服务接口
 * Created by tom on 2017/6/7.
 */
public interface DeliveryService {

    /**
     * 选择全部送货单
     */
    List<DeliveryModelFull> selectAll(DeliverySelection selection) throws Exception;

    /**
     * 根据ID选择送货单
     */
    DeliveryModelFull selectByID(Long id) throws Exception;

    /**
     * 更新指定送货单，以ID为准
     */
    void update(DeliveryModelFull model) throws Exception;

    /**
     * 插入指定送货单，插入后，该送货单的ID将得到更新
     */
    void insert(DeliveryModelFull model) throws Exception;

    /**
     * 删除指定送货单，以ID为准。
     */
    void delete(DeliveryModel model) throws Exception;
}
