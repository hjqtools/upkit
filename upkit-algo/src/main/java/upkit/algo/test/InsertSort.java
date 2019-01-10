package upkit.algo.test;

import java.util.Arrays;

import upkit.utils.NumGenerateUtil;

/**
 * 插入排序
 * 
 * @author melody
 *
 */
public class InsertSort {

	public static void main(String[] args) {

		int[] nums = NumGenerateUtil.generateWithReduceLoop(-5, 20, 15);
		System.out.println("随机数组为：" + Arrays.toString(nums));

		int N = nums.length;

		int i, j;
		int tmp = 0;
		for (i = 1; i < N; i++) {
			tmp = nums[i];
			j = i - 1;
			while (j >= 0 && nums[j] > tmp) {
				nums[j + 1] = nums[j];
				j--;
			}
			// 需要替换的位置
			nums[j + 1] = tmp;
			// 打印当前数组
			System.out.println("第" + i + "次排序结果：" + Arrays.toString(nums));
		}

	}
}
