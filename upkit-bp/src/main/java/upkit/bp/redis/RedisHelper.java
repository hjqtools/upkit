package upkit.bp.redis;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisHelper {
	private RedisHelper() {

	}

	private static Map<Integer, JedisPool> mapJp = new HashMap<Integer, JedisPool>();

	public static JedisPool getPool(int port) {
		JedisPool jp = mapJp.get(port);
		if (jp == null) {
			JedisPoolConfig jcfg = new JedisPoolConfig();
			jcfg.setMaxTotal(10);
			jcfg.setMinIdle(3);
			JedisPool jp1 = new JedisPool(jcfg, "192.168.1.106", 6379);
			mapJp.put(6379, jp1);

			JedisPoolConfig jcfg2 = new JedisPoolConfig();
			jcfg2.setMaxTotal(10);
			jcfg2.setMinIdle(3);
			JedisPool jp2 = new JedisPool(jcfg2, "192.168.1.106", 6387);
			mapJp.put(6387, jp2);

			JedisPoolConfig jcfg3 = new JedisPoolConfig();
			jcfg3.setMaxTotal(10);
			jcfg3.setMinIdle(3);
			JedisPool jp3 = new JedisPool(jcfg3, "192.168.1.106", 6388);
			mapJp.put(6388, jp3);

			JedisPoolConfig jcfg4 = new JedisPoolConfig();
			jcfg4.setMaxTotal(10);
			jcfg4.setMinIdle(3);
			JedisPool jp4 = new JedisPool(jcfg4, "192.168.1.106", 6389);
			mapJp.put(6389, jp4);

			jp = mapJp.get(port);
		}
		return jp;
	}

	public static String getScript(String str) {
		File f = new File(str);
		try {
			FileInputStream fin = new FileInputStream(f);

			byte[] bs = new byte[fin.available()];

			fin.read(bs);

			fin.close();

			return new String(bs);
		} catch (Exception err) {
			err.printStackTrace();
		}
		return "";
	}

}