package upkit.utils.test;

import java.util.StringTokenizer;

public class StringTest {

	public static void main(String[] args) {

		String ss = "4324,1235,53465346,643645";
//		String[] strings = ss.split(",");
//		for (String string : strings) {
//			System.out.println(string);
//		}
		System.out.println(ss.intern());
		StringTokenizer tokenizer = new StringTokenizer(ss, ",");

		long startA = System.currentTimeMillis();
		while (tokenizer.hasMoreElements()) {
			System.out.println("token: " + tokenizer.nextToken());
		}
		long startB = System.currentTimeMillis();
		System.out.println("A花费时间：" + (startB - startA));

//		System.out.println(tokenizer);
		

	}
}
