package upkit.bp.distributeLock.byZK;

import java.util.concurrent.TimeUnit;

public interface DistributedLock {

	/** 获取锁，如果没有得到就等待(阻塞) */
	public void acquire() throws Exception;

	/**
	 * 获取锁，直到超时
	 * 
	 * @param time
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	public boolean acquire(long time, TimeUnit unit) throws Exception;

	/**
	 * 释放锁
	 */
	public void release() throws Exception;

}
