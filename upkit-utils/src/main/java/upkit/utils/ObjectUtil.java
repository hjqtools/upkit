package upkit.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object 工具类，并非BeanUtils
 * 
 * @author melody
 *
 */
public final class ObjectUtil {

	private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

	/**
	 * 1. 基于BeanUtis对象克隆 bean对象 忽视transient属性
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cloneObjByBeanUtils(T obj) {
		try {
			if (obj == null) {
				logger.info("克隆对象为空");
				return null;
			}
			T bean = (T) BeanUtils.cloneBean(obj);
			return bean;
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 2.基于ObjectOutputStream对象克隆 对象要实现Serializable接口 不会护士transient
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T cloneObjByStream(T obj) {
		if (obj == null) {
			logger.info("克隆对象为空");
			return null;
		}
		if (!(obj instanceof Serializable)) {
			throw new RuntimeException("对象未实现Serializable，无法进行深度克隆");
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		ByteArrayInputStream bais = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			// 将对象写入字节数组输出，进行序列化
			bais = new ByteArrayInputStream(baos.toByteArray());
			// 执行反序列化，从流中读取对象
			ois = new ObjectInputStream(bais);
			T oobj = (T) ois.readObject();
			return oobj;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
