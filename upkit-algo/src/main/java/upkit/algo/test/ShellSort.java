package upkit.algo.test;

import java.util.Arrays;

import upkit.utils.NumGenerateUtil;

/**
 * 希尔排序
 * 
 * @author melody
 */
public class ShellSort {

	public static void main(String[] args) {
		int[] nums = NumGenerateUtil.generateWithReduceLoop(-5, 100, 50);
		System.out.println("随机数组为：" + Arrays.toString(nums));

		// 记录排序次数
		int count = 1;
		int N = nums.length;
		int h = 1;
		// 为了维持一个较好的算法复杂度 根据数组长度设置一个较好的偏移量
		while (h < N / 3) {
			h = 3 * h + 1; // 1, 4, 13, 40
		}
		System.out.println("H: " + h);
		// 进行排序
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				// 控制排序方向
				for (int j = i; j >= h && (nums[j] < nums[j - h]); j -= h) {
					int tmp = nums[j];
					nums[j] = nums[j - h];
					nums[j - h] = tmp;
				}
				// 打印当前数组
				System.out.println("第" + count++ + "次排序结果：" + Arrays.toString(nums));
			}
			h = h / 3;
		}
	}
}
