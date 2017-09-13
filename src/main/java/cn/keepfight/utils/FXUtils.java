package cn.keepfight.utils;

import javafx.beans.Observable;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.WritableImage;
import javafx.util.StringConverter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * FX 工具集合类
 * Created by tom on 2017/6/6.
 */
public class FXUtils {
    /**
     * 添加节点样式，由于 FXML 中样式的存储以 List 存储，所以可能导致多次存入相同样式的可能
     *
     * @param style 指定需要添加的样式
     * @param node  需要进行样式添加操作操作的节点列表
     */
    public static void addStyle(String style, Node... node) {
        Arrays.stream(node).forEach(n -> {
            if (!n.getStyleClass().contains(style)) {
                n.getStyleClass().add(style);
            }
        });
    }

    /**
     * 删除节点样式，由于 FXML 中样式的存储以 List 存储，所以可能导致多次存入相同样式的可能
     * @param style 指定需要删除的样式
     * @param node 需要进行样式删除操作操作的节点列表
     */
    public static void delStyle(String style, Node... node) {
        Arrays.stream(node).forEach(n -> {
            while (n.getStyleClass().contains(style)) {
                n.getStyleClass().remove(style);
            }
        });
    }

    public static <T, R> R getMapper(SqlSessionFactory sqlSessionFactory, Class<T> clazz, ConsumerTR<T, R> consumer) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return consumer.accept(session.getMapper(clazz));
        }
    }


    public static <T, P> void getMapper(SqlSessionFactory sqlSessionFactory, Class<T> clazz, ConsumerT<T, P> consumer, P p) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            consumer.accept(session.getMapper(clazz), p);
        }
    }

    public static <T, R, P> R getMapper(SqlSessionFactory sqlSessionFactory, Class<T> clazz, ConsumerTRP<T, R, P> consumer, P p) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return consumer.accept(session.getMapper(clazz), p);
        }
    }

    @FunctionalInterface
    public interface ConsumerTR<T, R> {
        R accept(T t) throws Exception;
    }

    @FunctionalInterface
    public interface ConsumerT<T, P> {
        void accept(T t, P p) throws Exception;
    }

    @FunctionalInterface
    public interface ConsumerTRP<T, R, P> {
        R accept(T t, P p) throws Exception;
    }


    /**
     * 限制输入控件的输入字符串长度
     */
    public static void limitLength(TextInputControl textInputControl, int limit) {
        textInputControl.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                // Check if the new character is greater than LIMIT
                if (textInputControl.getText().length() >= limit) {
                    // if it's 11th character then just setText to previous one
                    textInputControl.setText(textInputControl.getText().substring(0, limit));
                }
            }
        });
    }

    /**
     * 限制输入控件的输入字符串内容
     */
    public static void limitPattern(TextInputControl textInputControl, String pattern, String defaultStr) {
        textInputControl.setText(defaultStr);
        textInputControl.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue && textInputControl.getText()!=null) { //when focus lost
                if (!textInputControl.getText().trim().matches(pattern)) {
                    //when it not matches the pattern (1.0 - 6.0) set the textField empty
                    textInputControl.setText(defaultStr);
                }else {
                    textInputControl.setText(textInputControl.getText().trim());
                }
            }
        });
    }

    /**
     * 纯数字限制
     * @param inte             最大整数限制
     * @param deci             最大小数限制
     * @param allowNega        是否允许负数输入
     */
    public static void limitNum(TextInputControl textInputControl, int inte, int deci, boolean allowNega) {
        limitNum(textInputControl, inte, deci, allowNega, "0");
    }

    /**
     * 纯数字限制
     * @param inte             最大整数限制
     * @param deci             最大小数限制
     * @param allowNega        是否允许负数输入
     */
    public static void limitNum(TextInputControl textInputControl, int inte, int deci, boolean allowNega, String defaultValue) {
        if (inte < 1) {
            return;
        }
        String pattern = "\\d{1," + inte + "}";
        if (allowNega) {
            pattern = "-?" + pattern;
        }
        if (deci > 0) {
            pattern += "(\\.\\d{0," + deci + "})?";
        }
        limitPattern(textInputControl, pattern, defaultValue);
    }

