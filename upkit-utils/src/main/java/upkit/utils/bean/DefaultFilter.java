package upkit.utils.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @desc 默认过滤器
 **/
public  class DefaultFilter implements FieldFilter,MethodFilter{

    @Override
    public boolean filter(Field field) {
        // 如果字段为静态或者被标注为需要过滤时
        return  Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(InvokeExclude.class);
    }

    @Override
    public boolean filter(Method method) {
        return method.isAnnotationPresent(InvokeExclude.class);
    }
}
