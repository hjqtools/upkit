package upkit.algo;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

/**
 * simHash 工具 判断文本海明距离 持久化simHash 二分查询simhash 非线程安全
 * 
 * @author melody
 *
 */
public final class SimHashUtil {

	private final static Logger logger = LoggerFactory.getLogger(SimHashUtil.class);

	private SimHashUtil() {
	}

	/**
	 * 普�?�单值hash
	 * 
	 * @param source
	 * @return
	 */
	private static BigInteger hash(String source) {
		if (source == null || source.length() == 0) {
			return new BigInteger("0");
		} else {
			char[] sourceArray = source.toCharArray();
			BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
			BigInteger m = new BigInteger("1000003");
			BigInteger mask = new BigInteger("2").pow(64).subtract(new BigInteger("1"));
			for (char item : sourceArray) {
				BigInteger temp = BigInteger.valueOf((long) item);
				x = x.multiply(m).xor(temp).and(mask);
			}
			x = x.xor(new BigInteger(String.valueOf(source.length())));
			if (x.equals(new BigInteger("-1"))) {
				x = new BigInteger("-2");
			}
			return x;
		}
	}

	/**
	 * simHash的主算法
	 * 
	 * @param tokens
	 * @return
	 */
	public static BigInteger getSimHash(List<Term> termList) {
		int[] v = new int[64];
		for (int i = 0; i < termList.size(); i++) {
			String temp = termList.get(i).word;
			BigInteger t = hash(temp);
			for (int j = 0; j < 64; j++) {
				BigInteger bitMask = new BigInteger("1").shiftLeft(j);
				if (t.and(bitMask).signum() != 0) {
					v[j] += 1;
				} else {
					v[j] -= 1;
				}
			}
		}

		BigInteger fingerprint = new BigInteger("0");
		StringBuffer simHashBuffer = new StringBuffer();
		for (int i = 0; i < 64; i++) {
			if (v[i] >= 0) {
				fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
				simHashBuffer.append("1");
			} else {
				simHashBuffer.append("0");
			}
		}
		String strSimHash = simHashBuffer.toString();
		logger.info(strSimHash + " length " + strSimHash.length());
		return fingerprint;
	}

	/**
	 * 计算两个文本编码集的海明距离
	 * 
	 * @param other
	 * @return
	 */
	public static int hammingDistance(BigInteger intSimHash, BigInteger intSimHashExist) {

		BigInteger x = intSimHash.xor(intSimHashExist);
		int tot = 0;

		while (x.signum() != 0) {
			tot += 1;
			x = x.and(x.subtract(new BigInteger("1")));
		}
		return tot;
	}

	private int getDistance(String str1, String str2) {
		int distance;
		if (str1.length() != str2.length()) {
			distance = -1;
		} else {
			distance = 0;
			for (int i = 0; i < str1.length(); i++) {
				if (str1.charAt(i) != str2.charAt(i)) {
					distance++;
				}
			}
		}
		return distance;
	}

	public static void main(String[] args) {
		// String contentRaw = "中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程";
		String contentRaw = "地方大风";
		List<Term> segment = HanLP.segment(contentRaw);
		BigInteger simHash = SimHashUtil.getSimHash(segment);
		System.out.println(simHash);
	}

}
