
package upkit.utils.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time Oct 19, 2016
 * @Desc 方法Invoker帮助类,自动生成某个Class所有公有方法的调用器<br/>
 * 		@1:允许进行两个对象间属性的copy，BeanInvokeUtils.copyBean(src,dest);<br/>
 * 		@2:插入自定义类型转换的时候需要继承Typeconverter类并复写convert方法.<br/>
 * 		@3:可以用于替换反射方式(Method.invoke(...)),根据对象信息动态调用某个对象的某个方法.BeanInvokeUtils.findBeanMethodInvoke(Class
 *       <?>).getMethodInvoker(methodName,paramTypes).invoke(hostObj,argus...);
 */
public class BeanInvokeUtils {

	private static final Logger logger = LoggerFactory.getLogger(BeanInvokeUtils.class);
	
	private static LoadingCache<KeyWrapper<?>, BeanMethodInvoke<?>> beanInvokerCache = CacheBuilder.newBuilder()
			.maximumSize(1000).expireAfterAccess(30, TimeUnit.DAYS)
			.build(new CacheLoader<KeyWrapper<?>, BeanMethodInvoke<?>>() {
				@Override
				public BeanMethodInvoke<?> load(KeyWrapper<?> keyWrapper) throws Exception {
					return new BeanMethodInvoke<>(keyWrapper.getHostClass(), keyWrapper.getFieldFilter(),
							keyWrapper.getMethodField());
				}
			});

	@SuppressWarnings("unchecked")
	public static <T> BeanMethodInvoke<T> findBeanMethodInovker(Class<T> hostClass) {
		try {
			return (BeanMethodInvoke<T>) beanInvokerCache.get(new KeyWrapper<T>(hostClass));
		} catch (ExecutionException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> BeanMethodInvoke<T> findBeanMethodInovker(Class<T> hostClass, FieldFilter fieldFilter,
			MethodFilter methodFilter) {
		try {
			return (BeanMethodInvoke<T>) beanInvokerCache.get(new KeyWrapper<T>(hostClass, fieldFilter, methodFilter));
		} catch (ExecutionException e) {
			return null;
		}
	}

	/**
	 * 用于两个实体对象之间进行属性的copy,暂时只针对同名属性,不进行不同名属性间的映射操作.
	 * 
	 * 默认提供 str->num ,num --> str , long/int -->date,java.sql.Date,timestamp ，
	 * date,java.sql.Date,timestamp ---> int/long
	 * 之间的转换,自定义属性转换器需要继承Typeconverter.
	 * 
	 * @param src
	 * @param dest
	 */
	public static void copyBean(Object src, Object dest) {
		copyBean(src, dest, new Typeconverter() {
			@Override
			public Object convert(String fieldName, Type sourceFieldType, Type destFieldType, Object value) {
				return value;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> copyToMap(Object src, String... fieldNames) {
		BeanMethodInvoke<Object> srcMethodInvoker = (BeanMethodInvoke<Object>) findBeanMethodInovker(src.getClass());
		fieldNames = GenericsUtils.isNullOrEmpty(fieldNames)
				? srcMethodInvoker.fieldNames.toArray(new String[srcMethodInvoker.fieldNames.size()]) : fieldNames;
		Map<String, Object> retValueMap = Maps.newHashMap();
		for (String fieldName : fieldNames) {
			try {
				retValueMap.put(fieldName, srcMethodInvoker.getFieldReader(fieldName).invoke(src));
			} catch (Exception e) {
				log.info("can not read field's() value cause by :{} ", fieldName, e);
			}
		}
		return retValueMap;
	}

	@SuppressWarnings("unchecked")
	public static void copyBean(Object src, Object dest, Typeconverter typeConverter) {
		if (src == null || dest == null) {
			return;
		}
		Class<?> srcHostClass = src.getClass();
		Class<?> destHostClass = dest.getClass();
		BeanMethodInvoke<Object> srcMethodInvoker = (BeanMethodInvoke<Object>) findBeanMethodInovker(srcHostClass);
		BeanMethodInvoke<Object> destMethodInovker = (BeanMethodInvoke<Object>) findBeanMethodInovker(destHostClass);
		List<String> destFieldNames = destMethodInovker.getFieldNames();
		for (String fieldName : srcMethodInvoker.getFieldNames()) {
			log.debug("fieldName is :{} ", fieldName);
			Invoker<Object> srcReader = null;
			try {
				srcReader = srcMethodInvoker.getFieldReader(fieldName);
			} catch (Exception e) {
				if (!destFieldNames.contains(fieldName)) {
					log.info("1111 can not find :{} in destObj:{}", fieldName, destHostClass.getName());
					continue;
				}
			}
			Invoker<Object> fieldWriter = null;
			try {
				if (!destFieldNames.contains(fieldName)) {
					logger.info("2222 can not find :{} in destObj:{}", fieldName, destHostClass.getName());
					continue;
				}
				fieldWriter = destMethodInovker.getFieldWriter(fieldName);
			} catch (Exception e) {

			}
			if (fieldWriter != null) {
				Object setValue = srcReader.invoke(src);
				// 应该设置默认的转换器
				if (typeConverter != null) {
					setValue = typeConverter.superConvert(fieldName, srcMethodInvoker.getFieldType(fieldName),
							destMethodInovker.getFieldType(fieldName), setValue);
				}
				logger.debug("set value is :{} ", setValue);
				fieldWriter.invoke(dest, setValue);
			}
		}
	}

	@Data
	static class KeyWrapper<T> {
		Class<T> hostClass;
		FieldFilter fieldFilter;
		MethodFilter methodField;

		public KeyWrapper(Class<T> hostClass) {
			this.hostClass = hostClass;
		}

		public KeyWrapper(Class<T> hostClass, FieldFilter fieldFilter, MethodFilter methodFilter) {
			this.hostClass = hostClass;
			this.fieldFilter = fieldFilter;
			this.methodField = methodFilter;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((hostClass == null) ? 0 : hostClass.hashCode());
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			KeyWrapper<T> other = (KeyWrapper<T>) obj;
			if (hostClass == null) {
				if (other.hostClass != null)
					return false;
			} else if (!hostClass.equals(other.hostClass))
				return false;
			return true;
		}
	}

	public static Map<String, Object> transBean2Map(Object obj, String... excludeFields) {
		if (obj == null) {
			return null;
		}
		List<String> excludeFieldList = Lists.newArrayList();
		if (GenericsUtils.notNullAndEmpty(excludeFields)) {
			excludeFieldList.addAll(Arrays.asList(excludeFields));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (excludeFieldList.contains(key)) {
					continue;
				}
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (value == null) {
						continue;
					}
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("transBean2Map Error " + e);
		}
		return map;
	}

}
