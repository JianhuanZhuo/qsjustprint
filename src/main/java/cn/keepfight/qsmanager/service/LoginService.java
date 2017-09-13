package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.model.CustomModel;

/**
 * 登录操作服务
 * Created by tom on 2017/6/7.
 */
public interface LoginService {
    class PswIllegalException extends Exception {

    }

    /**
     * 远程验证用户的合法性，并返回用户的完整信息对象。
     *
     * @param userModel 有待验证的用户
     * @return 用户的完整信息对象
     * @throws Exception 在请求过程中产生的异常。
     */
    CustomModel login(CustomModel userModel) throws Exception;


    void checkConnect() throws Exception;
}
