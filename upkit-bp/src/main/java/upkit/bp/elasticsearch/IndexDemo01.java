package upkit.bp.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 演示基本的index过程
 * 
 * @author Administrator
 *
 */
public class IndexDemo01 {

	public static void main(String[] args) throws IOException {

		Settings settings = Settings.builder().put("cluster.name", "cuzart").build();
		PreBuiltTransportClient client = new PreBuiltTransportClient(settings);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.2.103"), 9300));

		String filePath = "D:/Data/blog.json";

		// 单行插入
		Files.lines(Paths.get(filePath)).forEach(new Consumer<String>() {
			@Override
			public void accept(String s) {
				IndexResponse indexResponse = client.prepareIndex("test", "test").setSource(s, XContentType.JSON)
						.get();
			}
		});
		
		
		
	}
}
