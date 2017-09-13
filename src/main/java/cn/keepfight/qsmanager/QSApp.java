package cn.keepfight.qsmanager;

import cn.keepfight.qsmanager.service.LocalServiceImpl;
import cn.keepfight.qsmanager.service.ServerService;
import cn.keepfight.utils.ViewPathUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * 晴旭管理软件类
 * Created by tom on 2017/6/5.
 */
public class QSApp extends Application {

    /**
     * 主面板控制器
     */
    public static MainPane mainPane;

    /**
     * 服务器服务资源
     */
    public static ServerService service = new LocalServiceImpl();

    public static Stage primaryStage;

    /**
     * 系统退出时执行动作列表
     */
    private static List<Runnable> stopActionList = new ArrayList<>(4);

    @Override
    public void start(Stage primaryStage) throws Exception {
        QSApp.primaryStage = primaryStage;
        FXMLLoader loader = ViewPathUtil.getLoader("main.fxml");
        BorderPane borderPane = loader.load();
        mainPane = loader.getController();
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        stopActionList.forEach(Runnable::run);
        stopActionList.clear();
    }

    /**
     * 添加系统退出时的动作
     */
    public static synchronized void addStopAction(Runnable r){
        stopActionList.add(r);
    }
}
