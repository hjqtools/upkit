package upkit.bp.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SingleThreadExecutorTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// 1.创建一个可重用固定线程数的线程池
//		ExecutorService pool = Executors.newSingleThreadExecutor();

		// 2.创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口;
		ExecutorService pool = Executors.newFixedThreadPool(2);

		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口;
//		ExecutorService pool=Executors.newCachedThreadPool();

//		Thread t1 = new MyThread();
//		Thread t2 = new MyThread();
//		Thread t3 = new MyThread();
//		Thread t4 = new MyThread();
//		Thread t5 = new MyThread(); // 将线程放到池中执行；
//
//		pool.execute(t1);
//		pool.execute(t2);
//		pool.execute(t3);
//		pool.execute(t4);
//		pool.execute(t5); // 关闭线程池
//		pool.shutdown();
		Future<String> future = pool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(5000);
				return "调用完成.";
			}
		});

		while (!future.isDone()) {
			// 停顿200ms
			Thread.sleep(200);
			// 输出控制符
			System.out.println("#");
		}

		System.out.println(future.get());
		pool.shutdown();

		/**
		 * 调度线程池
		 */
//		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
//		exec.scheduleAtFixedRate(new Runnable() {// 每隔一段时间就触发异常
//			@Override
//			public void run() {
//				System.out.println("===================");
//			}
//		}, 1000, 5000, TimeUnit.MILLISECONDS);
//		exec.scheduleAtFixedRate(new Runnable() {// 每隔一段时间打印系统时间，证明两者是互不影响的
//			@Override
//			public void run() {
//				System.out.println(System.nanoTime());
//			}
//		}, 1000, 2000, TimeUnit.MILLISECONDS);
	}
}
