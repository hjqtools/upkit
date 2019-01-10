package upkit.bp.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CountCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {

		int sum = 0;
		for (int i = 1; i < 100; i++) {
			TimeUnit.MICROSECONDS.sleep(10);
			sum += i;
		}
//		System.out.println("完成当前计算线程: " + Thread.currentThread().getName());
		return sum;
	}

}
