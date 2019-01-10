package upkit.algo.test;

import upkit.utils.NumGenerateUtil;

public class HashSearch {

	public static void main(String[] args) {

		String s = "a";
		String[] arr = new String[32];

		System.out.println(Integer.toBinaryString(s.hashCode()));

		int[] nums = NumGenerateUtil.generateWithReduceLoop(0, 100, 20);

		for (int i = 0; i < nums.length; i++) {
			int tmp = nums[i];
			int hash = tmp & (arr.length - 1);
			System.out.println(Integer.toBinaryString(tmp) + " : " + hash);
		}

	}
}
