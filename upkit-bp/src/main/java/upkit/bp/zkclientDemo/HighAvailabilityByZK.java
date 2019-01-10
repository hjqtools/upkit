package upkit.bp.zkclientDemo;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**
 * 通过zk实现高可用节点（IP）列表
 * 实现机制：
 * 	通过向一个节点中写入多个临时子节点（IP）的方式
 * @author melody
 *
 */
public class HighAvailabilityByZK {

	private static final int SESSION_TIMEOUT = 5000;
	private static final String IP_STR = "192.168.2.101:2181,192.168.2.102:2181,192.168.2.103:2181";

	public static void main(String[] args) {

		ZkClient client = new ZkClient(new ZkConnection(IP_STR), SESSION_TIMEOUT);
		ZkClient client1 = new ZkClient(new ZkConnection(IP_STR), SESSION_TIMEOUT);
		ZkClient client2 = new ZkClient(new ZkConnection(IP_STR), SESSION_TIMEOUT);
		ZkClient client3 = new ZkClient(new ZkConnection(IP_STR), SESSION_TIMEOUT);
		ZkClient client4 = new ZkClient(new ZkConnection(IP_STR), SESSION_TIMEOUT);
		ZkClient client5 = new ZkClient(new ZkConnection(IP_STR), SESSION_TIMEOUT);

		client.createEphemeral("/node");
		try {
			Thread.sleep(1000);
			client.close();
			new Thread(new ConsumerRunner(client1), "client-1").start();
			new Thread(new ConsumerRunner(client2), "client-2").start();
			new Thread(new ConsumerRunner(client3), "client-3").start();
			new Thread(new ConsumerRunner(client4), "client-4").start();
			new Thread(new ConsumerRunner(client5), "client-5").start();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
//			client.close();
		}

	}

}
