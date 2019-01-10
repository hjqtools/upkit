package upkit.bp.redis;

import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * RedisUtils
 * 
 */
public class RedisUtils {

	/**
	 * @param redisProperties
	 * @return
	 */
	public static JedisPool createJedisPool(Properties redisProperties) {
		// String host = redisProperties.getProperty("redis.host", "localhost");
		// int port = Integer.valueOf(redisProperties.getProperty("redis.port",
		// "6379"));
		// int timeout = Integer.valueOf(redisProperties.getProperty("redis.timeout",
		// "3000"));
		// int maxidle = Integer.valueOf(redisProperties.getProperty("redis.maxidle",
		// "10"));
		// String password = redisProperties.getProperty("redis.password");

		String host = "192.168.1.105";
		int port = 6379;
		int timeout = 3000;
		int maxidle = 10;
		String passwd = "root";

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxidle);
//		return new JedisPool(config, host, port, timeout, passwd);
		return new JedisPool(config, host, port, timeout);
	}

	public static void main(String[] args) {

		JedisPool jedisPool = createJedisPool(null);
		// boolean closed = jedisPool.isClosed();
		// System.out.println(closed);

		Jedis jedis = jedisPool.getResource();
		jedis.lpush("ll1", "哈哈");
		jedis.lpush("232", "asdsad","asdsd");
		
		Long llen = jedis.llen("111");
		
//		String value = jedis.lindex("111", 0);
		
//		String value = jedis.lpop("232");
//		String value2 = jedis.lpop("232");
		
		String value = jedis.lindex("232", 0);
		String value2 = jedis.lindex("232", 1);
		
		System.out.println(value);
		System.out.println(value2);
		
		System.out.println(llen);

		jedis.close();

		jedisPool.close();

	}
}