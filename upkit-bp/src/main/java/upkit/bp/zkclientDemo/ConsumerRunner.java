package upkit.bp.zkclientDemo;

import org.I0Itec.zkclient.ZkClient;

public class ConsumerRunner implements Runnable {

	private ZkClient zkClient;

	public ConsumerRunner(ZkClient zkClient) {
		super();
		this.zkClient = zkClient;
	}

	@Override
	public void run() {
		boolean isExists = this.zkClient.exists("/node");
		while (isExists) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			isExists = this.zkClient.exists("/node");
		}
		boolean isSuccess = false;
		// 循环直到写入成功
		while (!isSuccess) {
			try {
				zkClient.createEphemeral("/node");
			} catch (Exception e) {
				// 排除多个线程写入时导致的异常
				continue;
			}
			isSuccess = true;
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			zkClient.close();
		}
	}

}
