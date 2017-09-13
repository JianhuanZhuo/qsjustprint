package cn.keepfight.utils;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片加载工具类
 * Created by tom on 2017/6/7.
 */
public class ImageLoadUtil {

    public static final int IMG_SIZE_16 = 16;
    public static final int IMG_SIZE_32 = 32;
    public static final int IMG_SIZE_64 = 64;

    private static final String VIEW_IMAGE_URL = "graph/";

    private static final Image preloadImage = new Image(VIEW_IMAGE_URL + "picture_preload.png");
    private static final Image loadfailImage = new Image(VIEW_IMAGE_URL + "picture_loadfail.png");

    public static final String LOCAL_PROTOCOL_PREFIX="sqlocal";

    /**
     * 图像缓存
     */
    private static Map<String, ObjectProperty<Image>> reusableImageMap = new HashMap<>();

    public static ObjectProperty<Image> load(String imageName) {
        return load(imageName, IMG_SIZE_32);
    }

    /**
     * 加载指定尺寸的图片，这种加载机制会缓存已加载完成的图片，<br/>
     * 对于第二次请求相同的图片，会直接使用之前加载的图片文件而不是从磁盘中再加载一次
     *
     * @param imageName 指定名字
     * @param size      指定尺寸，仅支持 {@link #IMG_SIZE_16} 和
     *                  {@link #IMG_SIZE_32}和{@link #IMG_SIZE_64}
     * @return 图片对象，若对象不存在返回null。
     */
    public static ObjectProperty<Image> load(String imageName, int size) {
        if (size == IMG_SIZE_16 || size == IMG_SIZE_32 || size == IMG_SIZE_64) {
            if (reusableImageMap.containsKey("g" + size + "/" + imageName)) {
                return reusableImageMap.get("g" + size + "/" + imageName);
            } else {
                String imageUrl = VIEW_IMAGE_URL + "g" + size + "/" + imageName;
                ObjectProperty<Image> resImage = new SimpleObjectProperty<>(preloadImage);

                // 在此做加载
                Platform.runLater(() -> {
                    try {
                        resImage.set(new Image(imageUrl));
                    } catch (Exception e) {
                        e.printStackTrace();
                        resImage.set(loadfailImage);
                    }
                });

                reusableImageMap.put("g" + size + "/" + imageName, resImage);
                return resImage;
            }
        }
        return null;
    }

    public static ImageView bindImage(ImageView view, String imageName) {
        return bindImage(view, imageName, IMG_SIZE_32);
    }

    public static ImageView bindImage(ImageView view, String imageName, int size) {
        if (view != null) {
            view.imageProperty().bind(load(imageName, size));
        }
        return view;
    }

    public static void bindImageDirectly(ImageView view, String imageUrl) {
        if (view != null) {
            ObjectProperty<Image> resImage = new SimpleObjectProperty<>(preloadImage);
            Platform.runLater(() -> {
                try {
                    String prefix = LOCAL_PROTOCOL_PREFIX+":";
                    if (imageUrl.startsWith(prefix)) {
                        File targetFile = new File(imageUrl.substring(prefix.length())).getCanonicalFile();
                        System.out.println("+_targetFile.toURI().toString()"+targetFile.toURI().toString());

                        resImage.set(new Image(targetFile.toURI().toString()));
                    } else {
                        resImage.set(new Image(imageUrl));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    resImage.set(loadfailImage);
                }
            });
            view.imageProperty().bind(resImage);
        }
    }

    public static void bindDefault(ImageView view) {
        if (view != null) {
            view.imageProperty().bind(new SimpleObjectProperty<>(preloadImage));
        }
    }
}
