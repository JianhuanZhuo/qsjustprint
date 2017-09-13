package cn.keepfight.qsmanager.controller;

import javafx.scene.Node;

/**
 * 中间子面板的控制器的共性接口
 * Created by tom on 2017/6/5.
 */
public interface ContentCtrl {

    Node getRoot();

    /**
     * 该接口在界面加载完成后只调用一次，该调用一般会延迟到首次显示时调用
     */
    void loaded();

    /**
     * 该接口在每次显示（即点击菜单切换到该面板时）调用
     */
    void showed();


    default<T> void updateState(T s){}
}
