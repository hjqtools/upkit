package upkit.bp.apache.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import upkit.bp.apache.User;
import upkit.bp.apache.UserReduce;

/**
 * 属性工具 测试
 * 
 * @author HUJIANQING
 *
 */
public class PropertiesUtilsDemo {

	public static void main(String[] args)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		User user = new User();
		user.setAge(123);
		user.setBirthDay(new Date());
		user.setName("大大大");

		UserReduce userReduce = new UserReduce();
		PropertyUtils.copyProperties(userReduce, user);
		
		System.out.println(userReduce);
	}

}
