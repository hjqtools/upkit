package upkit.algo.test;

import java.util.Arrays;

import upkit.utils.NumGenerateUtil;

/**
 * 快速排序
 * 
 * @author melody
 *
 */
public class QuickSort {

	public static void main(String[] args) {

		int[] nums = NumGenerateUtil.generateWithReduceLoop(-20, 200, 100);
		System.out.println("随机数组为：" + Arrays.toString(nums));

		int N = nums.length;
		sort(nums, 0, N - 1);

		System.out.println(Arrays.toString(nums));
	}

	/**
	 * 进行排序
	 * 
	 * @param arr
	 * @param i
	 * @param j
	 */
	public static void sort(int[] arr, int low, int high) {
		if (high <= low) {
			return; // bvc高位小于等于低位则说明不需要排序，直接返回即可
		}
		// 快速排序的切分 进行完成切分后 将得到左边元素都小于j这个位置的元素 右边元素都大于j
		int j = partion(arr, low, high);
		sort(arr, low, j - 1); // 将左半部分a[low..j-1]排序
		sort(arr, j + 1, high); // 将右半部分a[j+1..hi]排序
	}

	/**
	 * 切分 并 排序
	 * 
	 * @param arr
	 * @param low
	 * @param high
	 * @return
	 */
	private static int partion(int[] arr, int low, int high) {
		// 将数组切分为a[low..high]，a[i]，a[i+1..high]
		int i = low;
		int j = high + 1;
		int v = arr[low];
		while (true) {
			// 扫描左部分,寻找大于v的元素位置
			// 从第二个元素开始 与第一个元素(low)进行比较
			while (arr[++i] < v) {
				if (i == high) {
					break;
				}
			}
			// 扫描右边部分，寻找小于v的元素位置
			while (v < arr[--j]) {
				if (j == low) {
					break;
				}
			}
			// 如果扫描完, 且满足两边的所有元素排序 ，成则跳出
			if (i >= j) {
				break;
			}
			exchange(arr, i, j);
		}
		// 最终得到左边元素都小v且右边元素都大于v的位置 然后进行交换位置
		exchange(arr, low, j); // 将v = a[j]
		return j;
	}

	/**
	 * 交换元素
	 * 
	 * @param arrkijijk
	 * @param i
	 * @param j
	 */
	private static void exchange(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}
