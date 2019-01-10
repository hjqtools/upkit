package upkit.bp.distributeLock.byRedis;

import java.util.List;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 实现思想：
 * 
 * （1）获取锁的时候，使用setnx加锁，并使用expire命令为锁添加一个超时时间，超过该时间则自动释放锁，锁的value值为一个随机生成的UUID，通过此在释放锁的时候进行判断。
 * 
 * （2）获取锁的时候还设置一个获取的超时时间，若超过这个时间则放弃获取锁。
 * 
 * （3）释放锁的时候，通过UUID判断是不是该锁，若是该锁，则执行delete进行锁释放。
 *
 * 
 */
public class DistributedLock {

	private final JedisPool jedisPool;

	public DistributedLock(JedisPool jedisPool) {
		super();
		this.jedisPool = jedisPool;
	}

	/**
	 * 加锁
	 * 
	 * @param lockName
	 * @param acquireTimeout
	 * @param timeout
	 * @return
	 */
	public String lockWithTimeout(String lockName, long acquireTimeout, long timeout) {
		Jedis conn = null;
		String retutnIdentifire = null;
		try {
			// 获取连接
			conn = jedisPool.getResource();
			// 随机生成一个value
			String identifier = UUID.randomUUID().toString();
			// 锁名 即key值
			String lockKey = "lock:" + lockName;
			// 超时时间 上锁后超过此时间自动释放锁
			int lockExpire = (int) (timeout / 1000);

			// 获取锁的超时时间，超过这个时间则放弃获取锁
			long end = System.currentTimeMillis() + acquireTimeout;
			while (System.currentTimeMillis() < end) {
				if (conn.setnx(lockKey, identifier) == 1) {
					// 设置过期时间 但有可能失败
					conn.expire(lockKey, lockExpire);
					// 返回value值，用于锁释放时间确认
					retutnIdentifire = identifier;
					return retutnIdentifire;
				}
			}
			/*
			 * 再次判断 如果存在key，但未设置过期时间，返回-1 代表key没有设置超时时间，为key设置一个超时时间
			 */
			if (conn.ttl(lockKey) == -1) {
				conn.expire(lockKey, lockExpire);
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return retutnIdentifire;
	}

	/**
	 * 释放锁
	 * 
	 * @param lockName
	 * @param identifier
	 * @return
	 */
	public boolean releaseLock(String lockName, String identifier) {
		Jedis conn = null;
		String lockKey = "lock:" + lockName;
		boolean retFlag = false;
		try {
			conn = jedisPool.getResource();
			while (true) {
				// 监视lock 准备开始事物
				conn.watch(lockKey);
				// 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
				if (identifier.equals(conn.get(lockKey))) {
					Transaction transaction = conn.multi();
					transaction.del(lockKey);
					List<Object> result = transaction.exec();
					if (result == null) {
						continue;
					}
					retFlag = true;
				}
				conn.unwatch();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return retFlag;
	}

}
