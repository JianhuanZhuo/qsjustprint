package cn.keepfight.qsmanager.model;

/**
 * 拓展模型接口，扩展指定模型为全引用
 * Created by tom on 2017/6/15.
 * @param <T> 被扩展的模型类参数
 */
public interface ModelFull<T> {

    /**
     * 以 原模型的属性 对拓展模型进行初始化
     * @param t 包含原模型属性的对象。
     */
    void set(T t);

    /**
     * 获得全模型的原模型信息
     * @return 原模型信息，注意，该返回值为生成值，故<code>equal</code>成立，而引用比较不成立。
     */
    T get();
}
