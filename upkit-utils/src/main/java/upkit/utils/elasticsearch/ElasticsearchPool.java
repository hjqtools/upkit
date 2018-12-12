package upkit.utils.elasticsearch;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Properties;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import upkit.utils.AssertUtil;

/**
 * Elasticsearch 线程池 :
 * 
 * 1.解决 es每次连接需要等待问题
 *
 * 实现思路： 首先连接池需要一个容量，容量要控制好，不然会对elasticsearch服务造成太大的压力。这里我们定义一个
 * corePoolSize这个变量来表示连接池的容量。 我们还需要一个缓冲容量，当连接池容量用完的时候可以继续创建。用变量maxnumPoolSize表示。
 * 维护TransportClient 我们用Stack数据结构，之所以用Stack后面再解释。
 * 维护工作中的TransportClient使用HashSet数据结构。
 * 
 * @author melody
 *
 *         采用单例模式
 *
 */
public class ElasticsearchPool {

	private static Logger logger = LoggerFactory.getLogger(ElasticsearchPool.class);
	// 连接池容量
	private static int corePoolSize;
	// 连接池最大容量
	private static int maxNumPoolSize;
	// 连接集群名称
	private static String clusterName;
	// 连接地址 如果有多个则用逗号分开
	private static String[] clusterAddress;
	// 连接端口号
	private static int clusterPort;
	// 可使用连接
	private static HashSet<TransportClient> workingSet;
	//
	private static Stack<TransportClient> workersStack;

	/**
	 * 配置文件 从application
	 */
	private static void config() {
		// 从application文件中直接获取配置文件、或者通过yml文件
		InputStream is = null;
		try {
			if (instance == null) {
				throw new NullPointerException("线程池未实例化成功。");
			}
			is = ElasticsearchPool.class.getClassLoader().getResourceAsStream("application.properties");
			Properties pros = new Properties();
			pros.load(is);
			corePoolSize = Integer.valueOf(pros.getProperty("spring.es.pool.mincount"), 16);
			maxNumPoolSize = Integer.valueOf(pros.getProperty("spring.es.pool.maxcount"), 128);
			clusterName = pros.getProperty("spring.es.pool.clustername");
			String addresses = pros.getProperty("spring.es.pool.clusteraddress");
			clusterAddress = addresses.split(",");
			if (clusterAddress == null || clusterAddress.length <= 0) {
				throw new RuntimeException("链接地址为空!");
			}
			clusterPort = Integer.valueOf(pros.getProperty("spring.es.pool.clusterport"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 初始化线程池
	 */
	private static void init() {
		AssertUtil.notBlank(clusterName, "集群名称不能为空。");
		AssertUtil.notNull(clusterAddress, "集群连接地址不能为空。");
		AssertUtil.notNull(clusterPort, "集群连接端口不能为空。");
		for (int i = 0; i < corePoolSize; i++) {
			TransportClient client = getTransportClient();
			if (client != null) {
				workersStack.add(client);
			}
		}
		// 初始化刷新线程
		refreshPool();
	}

	/**
	 * 获得连接
	 * 
	 * @return
	 */
	private static TransportClient getTransportClient() {
		Settings settings = Settings.builder().put("cluster.name", clusterName).build();
		TransportClient client = new PreBuiltTransportClient(settings);
		try {
			for (int i = 0; i < clusterAddress.length; i++) {
				String addr = clusterAddress[i];
				AssertUtil.notBlank(addr, "集群连接地址不能为空。");
				client.addTransportAddress(new TransportAddress(InetAddress.getByName(addr), clusterPort));
			}
			return client;
		} catch (Exception e) {
			logger.error("获取TransportClient连接失败：" + e.getMessage());
//			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化的时候创建初始化的连接Client。因为连接是会超时的，所以我们需要在超时之后定 时的去维护Client的连接，所以我们还需要一个定时刷新的job。
	 * 刷新连接
	 */
	private static void refreshPool() {
		// 这里可以使用ScheduledThreadPoolExecutor
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				logger.info("更新elasticsearch连接缓存。");
				if (!workersStack.isEmpty()) {
					TransportClient client = workersStack.remove(0);
					if (client != null) {
						try {
							client.close();
						} catch (Exception e) {
						}
					}
					TransportClient newClient = getTransportClient();
					/*
					 * 看到定时刷新这个方法就知道为什么选择Stack作为连接池存储的数 据结构了，因为需要把最活跃的链接压入
					 * 栈顶，保证每次出栈的而不Client基本是可用的，是超时的。然后从 栈的前端取出超时的Client，激活后重新入栈。
					 */
					workersStack.push(newClient);
				}
			}
		}, 10000, 10000);
	}

	/**
	 * 从连接池中获取一个线程, 获取活跃的Client后需要放入工作的Set集合内，用完之后再入连
	 * 接池存储栈，可以循环使用，并且刚用完的Client是非常活跃的，查询速度也比较快
	 * 
	 * @return
	 */
	public synchronized TransportClient getClient() {
		TransportClient client = null;
		if (!workersStack.isEmpty()) {
			client = workersStack.pop();
			workingSet.add(client);
		} else {
			if (workingSet.size() < maxNumPoolSize) {
				// 如果没有达到最大容量
				client = getTransportClient();
				workingSet.add(client);
			}
		}
		return client;
	}

	/**
	 * 关闭当前client
	 * @param client
	 */
	public synchronized void close(TransportClient client) {
		workingSet.remove(client);
		if (workersStack.size() >= corePoolSize) {
			workersStack.remove(0);
		}
		workersStack.push(client);
	}

	private ElasticsearchPool() {
	}

	private volatile static ElasticsearchPool instance = null;

	// 双重检测机制
	public static ElasticsearchPool getInstance() {
		if (instance == null) {
			synchronized (ElasticsearchPool.class) {
				if (instance == null) {
					instance = new ElasticsearchPool();
					// 读取配置
					config();
					// 初始化工作栈
					workersStack = new Stack<TransportClient>();
					// 初始化工作集
					workingSet = new HashSet<TransportClient>(maxNumPoolSize);
					// 初始化线程池
					init();
				}
			}
		}
		return instance;
	}

	/**
	 * 长时间不用，突然使用时，有时候会刚好碰到取出的Client是超时的，并且还没来得及刷新，这样的话就会报错了，解决这个问题可以使用错误尝试机制。
	 * 刚好在刷新的时候有使用连接，因为刷新是从Stack的前端获取，会导致Stack的重排，这个时候就会比较慢，经测试在1000ms ~
	 * 2000ms之间，不会太慢。 需要根据实际环境配置连接池的大小，否则有可能会更加慢。
	 * 
	 */
}
