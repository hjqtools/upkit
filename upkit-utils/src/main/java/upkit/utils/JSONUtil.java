package upkit.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @description:  使用fastjson 作为json util
 * @author: melody
 * @email:  cuzart@163.com
 */
public final class JSONUtil {

	/**
	 * 从json数组中获取string类型的数组
	 *
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static String[] getStringArrFromJson(String jsonStr) {
		try {
			if (StringUtils.isBlank(jsonStr)) {
				return null;
			}
			List<?> objectList = JSONArray.parseArray(jsonStr, String.class);
			if (objectList == null || objectList.size() <= 0) {
				return null;
			}
			return objectList.toArray(new String[objectList.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {

		List<String> ss = new ArrayList<>();
		ss.add("dfds");
		ss.add("3fdf");
		ss.add("hdgf");

		String s = JSON.toJSONString(ss);
		System.out.println(s);

		try {
			String[] arrayFromJsonStr = getStringArrFromJson(s);
			String s1 = Arrays.toString(arrayFromJsonStr);
			System.out.println(s1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
