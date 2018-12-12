package upkit.utils.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * @author melody
 * @desc 基本对象检测工具
 **/
public final class AssertUtil {

	/**
	 * 测试对象是否不为空 如果为空则抛出异常
	 *
	 * @param object
	 * @param message
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 如果为空 则返回真 否则抛异常
	 * 
	 * @param object
	 * @param message
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断字符是否不为空白或者不为"" " " ，为空白则返回异常
	 *
	 * @param str
	 * @param message
	 */
	public static void notBlank(String str, String message) {
		if (StringUtils.isBlank(str)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断是否为真如果不为真则抛异常
	 *
	 * @param expression
	 * @param message
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

}
