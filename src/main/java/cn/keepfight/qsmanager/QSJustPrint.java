package cn.keepfight.qsmanager;

import cn.keepfight.qsmanager.just.JustPrintController;
import cn.keepfight.utils.ViewPathUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * 晴旭管理软件类
 * Created by tom on 2017/6/5.
 */
public class QSJustPrint extends Application {

    /**
     * 主面板控制器
     */
    public static JustPrintController justPrintController;

    public static Stage primaryStage;

    /**
     * 系统退出时执行动作列表
     */
    private static List<Runnable> stopActionList = new ArrayList<>(4);

    @Override
    public void start(Stage primaryStage) throws Exception {
        QSJustPrint.primaryStage = primaryStage;
        FXMLLoader loader = ViewPathUtil.getLoader("just_print.fxml");
        primaryStage.setScene(new Scene(loader.load()));
        justPrintController = loader.getController();
        primaryStage.show();

        BillListServer.getInstance().loadFromFile();
    }

    @Override
    public void stop(){
        BillListServer.getInstance().storeToFile();
        PropertiesServer.getInstance().storeToFile();
    }
}
