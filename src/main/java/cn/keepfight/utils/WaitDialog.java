package cn.keepfight.utils;

import java.util.Optional;
import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WaitDialog<V> extends Dialog<Boolean> implements EventHandler<WorkerStateEvent> {

    private static final String waitIcon = "wait.png";
    private Supplier<V> action;

    public WaitDialog(Supplier<V> action) {
        setTitle("正在执行");
        setHeaderText("这个操作可能需要花费些时间，请稍等待...");
        setGraphic(ImageLoadUtil.bindImage(new ImageView(), waitIcon, ImageLoadUtil.IMG_SIZE_64));

        ButtonType buttonTypeCancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(buttonTypeCancel);

        //进程指示器
        ProgressIndicator indic = new ProgressIndicator();
        BorderPane borderPane = new BorderPane(indic);

        getDialogPane().setContent(borderPane);

        Stage stage = (Stage) getDialogPane().getScene().getWindow();

        // Add a custom icon.
        ObjectProperty<Image> image = ImageLoadUtil.load(waitIcon, ImageLoadUtil.IMG_SIZE_16);
        Platform.runLater(() -> stage.getIcons().add(image.getValue()));

        setResultConverter(dialogButton -> false);

        this.action = action;
    }

    /**
     * as it said, just wait.
     * this method will block the thread to wait finishing of task action.
     *
     * @return an optional which indicate whether task finishes or interrupts for exceptions
     */
    public Optional<V> justWait() {
        Task<V> task = new Task<V>() {
            @Override
            protected V call() throws Exception {
                return action.get();
            }
        };
        task.setOnSucceeded(this);
        new Thread(task).start();

        Optional<Boolean> result = showAndWait();

        if (result.isPresent() && result.get()) {
            return Optional.ofNullable(task.getValue());
        }

        return Optional.empty();
    }

    /**
     * 设置头部文字信息
     */
    public WaitDialog<V> setDetail(String title, String headerText) {
        setTitle(title);
        setHeaderText(headerText);
        return this;
    }

    /**
     * 设置头部图标
     */
    public WaitDialog<V> setIcon(String icon) {
        try {
            ImageLoadUtil.bindImageDirectly((ImageView) getGraphic(), icon);
        }catch (Exception e){}
        return this;
    }

    @Override
    public void handle(WorkerStateEvent event) {
        setResult(Boolean.TRUE);
        close();
    }

}
