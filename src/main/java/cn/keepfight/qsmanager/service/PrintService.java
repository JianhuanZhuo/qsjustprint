package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.controller.PrintController;
import cn.keepfight.utils.CustomDialog;
import cn.keepfight.utils.ViewPathUtil;

import java.io.IOException;
import java.util.Optional;

/**
 * 打印服务
 * Created by tom on 2017/7/4.
 */
public class PrintService {

    private static PrintService instance = new PrintService();

    // 子界面
    private PrintController controller;

    // 私有化实例，表示单例模式
    private PrintService(){
        try {
            controller = ViewPathUtil.loadViewForController("print.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PrintService getInstance() {
        return instance;
    }

    public PrintController getController() {
        return controller;
    }

    public Optional<PrintSelection> build(){
        return CustomDialog.gen().build(controller);
    }

    public Optional<PrintSelection> build(PrintSelection s){
        return CustomDialog.gen().build(controller, s);
    }
}
