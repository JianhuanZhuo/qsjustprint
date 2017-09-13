package cn.keepfight.utils;

import javafx.util.Pair;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * 配置工具类
 * 和属性工具类很像，但这是为了分离出来，因为它的职责是读不会被打包的文件的
 * 也就是外部配置
 * Created by tom on 2017/6/9.
 */
public class ConfigUtil {
    private static final String PROPERTIES_RESOURCE_PATH = "./conf/";

    private static File getFile(String file) throws IOException {
        return new File(PROPERTIES_RESOURCE_PATH + file).getCanonicalFile();
    }

    public static Properties load(String file) {
        Properties resProperties = new Properties();

        //load properties file
        try (InputStream in = new FileInputStream(getFile(file))) {
            resProperties.load(new InputStreamReader(in, "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resProperties;
    }

    public synchronized static void alter(String file, String key, String value) {

        Properties props = load(file);
        props.setProperty(key, value);

        try (FileOutputStream out = new FileOutputStream(getFile(file))) {
            props.store(new OutputStreamWriter(out, "UTF-8"), null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @SafeVarargs
    public synchronized static void alter(String file, Pair<String, String>... ps) {
        Properties props = load(file);
        for (Pair<String, String> p : ps) {
            props.setProperty(p.getKey(), p.getValue());
        }

        try (FileOutputStream out = new FileOutputStream(getFile(file))) {
            props.store(new OutputStreamWriter(out, "UTF-8"), null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public synchronized static void store(String file, Properties ps){
        if (file==null || file.trim().equals("") || ps==null){
            return;
        }

        try (FileOutputStream out = new FileOutputStream(getFile(file))) {
            ps.store(new OutputStreamWriter(out, "UTF-8"), null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
