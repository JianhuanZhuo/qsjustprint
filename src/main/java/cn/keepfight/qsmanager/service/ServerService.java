package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.ReceiptModelFull;

/**
 * Created by tom on 2017/6/7.
 */
public interface ServerService {

    /**
     * 获取登录服务实现实例
     *
     * @return 登录服务实现实例
     */
    LoginService getLoginService();


    /**
     * 获得客户服务实现实例
     */
    CustomService getCustomService();

    /**
     * 获得员工服务实现实例
     */
    StuffService getStuffService();

    /**
     * 获得产品服务实现实例
     */
    ProductService getProductService();

    /**
     * 获得供应商服务实现实例
     */
    SupplyService getSupplyService();


    /**
     * 获得原料服务实现实例
     */
    MaterialService getMaterialService();

    /**
     * 获得常见订购列表服务实现实例
     */
    OrderFavorService getOrderFavorService();

    /**
     * 获得供应送货记录服务实现实例
     */
    ReceiptService getReceiptService();

    /**
     * 获得供应商年度对账表操作服务实现实例
     */
    SupAnnualService getSupAnnualService();

    /**
     * 获得订单服务实现实例
     */
    OrderService getOrderService();

    /**
     * 获得送货单服务实现实例
     */
    DeliveryService getDeliveryService();

    /**
     * 获得客户年度对账表操作服务实现实例
     */
    CustAnnualService getCustAnnualService();



    /**
     * 获得打印服务实现实例
     */
    default PrintService getPrintService(){
        return PrintService.getInstance();
    }
}
