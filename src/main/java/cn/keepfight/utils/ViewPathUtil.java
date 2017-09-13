package cn.keepfight.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javafx.fxml.FXMLLoader;


/**
 * 视图加载
 */
public class ViewPathUtil {

    private static Properties properties = PropertieUtil.loadProperties("fxapp.properties");
    private static String path = properties.getProperty("view.path");
    private static ViewPathUtil instance = new ViewPathUtil();

    /**
     * 私有化构造器
     */
    private ViewPathUtil() {
    }

    public static ViewPathUtil getInstance() {
        return instance;
    }

    /**
     * 指定frame视图名，获得对应的URL路径
     *
     * @param viewURL 视图名
     * @return URL路径
     */
    public static URL getFrameView(String viewURL) {
        //@TODO 做出配置的方式
        if (!viewURL.contains(".fxml")) {
            System.out.println(viewURL + " do not contain .fxml! It's may wrong!");
        }
        return instance.getClass().getClassLoader().getResource(path + viewURL);
    }

    /**
     * 指定frame视图名，加载视图实例
     *
     * @param viewURL 视图名
     * @return 视图实例
     * @throws IOException IO异常
     */
    public static Object loadView(String viewURL) throws IOException {
        return getLoader(viewURL).load();
    }

    /**
     * 指定frame视图名，获得该视图加载器
     *
     * @param viewURL 视图名
     * @return 加载器
     */
    public static FXMLLoader getLoader(String viewURL) {
        System.out.println("View--------------------getLoader:"+viewURL);
        return new FXMLLoader(ViewPathUtil.getFrameView(viewURL));
    }

    public static <T> T loadViewForController(String viewURL) throws IOException {
        FXMLLoader loader = getLoader(viewURL);
        loader.load();
        return loader.getController();
    }
}
