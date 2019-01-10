package upkit.algo.test;

import java.util.Arrays;

import upkit.utils.NumGenerateUtil;

/**
 * 冒泡排序
 * 
 * @author melody
 */
public class BubbleSort {

	public static void main(String[] args) {

		int[] nums = NumGenerateUtil.generateWithReduceLoop(-5, 20, 15);
		System.out.println("随机数组为：" + Arrays.toString(nums));
		int tmp = 0;
		for (int i = 0; i < nums.length; i++) {
			for (int j = 1; j < nums.length - i; j++) {
				tmp = nums[j - 1];
				if (nums[j - 1] > nums[j]) {
					nums[j - 1] = nums[j];
					nums[j] = tmp;
				}
			}
			// 打印当前数组
			System.out.println("第" + (i + 1) + "次排序结果：" + Arrays.toString(nums));
		}
	}
}
