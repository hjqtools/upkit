package upkit.bp.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 
 * ForkJoinTask: 我们要使用ForkJoin框架，必须首先创建一个ForkJoin任务。它提供在任务中执行fork()和join的操
 * 作机制，通常我们不直接继承ForkjoinTask类，只需要直接继承其子类。
 * 
 * 1. RecursiveAction，用于没有返回结果的任务
 * 
 * 2. RecursiveTask，用于有返回值的任务
 * 
 * 
 * @author melody
 *
 */
public class ForkJoinDemo {

	public static void main(String[] args) {

		// 生成一个计算任务，计算1+2+3+4
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		// 生成一个计算任务，计算1+2+3+4
		CountTask task = new CountTask(1, 100000);
		// 执行一个任务
		Future<Integer> result = forkJoinPool.submit(task);
		try {
			System.out.println(result.get());
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}

/**
 * 计算任务
 * 
 * @author melody
 *
 */
class CountTask extends RecursiveTask<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2519669434822232538L;

	private static final int threadhold = 2;
	private int start;
	private int end;

	public CountTask(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {

		int sum = 0;

		// 如果任务足够小就计算任务
		boolean canCompute = (end - start) <= threadhold ? true : false;

		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum += i;
			}
		} else {
			// 如果任务大于阙值，就分裂成两个子任务计算
			int middle = (start + end) / 2;
			CountTask left = new CountTask(start, middle);
			CountTask right = new CountTask(middle + 1, end);

			// 执行子任务
			left.fork();
			right.fork();

			// 等待任务执行结束合并结果
			int leftResult = left.join();
			int rightResult = right.join();

			// 合并子任务
			sum = leftResult + rightResult;
		}

		return sum;

	}

}