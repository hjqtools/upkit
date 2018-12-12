package upkit.utils.bean;

import java.lang.reflect.Method;

/**
 * @desc 方法过滤器
 */
public interface MethodFilter {

     boolean filter(Method method);

}
