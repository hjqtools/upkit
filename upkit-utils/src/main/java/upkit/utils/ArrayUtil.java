package upkit.utils;

/**
 * 数组工具
 * 
 * @author melody
 *
 */
public class ArrayUtil {

	/**
	 * 判断数组中是否存在元素
	 * 
	 * @param arr
	 * @return 为空返回真
	 */
	public static <T> boolean isEmpty(T[] arr) {
		if (arr == null || arr.length <= 0) {
			return true;
		}
		boolean flag = true;
		for (T t : arr) {
			if (t != null) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public static <T> boolean isNotEmpty(T[] arr) {
		return !isEmpty(arr);
	}

	public static void main(String[] args) {
		Integer[] aa = new Integer[10];
		boolean empty = isEmpty(aa);
		System.out.println(empty);
	}

}
