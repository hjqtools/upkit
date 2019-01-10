package upkit.algo;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import java.util.HashSet;
import java.util.Random;

/**
 * 布隆过滤器使用
 * 
 * @author melody
 *
 */
public class testBloomFilter {

	static int sizeOfNumberSet = Integer.MAX_VALUE >> 12;

	static Random generator = new Random();

	public static void main(String[] args) {

		System.out.println(sizeOfNumberSet);
		int error = 0;
		HashSet<Integer> hashSet = new HashSet<Integer>();
		BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), sizeOfNumberSet);

		for (int i = 0; i < sizeOfNumberSet; i++) {
			int number = generator.nextInt();
			// 测试错误
			if (filter.mightContain(number) != hashSet.contains(number)) {
				error++;
			}
			filter.put(number);
			hashSet.add(number);
		}

		System.out.println("Error count: " + error + ", error rate = "
				+ String.format("%f", (float) error / (float) sizeOfNumberSet));
	}
}
