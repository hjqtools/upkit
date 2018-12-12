package upkit.utils.http;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

/**
 * 用来对Https类型的网站发送请
 * @description:  TODO
 * @author: melody
 * @email:  cuzart@163.com
 */
public final class HttpsUtil {

	/**
	 * 重写验证方法，取消检测SSL
	 */
	private static TrustManager manager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}
		
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	/**
	 * 私密连接工厂
	 */
	private static SSLConnectionSocketFactory socketFactory;

	/**
	 * 调用SSL，使得SSL利用被重写的TrustManager,从不用信任检。
	 */
	private static void enableSSL() {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { manager }, null);
			socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
		} catch (Exception e) {

		}
	}

	/**
	 * 使用https实现get请求:
	 *
	 * @param url
	 * @param cookie
	 * @param referer
	 * @return
	 * @throws IOException
	 */
	public static CloseableHttpResponse doHttpsGet(String url, String cookie, String referer) throws IOException {
		// 取消SSL�?查，使得SSL可用
		enableSSL();

		RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(defaultRequestConfig).build();

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
	 * 使用Post请求Https类型的请
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
	public static CloseableHttpResponse doHttpsPost(String url, List<NameValuePair> values, String cookie,
			String referer) throws ClientProtocolException, IOException {
		// 取消SSL�?查，使得SSL可用
		enableSSL();

		RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(defaultRequestConfig).build();

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
