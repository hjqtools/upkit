package upkit.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @description: 借鉴spring
 * @author: melody
 * @email: cuzart@163.com
 */
public final class AssertUtil {

	
	/**
	 * 测试对象是否不为如果为空则抛出异常
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
	 * 字符串是否有长度
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasLength(String str) {
		return (str != null && !str.isEmpty());
	}

	/**
	 * 判断是否包含某个字符串并设置异常信息
	 * 
	 * @param text
	 * @param message
	 */
	public static void hasText(String text, String message) {
		if (hasText(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断字符串判断并判断否包含某个字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasText(String str) {
		return (hasLength(str) && containsText(str));
	}

	/**
	 * 判断字符是否不为空白或"" " " ，为空白则返回异�?
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
	 * 是否包含某个字符�?
	 * 
	 * @param str
	 * @return
	 */
	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为真如果不为真则抛异�?
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