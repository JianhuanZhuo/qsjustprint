package cn.keepfight.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties utility class
 *
 * @author Tom
 */
public class PropertieUtil {

    public static final String PROPERTIES_RESOURCE_PATH = "conf/";

    /**
     * load a properties file by specifying a file name under {@link #PROPERTIES_RESOURCE_PATH}
     *
     * @param file the file name which will be loaded
     * @return Properties object
     */
    public static Properties loadProperties(String file) {
        Properties resProperties = new Properties();

        // construct URL
        String filePath = PROPERTIES_RESOURCE_PATH + file;

        //XXX 这里可以是更简单的配置方式
        //load properties file
        try (InputStream in = PropertieUtil.class.getClassLoader().getResourceAsStream(filePath)) {
            resProperties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resProperties;
    }

    public synchronized static void alterProperty(String file, String key, String value) {
        // construct URL
        String filePath = PROPERTIES_RESOURCE_PATH + file;

        Properties props = loadProperties(file);
        String f = PropertieUtil.class.getClassLoader().getResource(filePath).getFile();
        try (FileOutputStream out = new FileOutputStream(f)) {
            props.setProperty(key, value);
            props.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * load a properties file by specifying a file name under {@link #PROPERTIES_RESOURCE_PATH}
     * <br/>
     * optionally, it could be passed another parameter defaultFile to try to read another file
     * when exception occur.
     *
     * @param file        the file name which will be loaded
     * @param defaultFile default properties file which may be could contain configuration
     * @return Properties object
     */
    public static Properties loadProperties(String file, String defaultFile) {
        try {
            return loadProperties(file);
        } catch (Exception e) {
            return loadProperties(defaultFile);
        }
    }
}
