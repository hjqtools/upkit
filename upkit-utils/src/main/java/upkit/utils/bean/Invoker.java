package upkit.utils.bean;

/**
 * @desc  通用调用接口
 */
public interface Invoker<T> {

    /**
     * 调用方法
     * @param host  执行对象
     * @param args  执行参数u
     * @return
     */
    Object invoke(T host,Object...args);

}
