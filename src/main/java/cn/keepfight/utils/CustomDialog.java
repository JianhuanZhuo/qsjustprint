package cn.keepfight.utils;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;

import java.util.Optional;

/**
 * 自定义面板
 * Created by tom on 2017/6/7.
 */
public class CustomDialog {

    private String title;
    private String head;
    private String graph;

    private String okStr = "确定";
    private String cancelStr = "取消";

    public static CustomDialog gen() {
        return new CustomDialog();
    }

    public static CustomDialog gen(String head, String graph) {
        CustomDialog res = new CustomDialog();
        res.setGraph(graph);
        res.setHead(head);
        return res;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOkStr(String str){
        okStr = str;
    }

    public void setCancelStr(String cancelStr) {
        this.cancelStr = cancelStr;
    }

    public <T> Optional<T> build(DialogContent<T> content, T initData) {
        if (content==null) {
            new RuntimeException("empty DialogContent to build!").printStackTrace();
            return Optional.empty();
        }
        //初始化内容
        Dialog<T> dialog = new Dialog<>();
        content.passDialog(dialog);
        content.init();
        content.fill(initData);
        return construct(content, dialog);
    }

    public <T> Optional<T> build(DialogContent<T> content) {
        if (content==null) {
            new RuntimeException("empty DialogContent to build!").printStackTrace();
            return Optional.empty();
        }
        //初始化内容
        Dialog<T> dialog = new Dialog<>();
        content.passDialog(dialog);
        content.init();
        return construct(content, dialog);
    }

    private <T> Optional<T> construct(DialogContent<T> content,  Dialog<T> dialog) {
        dialog.setTitle(title);
        dialog.setHeaderText(head);
        if (graph != null)
            dialog.setGraphic(ImageLoadUtil.bindImage(new ImageView(), graph));

        // 添加按钮
        ButtonType okButtonType = new ButtonType(okStr, ButtonBar.ButtonData.OK_DONE);
        ButtonType canButtonType = new ButtonType(cancelStr, ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, canButtonType);

        dialog.getDialogPane().setContent(content.getContent());

        //有效性验证
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.disableProperty().bind(content.allValid().not());

        // 设置转换器
        dialog.setResultConverter(content::resultConverter);

        // 自适配尺寸调用
        
        return dialog.showAndWait();
    }
}
