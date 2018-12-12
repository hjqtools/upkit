package upkit.utils;

import java.util.Random;
import java.util.UUID;

import org.joda.time.DateTime;

/**
 * 生成ID 工具
 * 
 * @author melody
 *
 */
public final class IDUtil {

	private static final char[] LETTERS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '+', '-' };

	/**
	 * 使用当前时间生成的毫秒数以及随机三位数字 合成ID
	 */
	public static String genIDByCurMillions() {
		// 取当前时间的长整形
		long millis = System.currentTimeMillis();
		// long millis = System.nanoTime();
		// 加上三位随机
		Random random = new Random();
		int end3 = random.nextInt(999);
		// 如果不足三位前面补零
		String str = millis + String.format("%03d", end3);
		return str;
	}

	/**
	 * 使用UUID生成ID 没有破折号
	 * 
	 * @return
	 */
	public static String getUUIDWithout55() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 使用UUID生成ID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 使用当前时间yyyyMMddHHmmss 以及随机三位数字 合成ID
	 * 
	 * @return
	 */
	public static String genIDByCurTime() {
		String curTime = new DateTime().toString("yyyyMMddHHmmss");
		// 加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		// 如果不足三位前面补0
		String str = curTime + String.format("%03d", end3);
		return str;
	}

	/**
	 * 商品id生成
	 */
	public static long genItemId() {
		// 取当前时间的长整形
		long millis = System.currentTimeMillis();
		// long millis = System.nanoTime();
		// 加上两位随机数
		Random random = new Random();
		int end2 = random.nextInt(99);
		// 如果不足两位前面补0
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return id;
	}

	public static void main(String[] args) {
		String id = getUUIDWithout55();
		// String id2 = getUUID();
		System.out.println(id.length());
		// System.out.println(id2);

		String ids = genIDByCurMillions();
		System.out.println(ids);
		// System.out.println(ids.length());

		String ids2 = genIDByCurMillions();
		System.out.println(ids2);
		// System.out.println(ids2.length());

		System.out.println(LETTERS.length);
	}

}
