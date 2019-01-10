package upkit.bp.zkclientDemo;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

public class Demo01 {

	private static final int SESSION_TIMEOUT = 5000;
	private static final String IP_STR = "192.168.2.101:2181,192.168.2.102:2181,192.168.2.103:2181";

	public static void main(String[] args) {

		ZkClient client = new ZkClient(new ZkConnection(IP_STR), SESSION_TIMEOUT);
//		client.createEphemeral("/node1");

//		client.createPersistent("/node1");
//		client.delete("/node1");
//		client.create("/node1", "melody	", CreateMode.PERSISTENT_SEQUENTIAL);
		client.createPersistent("/node1","aaaa");
		
		/*
		 * 监听节点数据的变化
		 * */
//		client.subscribeDataChanges("/node1", new IZkDataListener() {
//			@Override
//			public void handleDataDeleted(String dataPath) throws Exception {
//				System.out.println("删除的节点为：" + dataPath);
//			}
//
//			@Override
//			public void handleDataChange(String dataPath, Object data) throws Exception {
//				System.out.println("变更节点为：" + dataPath + "，变更数据为：" + data);
//			}
//		});
			
		
		/*
		 * 监听节点的变化
		 */
		client.subscribeChildChanges("/node1", new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				System.out.println("parent node: " + parentPath);
				for(String c : currentChilds) {
					System.out.println(c);
				}
			}
		});
		
		client.delete("/node1");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		client.close();
	}
}
