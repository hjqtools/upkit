package upkit.utils.http;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @description:  http 操作
 * @author: melody
 * @email:  cuzart@163.com
 */
public final class HttpUtil {

	/**
	 * 使用Get请求Http类型的请
	 *
	 * @param url
	 *            请求地址
	 * @param cookie
	 *            cookie
	 * @param referer
	 *            跳转地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public static CloseableHttpResponse doHttpGet(String url, String cookie, String referer) throws IOException {

		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();

		HttpGet get = new HttpGet(url);
		if (cookie != null) {
			get.setHeader("Cookie", cookie);
		}
		if (referer != null) {
			get.setHeader("Referer", referer);
		}
		CloseableHttpResponse response = httpClient.execute(get);
		return response;
	}

	/**
	 * 使用Post请求Http类型的请
	 *
	 * @param url
	 *            请求地址
	 * @param values
	 *            请求参数
	 * @param cookie
	 *            cookie
	 * @param referer
	 *            跳转地址
	 * @return 响应对象
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static CloseableHttpResponse doHttpPost(String url, List<NameValuePair> values, String cookie,
			String referer) throws ClientProtocolException, IOException {
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();

		HttpPost post = new HttpPost(url);
		if (cookie != null) {
			post.setHeader("Cookie", cookie);
		}
		if (referer != null) {
			post.setHeader("Referer", referer);
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, Consts.UTF_8);// 设置字符编码为UTF-8
		// 设置请求的参数
		post.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(post);
		return response;
	}

}
