package cn.keepfight.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * 警告窗口
 * Created by tom on 2017/6/7.
 */
public class WarningBuilder {

    public static void build(String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("警告，程序运行中可能出现某些异常！");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void build(String head, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public static void delSimpleConfirm(Runnable x){
        simpleConfirm(x, "是否要删除这条记录？");
    }

    /**
     * 简单确认提示
     * @param x 点击确认回调接口
     * @param text 提示文本
     */
    public static void simpleConfirm(Runnable x, String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(text);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            x.run();
        }
    }
}
