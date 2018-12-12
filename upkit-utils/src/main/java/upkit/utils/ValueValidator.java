package upkit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数验证�?
 * 
 * @author qtom
 *
 */
public final class ValueValidator {
	
	/**
	 * 校验手机号码是否合规
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (phone.length() != 11) {
			System.out.println("手机号应为11位数");
			return false;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();

			if (!isMatch) {
				System.out.println("请填入正确的手机好号码");
			}
			return isMatch;
		}
	}


	/**
	 * 是否为合理的密码
	 * @param password
	 * @return
	 */
	public static boolean isPassword(String password) {
		String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{7,16}$";
		return password.matches(regex);
	}
	
	public static void main(String[] args) {
		boolean phone = ValueValidator.isPhone("15734222245");
//		System.out.println(phone);
		
		System.out.println(isPassword("qaz12345"));
	}
	
	
}
