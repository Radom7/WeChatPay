package utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * @author 林尤庆
 * @date 2018年3月31日 下午5:21:17
 * @version 1.0
 */
public class HttpsRequest {

	public static String httpsRequest(String requestUrl, String requestMethod, String outStr) throws Exception {

		// 创建 SSLContext
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		TrustManager[] tm = { new MyX509TrustManager() };
		// 初始化
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 获取sslSOCKETfactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		// 设置当前实例使用sslSOCKETfactory

		StringBuffer buffer = null;
		URL url = new URL(requestUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

		conn.setRequestMethod(requestMethod);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setSSLSocketFactory(ssf);
		conn.connect();

		if (outStr != null) {

			OutputStream os = conn.getOutputStream();
			os.write(outStr.getBytes("UTF-8"));
			os.close();

		}

		// 读取服务端的内容
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		buffer = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}

		// 打印数据

		return buffer.toString();

	}

}
