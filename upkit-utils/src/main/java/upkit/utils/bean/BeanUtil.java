package upkit.utils.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import upkit.utils.AssertUtil;
import upkit.utils.ReflectionUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * 
 * @description:  bean 对象工具
 * @author: melody
 * @email:  cuzart@163.com
 */
public class BeanUtil {

	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	// bean对象校验使用java自带校验
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 深度克隆
	 *
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deepClone(final T obj) {
		AssertUtil.notNull(obj, "对象不能为空");
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;

		// 将obj对象序列化成为一个流，再写出成一个对象，实现对象的拷贝
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			// 将流序列化成对象
			byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			Object returnObj = objectInputStream.readObject();
			return (T) returnObj;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				byteArrayOutputStream = null;
				byteArrayInputStream = null;
				if (objectOutputStream != null) {
					objectOutputStream.close();
				}
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			} catch (IOException e2) {
				logger.error(e2.getMessage(), e2);
			}
		}
		return null;
	}

	/**
	 * 校验bean对象是否被填充满
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isFilled(final Object obj, Class<?> clazz) {
		AssertUtil.notNull(obj, "对象不能为空。");
		AssertUtil.notNull(clazz, "类不能为空。");
		if (!obj.getClass().equals(clazz)) {
			throw new IllegalArgumentException(obj + "对象不属于该类型。" + clazz);
		}
		Field[] declaredFields = clazz.getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			Object fieldValue = ReflectionUtil.getFieldValue(obj, declaredFields[i].getName());
			if (fieldValue == null) {
				return false;
			}
		}
		return true;
	}

	public static void copyWithSameField(Object from, Object to) {

	}

	/**
	 * 校验bena对象是否符合注解规范
	 *
	 * @param t
	 * @param <T>
	 */
	public static <T> void validate(T t) throws ValidationException {
		AssertUtil.notNull(t, "校验对象不能为空。");
		Set<ConstraintViolation<T>> set = validator.validate(t);
		if (set.size() > 0) {
			StringBuilder validateError = new StringBuilder();
			for (ConstraintViolation<T> val : set) {
				validateError.append(val.getMessage() + ";  ");
			}
			throw new ValidationException(validateError.toString());
		}
	}

	public static void main(String[] args) {
		// WdElasticsearch wdElasticsearch = new WdElasticsearch();
		//// wdElasticsearch.setDataSourceTypeId(1232323L);
		// wdElasticsearch.setElasticsearchAnalysisPattern(AnalysisPattern.LOCAL_PATTERN);
		// wdElasticsearch.setElasticsearchClusterName("melody");
		// wdElasticsearch.setElasticsearchIndex("dsfdsf");
		// wdElasticsearch.setElasticsearchInetAddress("345435345213");
		// wdElasticsearch.setElasticsearchPort(242434);
		// wdElasticsearch.setElasticsearchId(32534543L);
		// wdElasticsearch.setElasticsearchType("");

		// ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		// validator = vf.getValidator();
		// boolean b = BeanUtil.beanIsFilled(wdElasticsearch, WdElasticsearch.class);
		// BeanUtil.validateBean(wdElasticsearch);

		// System.out.println(b);
	}

}
