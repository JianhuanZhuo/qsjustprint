package cn.keepfight.utils;

import cn.keepfight.qsmanager.model.OrderItemModel;
import cn.keepfight.qsmanager.model.ReceiptDetailModel;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

/**
 * FX 反射相关工具方法类
 * Created by tom on 2017/6/22.
 */
public class FXReflectUtils {

    public static<T> void connectForColumn(TableColumn<T, String> tab_col){
        tab_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<T, String> param) {
                ObservableValue x = search("tab_col", param.getValue());
                return x;
            }
        });
    }

    public static ObservableValue search(String name, Object obj){
        Method[] ms = obj.getClass().getDeclaredMethods();
        for (Method m : ms) {
            m.setAccessible(true);
            if (m.getName().equals(name+"Property")){
                System.out.println("m.getReturnType().getClass():"+m.getReturnType().getClass());
                if (StringProperty.class.equals(m.getReturnType().getClass())){
                    System.out.println("");
                }
            }
            try {
                return (ObservableValue<String>) m.invoke(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将 tab_serial 映射到 serialProperty 方法
     * tab 开头的全部 TableColumn 对象
     * <pre>
     * tab_serial.setCellValueFactory(param->param.getValue().getPrice().serialProperty())
     * </pre>
     * @param clazz
     * @param <T>
     */
    public static<T> void connect(Class<T> clazz, Object obj){
        System.out.println("f !!!!!!!!!!!!!!!!!!!!!!!! obj.getClass():"+obj.getClass());
        Field[] fs = obj.getClass().getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            System.out.println("f !!!!!!!!!!!!!!!!!!!!!!!! name:"+f.getName());
            System.out.println("f !!!!!!!!!!!!!!!!!!!!!!!! name:"+f.getClass());
            System.out.println("f !!!!!!!!!!!!!!!!!!!!!!!! name:"+f.getType());
        }
        TableColumn<ReceiptDetailModel, String> tab_name;

        Arrays.stream(fs)
                .filter(f->f.getType().equals(TableColumn.class))
                .filter(f->f.getName().startsWith("tab_"))
                .forEach(f->{
                    System.out.println(f);
                });
//        try {
//            Field x = clazz.getDeclaredField("");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
    }
}
