package upkit.algo.test;

import java.util.Arrays;

import upkit.utils.NumGenerateUtil;

/**
 * 最大收益算法
 * 
 * @author melody
 *
 */
public class MaximumProfit {

	public static void main(String[] args) {

		//
		int[] nums = NumGenerateUtil.generateWithReduceLoop(1, 100, 10);
		System.out.println("随机数组为：" + Arrays.toString(nums));
		// 当前最小值
		int min = nums[0];
		// 最大收益
		int maxProfit = Integer.MIN_VALUE;

		for (int i = 1; i < nums.length; i++) {
			maxProfit = Math.max(maxProfit, nums[i] - min);
			min = Math.min(min, nums[i]);
		}
		System.out.println("最大收益为：" + maxProfit);
	}
}
