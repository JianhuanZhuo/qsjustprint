package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.*;

import java.util.List;

/**
 * 订单处理服务
 * Created by tom on 2017/6/20.
 */
public interface OrderService {

    /**
     * 选择指定条件的全部订单，其中客户、年份、月份、选择状态可以为空，表示忽视该条件
     * @param selection 指定条件选择器，包括时间、客户、订单状态，
     */
    List<OrderModelFull> selectAll(OrderSelection selection) throws Exception;


    /**
     * 选择指定客户的全部订单
     */
    List<OrderModel> selectByCid(Long cid) throws Exception;

    /**
     * 查询指定客户和年份的全部月份的订购总额
     * @param cid 指定客户
     * @param year 指定年份
     */
    List<AnnualTotalModel> supAnnualTotal(Long cid, Long year) throws Exception;

    /**
     * 插入一条订单记录，带全部信息的
     * @param modelFull 带全部信息的订单记录
     */
    void insert(OrderModelFull modelFull) throws Exception;

    /**
     * 修改一条订单记录
     */
    void update(OrderModelFull model) throws Exception;

    /**
     * 删除指定 ID 的订单记录。
     */
    void delete(OrderModel model) throws Exception;
    /**
     * 获取订单全部可用的年份
     */
    List<Long> selectYear() throws Exception;

    /**
     * 获得指定订单 ID 的全部明细
     */
    List<OrderItemModel> selectAllByOid(Long oid) throws Exception;
}
