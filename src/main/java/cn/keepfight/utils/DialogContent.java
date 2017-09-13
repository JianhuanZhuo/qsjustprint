package cn.keepfight.utils;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;


/**
 * 用于展现面板的内容接口
 * Created by tom on 2017/6/7.
 */
public interface DialogContent<T> {

    /**
     * 获得 DialogContent 的根节点，用于填入展现面板
     */
    Node getContent();

    /**
     * 想内容控制器传递 Dialog，这个方法将在 init() 调用之前进行。
     * 默认不实现，也就是一般来说内容不需要用到这个内容控制器，除非需要用到特殊的效果或处理
     * @param dialog 内容控制器所属的 dialog 对象
     */
    default void passDialog(Dialog<T> dialog){

    }

    /**
     * Dialog 界面数据初始化，将在 {@link #passDialog(Dialog<T>)} 之后，{@link #fill(T)} 之前调用
     */
    void init();

    default void fill(T t){
        // 默认不做填充
    }

    /**
     * 获得内容是否有效的布尔值只读属性，展现面板将根据该属性决定是否使能确定按钮
     */
    ReadOnlyBooleanProperty allValid();

    /**
     * 设置结果转换器，根据点击按钮的类型，返回结果
     */
    default T resultConverter(ButtonType type) {
        if (!type.getButtonData().isCancelButton())
            return pack();
        return null;
    }

    /**
     * 将数据进行打包，并封装为一个 T 对象
     * 需要注意的是，那些默认的值也要上哦
     */
    default T pack(){
        return null;
    }
}
