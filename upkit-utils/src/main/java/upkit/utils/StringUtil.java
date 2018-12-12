package upkit.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;

/**
 * 
 * @description: 常用字符�? 值操作工�?
 * @author: melody
 * @email: cuzart@163.com
 */
public final class StringUtil {

	/**
	 * 抽取一个字符串中的url 比如 http https
	 *
	 * @param rawStr
	 * @return
	 */
	public static String[] extractUrl(String rawStr) {
		return null;
	}

	/**
	 * 是否为空，为空返回真，不为空返回假
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNull(final String str) {
		return (str == null) || ("".equals(str));
	}

	/**
	 * 除去两端的空格，并防止trim()方法抛出异常
	 *
	 * @param str
	 * @return
	 */
	public static String trim(final String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * <p>
	 * 检验字符序列是否是空格, empty ("") or null.
	 * </p>
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 */
	public static boolean isBlank(final CharSequence charSequence) {
		int strLen;
		if (charSequence == null /*-->null*/
				|| (strLen = charSequence.length()) == 0/*--> "" */) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			// 检车某个字符是否是空格
			if (Character.isWhitespace(charSequence.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查字符串是否匹配正则表达式
	 *
	 * @param str
	 *            要匹配的字符串
	 * @param regex
	 *            正则表达式
	 * @return
	 */
	public static boolean isMatcherRegex(final String str, final String regex) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}

	/**
	 * 返回所有被匹配到的字符串
	 *
	 * @param str
	 * @param regex
	 * @return
	 */
	public static ArrayList<String> getAllMatcherStr(final String str, final String regex) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> list = new ArrayList<String>();
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list;
	}

	/**
	 * 返回regex在orig中出现的次数
	 *
	 * @param orig
	 * @param regex
	 * @return
	 */
	public static int getContainNum(final String orig, final CharSequence regex) {
		return orig.split((String) regex).length - 1;
	}

	/**
	 * 根据提供的参数，生成md5值</br>
	 * 会对传过来的值用UTF-8方式编码
	 *
	 * @param source
	 * @return 正常的字符串，出错会返回null
	 */
	public static String getMD5Code(final String str) {
		byte[] source;
		try {
			source = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char strs[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				strs[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				strs[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}

			s = new String(strs); // 换后的结果转换为字符串

		} catch (Exception e) {
		}
		return s;
	}

	/**
	 * 将字符串转移为ASCII码
	 *
	 * @param cnStr
	 * @return
	 */
	public static String getCnASCII(final String cnStr) {
		StringBuffer strBuf = new StringBuffer(); // 线程安全的与StringBuilder相比速度较慢
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// System.out.println(Integer.toHexString(bGBK[i]&0xff));
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	/**
	 * 验证某字符串是否符合邮箱格式
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmail(final String str) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 验证某字符串是否符合手机格式
	 *
	 * @param str
	 * @return
	 */
	public static boolean isMobile(final String str) {
		String regular = "1\\d{10}";
		Pattern pattern = Pattern.compile(regular);
		boolean flag = false;
		if (str != null) {
			Matcher matcher = pattern.matcher(str);
			flag = matcher.matches();
		}
		return flag;
	}

	/**
	 * 删除掉JSON串前后的[]，某些插件不能解析带[]的json格式
	 *
	 * @param json
	 * @return
	 */
	public static String treatJson(final String tempjson) {
		String json = tempjson;
		if (null == json || json.length() < 3) {
			return "";
		}
		json = json.substring(1, json.length());
		json = json.substring(0, json.length() - 1);
		return json;
	}

	/**
	 * 返回字符串的Base64编码
	 *
	 * @param str
	 * @return
	 */
	public static String getBase64(final String str) {
		if (isNull(str)) {
			throw new IllegalArgumentException("StringUtil.getBase64（）参数不能为空!");
		}
		return (new sun.misc.BASE64Encoder()).encode(str.getBytes());
	}

	/**
	 * 返回Base64编码解码后的字符串
	 *
	 * @return
	 */
	public static String getFromBase64(final String str) {
		if (isNull(str)) {
			throw new IllegalArgumentException("StringUtil.getFromBase64()参数不能为空!");
		}
		BASE64Decoder decoder = new BASE64Decoder();

		byte[] b = null;
		try {
			b = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new String(b);
	}

}
