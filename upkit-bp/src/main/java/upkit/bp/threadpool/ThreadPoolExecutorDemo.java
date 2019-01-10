package upkit.bp.threadpool;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10240, false);

		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1000, 5000, 60L, TimeUnit.SECONDS, queue);

		ArrayList<Future<Integer>> futerList = new ArrayList<Future<Integer>>();

		int x = 0;
		for (int i = 0; i < 6000; i++) {
			CountCallable sumer = new CountCallable();
			Future<Integer> result = poolExecutor.submit(sumer);
			futerList.add(result);
//			try {
////				while(result.isDone()) {
////					
////				}
//				x += result.get();
//			} catch (InterruptedException | ExecutionException e) {
//				e.printStackTrace();
//			}
		}
		try {
			for (Future<Integer> future : futerList) {
				x += future.get();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		float end = (System.currentTimeMillis() - start) / 1000;
		System.out.println("x: " + x + "use time: " + end);

	}

}
