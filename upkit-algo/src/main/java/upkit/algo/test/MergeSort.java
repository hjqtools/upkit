package upkit.algo.test;

import java.util.Arrays;

import upkit.utils.NumGenerateUtil;

public class MergeSort {

	static int[] aux;

	public static void main(String[] args) {

		int[] nums = NumGenerateUtil.generateWithReduceLoop(-10, 20, 20);
		System.out.println("随机数组为：" + Arrays.toString(nums));

		aux = new int[nums.length];
		sort(nums, 0, nums.length - 1);

		System.out.println("排序完成后：" + Arrays.toString(nums));
	}

	/**
	 * 自顶向下的归并排序
	 *
	 * @param a
	 * @param low
	 * @param high
	 */
	private static void sort(int[] a, int low, int high) {
		// 将数组a[low..high]排序
		if (high <= low)
			return;
		int mid = low + (high - low) / 2;
		sort(a, low, mid); // 将左半边排序
		sort(a, mid + 1, high); // 将右半边排序
		merge(a, low, mid, high); // 归并结果
	}

	/**
	 * 排序并合并数组 该方法是将所有的元素复制到aux[]中，然后再归并回a[]中。方法在归并时(第二个for循环，进行了四个条件的判断：
	 * 左半边用尽（取右半边的元素）、右半边元素用尽（取左半边的元素）、右半边的当前的元素小于左半边的当前的元素（取右半边的元素）
	 * 以及右半边的当前元素大于等于左半边的当前元素（取左半边的元素）)
	 *
	 * @param a
	 * @param low
	 * @param mid
	 * @param high
	 */
	@SuppressWarnings("unchecked")
	private static void merge(int[] a, int low, int mid, int high) {

		// 将a[low..mid]和a[mid+1..high] 归并
		int i = low;
		int j = mid + 1;

		for (int k = low; k <= high; k++) // 将a[low..high]复制到aux[low..high]
			aux[k] = a[k];

		for (int k = low; k <= high; k++) { // 归并回到a[low..high]
			if (i > mid)
				a[k] = aux[j++]; // 左边取尽元素时，则将右边的元素全部复制到数组中
			else if (j > high)
				a[k] = aux[i++]; // 当右边的元素取尽时，则将左边的元素全部复制到数组a中
			else if (aux[j] < aux[i])
				a[k] = aux[j++]; // 这里比较高位和低位的元素，如果高位小于低位则取高位元素，在排列给a数组
			else
				a[k] = aux[i++]; // 这个则说明低位小于高位，则取低位元素，再排列给a数组
		}

	}

}
