package upkit.bp.threadpool;

public class MyRunnableA implements Runnable{

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
	
}