package cn.keepfight.qsmanager;

import cn.keepfight.utils.ConfigUtil;
import cn.keepfight.utils.PropertieUtil;

import java.sql.Timestamp;
import java.util.Properties;

/**
 * Created by tom on 2017/9/13.
 */
public class PropertiesServer {
    private Properties ps;
    private static PropertiesServer instance = new PropertiesServer();
    public static PropertiesServer getInstance() {
        return instance;
    }

    private PropertiesServer(){
        ps = ConfigUtil.load("server.properties");
    }

    public Properties getPS(){
        return ps;
}

    public int getNum(){
        int num = Integer.valueOf(ps.getProperty("serial.num")) ;
        ps.setProperty("serial.num", ""+(++num));
        return num;
    }

    public String getNumStr(){
        int year = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate().getYear();
        return String.format("00%d%05d", year, getNum());
    }

    public void storeToFile(){
        ConfigUtil.store("server.properties", ps);
    }
}
