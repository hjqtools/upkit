package upkit.algo.test;

import java.util.Arrays;

import upkit.utils.NumGenerateUtil;

/**
 * 二分法搜索
 * 
 * @author Administrator
 *
 */
public class BinarySearch {

	public static void main(String[] args) {

		int[] nums = NumGenerateUtil.generateWithReduceLoop(-5, 20, 20);
		System.out.println("随机数组为：" + Arrays.toString(nums));

		// 使用快速排序进行预处理
		QuickSort.sort(nums, 0, nums.length - 1);

		System.out.println("排序后得到的数组：" + Arrays.toString(nums));

		int searchOne = 5;

		int left = 0;
		int right = nums.length - 1;
		while (left < right) {
			int mid = (left + right) / 2;
			if (nums[mid] == searchOne) {
				System.out.println("查到位置为: " + mid);
				break;
			} else if (searchOne < nums[mid]) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		// System.out.println("未找着。");
	}
}
