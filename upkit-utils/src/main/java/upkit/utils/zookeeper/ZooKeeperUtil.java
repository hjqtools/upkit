package upkit.utils.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ZooKeeper 工具 设计为枚举类型的单例模式
 * 
 * @description: TODO
 * @author: melody
 * @email: cuzart@163.com
 */
public enum ZooKeeperUtil implements Watcher {

	// 实例元素，有且只有一
	instance;

	private static final Logger logger = LoggerFactory.getLogger(ZooKeeperUtil.class);
	// 会话超时
	private static final int SESSION_TIMEOUT = 5000;
	private static final String IP_STR = "192.168.2.100:2181,192.168.2.101:2181,192.168.2.102:2181";

	static {
		try {
			instance.connect(IP_STR);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private ZooKeeper zookeeper;

	/**
	 * 删除该节点以及该节点下的所有子节点
	 *
	 * @param path
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void rmrNode(String path) throws KeeperException, InterruptedException {
		if (existNode(path)) {
			List<String> childrens = getChildren(path, false);
			// 遍历列表 获得能够可用的节点
			if (childrens != null && childrens.size() > 0) {
				for (int i = 0; i < childrens.size(); i++) {
					String child = path + "/" + childrens.get(i);
					rmrNode(child);
				}
			}
			System.out.println("删除节点:" + path);
			instance.zookeeper.delete(path, -1);
		}
	}

	/**
	 * 设置节点的数据
	 *
	 * @param path
	 * @param data
	 * @return
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public Stat setData(String path, String data) throws KeeperException, InterruptedException {
		// 如果存在这个节点
		if (existNode(path)) {
			return zookeeper.setData(path, data.getBytes(), -1);
		}
		return null;
	}

	/**
	 * 获取path下的所有孩子节点
	 *
	 * @param path
	 * @param watch
	 * @return
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public List<String> getChildren(String path, boolean watch) throws KeeperException, InterruptedException {
		return zookeeper.getChildren(path, watch);
	}

	/**
	 * 创建节点 带数 OPEN—ACL Persistent节点
	 *
	 * @param groupName
	 * @param data
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void createDataNode(String groupName, String data) throws KeeperException, InterruptedException {
		String path = groupName;
		if (zookeeper.exists(path, false) == null) {
			zookeeper.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		logger.info("创建数据节点:" + path);
	}

	/**
	 * 创建节点 无数据 OPEN—ACL Persistent节点
	 *
	 * @param groupName
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void createNode(String groupName) throws KeeperException, InterruptedException {
		String path = groupName;
		if (zookeeper.exists(path, false) == null) {
			zookeeper.create(path, null/* data */, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		logger.info("创建无数据节点:" + path);
	}

	/**
	 * 返回节点是否存在
	 *
	 * @param node
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public boolean existNode(String node) throws KeeperException, InterruptedException {
		Stat exists = zookeeper.exists(node, false);
		return exists != null;
	}

	/**
	 * 关闭连接
	 *
	 * @throws InterruptedException
	 */
	public void close() throws InterruptedException {
		zookeeper.close();
	}

	// ip列表
	// 初始化 并连接
	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			System.out.println("连接zookeeper集群成功!");
			// connectedSignal.countDown();
		}
	}

	/**
	 * 初始化连接
	 *
	 * @param hosts
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void connect(String hosts) throws IOException, InterruptedException {
		logger.info("开始连接ZooKeeper集群...");
		instance.zookeeper = new ZooKeeper(hosts, SESSION_TIMEOUT, instance);
		// 等待时间响应
		// connectedSignal.await();
	}

	/**
	 * 获取
	 *
	 * @param datapath
	 * @return
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public String getData(String datapath) {
		byte[] data;
		try {
			data = instance.zookeeper.getData(datapath, false, new Stat());
		} catch (KeeperException | InterruptedException e) {
			data = null;
		}
		return data == null ? null : new String(data);
	}

}
