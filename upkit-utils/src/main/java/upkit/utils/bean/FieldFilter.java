package upkit.utils.bean;

import java.lang.reflect.Field;

/**
 * @desc   字段过滤
 */
public interface FieldFilter {

    boolean filter(Field field);
}

