package upkit.bp.reentrant.demo01;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 灯
 * 
 * @author melody
 *
 */
public class Light {

	private ReentrantLock lock = new ReentrantLock();
	private volatile boolean status = false;
	private Condition condition = lock.newCondition();

	public void off() {
		lock.lock();
		try {
			while (!status) {
				condition.await();
			}
			status = false;
			System.out.println("关灯.");
			condition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void on() {
		lock.lock();
		try {
			while (status) {
				condition.await();
			}
			status = true;
			System.out.println("开灯.");
			condition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
