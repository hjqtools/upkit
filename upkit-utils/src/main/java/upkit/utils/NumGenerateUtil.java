package upkit.utils;

import java.util.Random;
import java.util.Set;

/**
 * 
 * @Description: 随机生成数字 成工具类
 * @author: melody
 * @email: cuzart@163.com
 */
public final class NumGenerateUtil {

	/**
	 * 随机指定范围内N个不重复的数 生成数字后 遍历数组查重 最简单最基本的方法
	 *
	 * @param min
	 *            开始数
	 * @param max
	 *            结尾数
	 * @param n
	 *            需要生成的数
	 * @return
	 */
	public static int[] generatorCommon(int min, int max, int n) {
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}

	/**
	 * 利用HashSet的性质 去除重复
	 *
	 * @param min
	 * @param max
	 * @param n
	 * @param set
	 */
	public static void generateWithSet(int min, int max, int n, Set<Integer> set) {
		if (n > (max - min + 1) || max < min) {
			return;
		}
		for (int i = 0; i < n; i++) {
			// 调用Math.random()方法
			int num = (int) (Math.random() * (max - min)) + min;
			set.add(num);// 将不同的数存入HashSet中
		}
		int setSize = set.size();
		//  如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小
		if (setSize < n) {
			generateWithSet(min, max, n - setSize, set);//  递归
		}
	}

	/**
	 * 随机指定范围内N个不重复的数 在初始化的无重复待选数组中随机产生一个数放入结果中。
	 *
	 * @param min
	 * @param max
	 * @param n
	 * @return
	 */
	public static int[] generateWithReduceLoop(int min, int max, int n) {
		int len = max - min + 1;
		if (max < min || n > len) {
			return null;
		}
		// 初始化给定范围的待选数组
		int[] source = new int[len];
		for (int i = min; i < min + len; i++) {
			source[i - min] = i;
		}
		int[] result = new int[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			// 待选数组0到(len-2)随机一个下标
			index = Math.abs(rd.nextInt() % len--);
			// 将随机到的数放入结果集
			result[i] = source[index];
			// 将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换,
			// 通过置换 保证下次出现的随机数不会出现在原来的随机数数组中 因为下一次的随机数数组 比上次少一
			source[index] = source[len];
		}
		return result;

	}

}
