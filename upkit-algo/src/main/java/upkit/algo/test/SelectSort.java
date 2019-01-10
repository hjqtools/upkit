package upkit.algo.test;

import java.util.Arrays;

import upkit.utils.NumGenerateUtil;

/**
 * 选择排序法
 * 
 * 每次选择最小（最大）元素与当前位置元素进行交换
 * 
 * @author melody
 *
 */
public class SelectSort {

	public static void main(String[] args) {

		int[] nums = NumGenerateUtil.generateWithReduceLoop(-5, 20, 15);
		System.out.println("随机数组为：" + Arrays.toString(nums));

		// 最大或者最小的那个元素
		int tmp = 0;
		int pos = 0;
		for (int i = 0; i < nums.length; i++) {
			tmp = nums[i];
			pos = i;
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[j] < tmp) {
					tmp = nums[j];
					pos = j;
				}
			}
//			if (i + 1 < nums.length) {
				nums[pos] = nums[i];
				nums[i] = tmp;
//			}

			// 打印当前数组
			System.out.println("第" + (i + 1) + "次排序结果：" + Arrays.toString(nums));
		}

	}
}
