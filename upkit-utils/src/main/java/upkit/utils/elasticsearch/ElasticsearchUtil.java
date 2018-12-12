package upkit.utils.elasticsearch;

import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import upkit.utils.AssertUtil;

/**
 * 
 * @description: elasticsearch 常用操作工具
 * @author: melody
 * @email: cuzart@163.com
 */
public final class ElasticsearchUtil {

	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchUtil.class);
	
	/**
	 * 获取Elasticsarch客户
	 *
	 * @param clusterName
	 * @param address
	 * @param port
	 * @return 返回es 客户端连
	 */
	public static TransportClient getClient(String clusterName, Integer port, String... address) {
		AssertUtil.notBlank(clusterName, "集群名称不能为空。");
		AssertUtil.notNull(address, "集群连接地址不能为空。");
		AssertUtil.notNull(port, "集群连接端口不能为空。");
		Settings settings = Settings.builder().put("cluster.name", clusterName).build();
		TransportClient client = new PreBuiltTransportClient(settings);
		try {
			for (int i = 0; i < address.length; i++) {
				String addr = address[i];
				AssertUtil.notBlank(addr, "集群连接地址不能为空。");
				client.addTransportAddress(new TransportAddress(InetAddress.getByName(addr), port));
			}
			return client;
		} catch (Exception e) {
			logger.info("获取elasticsearch客户端失败！");
			return null;
		}
	}

	/**
	 * 预设置client查询
	 *
	 * @param client
	 * @param indices
	 * @return
	 */
	public static SearchRequestBuilder prepareSearch(TransportClient client, String... indices) {
		AssertUtil.notNull(client, "客户端连接对象不能为。");
		AssertUtil.notNull(indices, "索引不能为空。");
		for (String index : indices) {
			if (StringUtils.isBlank(index)) {
				throw new IllegalArgumentException("索引不能为空。");
			}
		}
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indices);
		return searchRequestBuilder;
	}

	/**
	 * 设置索引类型
	 *
	 * @param builder
	 * @param types
	 * @return
	 */
	public static SearchRequestBuilder setTypes(SearchRequestBuilder builder, String... types) {
		AssertUtil.notNull(builder, "SearchRequestBuilder对象不能为空");
		AssertUtil.notNull(types, "类型不能为空。");
		for (String type : types) {
			if (StringUtils.isBlank(type)) {
				throw new IllegalArgumentException("索引不能为空。");
			}
		}
		SearchRequestBuilder searchRequestBuilder = builder.setTypes(types);
		return searchRequestBuilder;
	}

	public static void main(String[] args) throws InterruptedException {
		// WdElasticsearchLocal wdElasticsearch = new WdElasticsearchLocal();
		// wdElasticsearch.setDataSourceId(1232323L);
		// wdElasticsearch.setElasticsearchClusterName("cuzart");
		// wdElasticsearch.setElasticsearchIndex("cuzart");
		// wdElasticsearch.setElasticsearchInetAddress("[\"192.168.2.101\",
		// \"192.168.2.102\",\"192.168.2.103\"]");
		// wdElasticsearch.setElasticsearchPort(9300);
		// wdElasticsearch.setElasticsearchLocalId(32534543L);
		// wdElasticsearch.setElasticsearchType("blog");

		// String[] address =
		// JSONUtil.getStringArrFromJson(wdElasticsearch.getElasticsearchInetAddress());
		//
		// TransportClient client =
		// ElasticsearchUtil.getClient(wdElasticsearch.getElasticsearchClusterName(),
		// wdElasticsearch.getElasticsearchPort(), address);
		// SearchRequestBuilder searchRequestBuilder =
		// client.prepareSearch(wdElasticsearch.getElasticsearchIndex());
		// searchRequestBuilder.setTypes(wdElasticsearch.getElasticsearchType());
		// MatchAllQueryBuilder allQuery = QueryBuilders.matchAllQuery();
		//
		// SearchResponse scrollResp =
		// searchRequestBuilder.setQuery(allQuery).setScroll(new TimeValue(1000))
		// .setQuery(allQuery)
		// // 每次滚动请求1000hits
		// .setSize(100)
		// .get();

		// SearchHit[] hits = scrollResp.scrollId();
		// for (int i = 0; i < hits.length; i++) {
		// SearchHit hit = hits[i];
		// System.out.println(hit.getIndex());
		// }
		// CountDownLatch latch = new CountDownLatch(1);

		// int count = 1;
		// do {
		// for (SearchHit hit : scrollResp.getHits().getHits()) {
		// Map<String, Object> source = hit.getSourceAsMap();
		// String id = hit.getId();
		// try {
		// if(count >= 201){
		// String s = JSONObject.toJSONString(source);
		//// System.out.println(id +"->"+s);
		// System.out.println(s);
		// }
		// if( count == 300){
		// return;
		// }
		//
		// count++;
		// } catch (Exception e) {
		// continue;
		// }
		// }
		//
		// System.out.println(count);
		//// latch.await();
		// scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).
		// setScroll(new TimeValue(10000)).execute().actionGet();
		// } while (scrollResp.getHits().getHits().length != 0);

		// System.out.println(count);

		// System.out.println(client);
	}

	// /**
	// * 验证基wdElasticsearchLocal 基本参数是否为存在为空的情况
	// *
	// * @param wdElasticsearchLocal
	// * @return
	// */
	// private static boolean validatePrimaryFieldNotEmpty(WdElasticsearchLocal
	// wdElasticsearchLocal) {
	// if (wdElasticsearchLocal == null) {
	// return false;
	// }
	// Long dataSourceId = wdElasticsearchLocal.getDataSourceId();
	// Long elasticsearchLocalId = wdElasticsearchLocal.getElasticsearchLocalId();
	// if (dataSourceId == null || elasticsearchLocalId == null) {
	// return false;
	// }
	// String elasticsearchIndex = wdElasticsearchLocal.getElasticsearchIndex();
	// String elasticsearchType = wdElasticsearchLocal.getElasticsearchType();
	// if (StringUtils.isBlank(elasticsearchIndex) ||
	// StringUtils.isBlank(elasticsearchType)) {
	// return false;
	// }
	// return true;
	// }

}