//    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
//    private static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 转换时间戳为时期字符串
     */
    public static String stampToDate(Long stamp, String pattern){
        if (stamp==null){
            return "";
        }
        return stampToLocalDate(stamp).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 转换时间戳为时期字符串
     */
    public static String stampToDate(Long stamp){
        return stampToDate(stamp, "yyyy-MM-dd");
    }

    /**
     * 转换时间戳为日期时间字符串
     */
    public static String stampToDateTime(Long stamp){
        if (stamp==null){
            return "";
        }
        return stampToLocalDateTime(stamp).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 转时间戳为本地时间对象
     */
    public static LocalDate stampToLocalDate(Long stamp){
        if (stamp==null){
            stamp = System.currentTimeMillis();
        }
        return new Timestamp(stamp).toLocalDateTime().toLocalDate();
    }

    /**
     * 转时间戳为本地时间对象
     */
    public static LocalDateTime stampToLocalDateTime(Long stamp){
        if (stamp==null){
            stamp = System.currentTimeMillis();
        }
        return new Timestamp(stamp).toLocalDateTime();
    }

    /**
     * 带默认值的分数字符串转换，若转换错误则返回默认值
     * @param str 需要转换的分数
     * @param defaultValue 默认值
     */
    public static BigDecimal getDecimal(String str, BigDecimal defaultValue){
        try {
            return new BigDecimal(str);
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 带默认值的分数字符串转换，若转换错误则返回默认值
     * 重载方法默认值为0
     * @param str 需要转换的分数
     */
    public static BigDecimal getDecimal(String str){
        return getDecimal(str, new BigDecimal(0));
    }

    /**
     * 带默认值的分数字符串转换，若转换错误则返回默认值
     * 重载方法默认值为0
     * @param text 包含需要转换的分数的文本控件
     */
    public static BigDecimal getDecimal(TextField text){
        return getDecimal(text.getText(), new BigDecimal(0));
    }


    /**
     * 带默认值的长整形数字符串转换，若转换错误则返回默认值
     * @param str 需要转换的长整形字符串
     * @param defaultValue 默认值
     */
    public static long getLong(String str, long defaultValue){
        try {
            return Long.valueOf(str);
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 带默认值的长整形数字符串转换，若转换错误则返回默认值
     * 重载方法，默认为0
     * @param str 需要转换的长整形字符串
     */
    public static long getLong(String str){
        return getLong(str, 0);
    }

    public static String decimalStr(BigDecimal d){
        if (d==null){
            return "0";
        }else {
            return d.toString();
        }
    }

    /**
     * 将指定路径的本地图片进行另存为，注意是本地图片，其中图片名并不对应上
     * @param picUrl 图片的路径，如 D:/pic/xx.png
     * @return 包装后的图片URL，也就是 file:D:/xx/xx.png，或者 http:/xxx/xx/xx.png
     */
    public static String copyAndTransform(String picUrl) throws IOException {
        Path source = Paths.get(picUrl);
        String genFileName = generateName(source.getFileName().toString());
        File targetFile = new File("./pic/" + genFileName).getCanonicalFile();
        while (targetFile.exists()){
            genFileName = generateName(genFileName);
            targetFile = new File("./pic/" + genFileName).getCanonicalFile();
        }
        Path target = targetFile.toPath();
        Files.copy(source, target);
        // 添加 file: 本地文件连接
        return "file:" + targetFile.getAbsolutePath();
    }

    /**
     * 随机修改文件名
     */
    public static String generateName(String source){
        return "gen"+(source+System.currentTimeMillis()).hashCode()+ source.substring(source.lastIndexOf('.'));
    }

    /**
     * 将指定图片写入到文件中，并返回这个文件名
     */
    public static String writeImage(WritableImage image) throws IOException {
        String genFileName = "snapshot"+System.currentTimeMillis()+".png";
        File targetFile = new File("./pic/" + genFileName).getCanonicalFile();
        while (targetFile.exists()){
            genFileName = generateName(genFileName);
            targetFile = new File("./pic/" + genFileName).getCanonicalFile();
        }
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", targetFile);
        return targetFile.getAbsolutePath();
    }

    /**
     * 将指定图片写入到文件中，并返回这个文件名相对路径
     */
    public static String writeImageCanonical(WritableImage image) throws IOException {
        String filePath = "./pic/snapshot"+System.currentTimeMillis()+".png";
        File targetFile = new File(filePath).getCanonicalFile();
        while (targetFile.exists()){
            filePath = "./pic/" + generateName(filePath);
            targetFile = new File(filePath).getCanonicalFile();
        }
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", targetFile);
        return filePath;
    }

    /**
     * 构造转换器，在传入为 null 对象时使用空字符串作为转换返回值
     */
    public static <T> StringConverter<T> converter(Function<T, String> convert){
        return converter(convert, "");
    }

    /**
     * 构造转换器
     * @param nullValue 在传入对象为空时，默认的转换值
     */
    public static <T> StringConverter<T> converter(Function<T, String> convert, String nullValue){
        return new StringConverter<T>() {
            @Override
            public String toString(T object) {
                if (Objects.isNull(object)){
                    return nullValue;
                }else {
                    return convert.apply(object);
                }
            }

            @Override
            public T fromString(String string) {
                return null;
            }
        };
    }

    public static<T> void bindProperties(ObjectProperty<T> p, Supplier<T> s, Observable... os){
        p.bind(new ObjectBinding<T>() {
            {
                bind(os);
            }
            @Override
            protected T computeValue() {
                return s.get();
            }
        });
    }


    /**
     * 分数转换器
     * @param nullValue 当输入为 NULL 时，使用的默认输出
     */
    public static StringConverter<BigDecimal> decimalConverter(String nullValue){
        return new StringConverter<BigDecimal>() {
            @Override
            public String toString(BigDecimal o) {
                return Objects.isNull(o) ? nullValue : ("" + o);
            }
            @Override
            public BigDecimal fromString(String s) {
                try {
                    return new BigDecimal(s);
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }

    /**
     * 整数转换器
     * @param nullValue 当输入为 NULL 时，使用的默认输出
     */
    public static StringConverter<Integer> integerConverter(String nullValue){
        return new StringConverter<Integer>() {
            @Override
            public String toString(Integer o) {
                return Objects.isNull(o) ? nullValue : o.toString();
            }
            @Override
            public Integer fromString(String s) {
                try {
                    return Integer.valueOf(s);
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }


    /**
     * 税率转换器
     */
    public static StringConverter<BigDecimal> rateConverter(){
        return new StringConverter<BigDecimal>() {
            @Override
            public String toString(BigDecimal o) {
                return Objects.isNull(o) ? "0%" : ("" + o.movePointRight(2)+"%");
            }
            @Override
            public BigDecimal fromString(String s) {
                try {
                    return new BigDecimal(s.replace("%", "")).movePointLeft(2);
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }

    /**
     * 整数转换器
     * @param nullValue 当输入为 NULL 时，使用的默认输出
     */
    public static StringConverter<Long> longConverter(String nullValue){
        return new StringConverter<Long>() {
            @Override
            public String toString(Long o) {
                return Objects.isNull(o) ? nullValue : o.toString();
            }
            @Override
            public Long fromString(String s) {
                try {
                    return Long.valueOf(s);
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }


    /**
     * 时间搓转换器
     */
    public static StringConverter<Long> timestampConverter(){
        return new StringConverter<Long>() {
            @Override
            public String toString(Long o) {
                return Objects.isNull(o) ? "" : FXUtils.stampToDate(o);
            }
            @Override
            public Long fromString(String s) {
                try {
                    return Timestamp.valueOf(s).getTime();
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }

    public static int getNumAt(BigDecimal decimal, int place, boolean left){
        if (decimal==null){
            throw new RuntimeException("decimal is null!");
        }
        String ds = decimal.toString();
        int p = ds.indexOf(".");
        int kp = left?p-place:p+place;
        try {
            return Integer.valueOf(""+ds.charAt(kp));
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 对字符串进行包含了对 s 字符串的空处理，转化的异常处理，去除无效切分值
     * @param s 需要被切分的字符串
     * @param regex 切分的正则式
     * @param convert 转换函数
     * @return 切分后的列表
     */
    public static<T> List<T> split(String s, String regex, Function<String, T> convert){
//        if (s==null || s.trim().equals("")){
//            return new ArrayList<>(0);
//        }

        try {
            return Arrays.stream(s.split(regex))
                    .map(x->{
                        try {
                            return convert.apply(x);
                        }catch (Exception e){
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }catch (Exception e){
            return new ArrayList<>(0);
        }
    }
}
