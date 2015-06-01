package com.shadow.mytest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author 管黎明
 * 
 *         All rights reserved.
 */
public class MainTest1 {
	public static void main(final String[] args) throws URISyntaxException {
		final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("param1", "a1"));
		pairs.add(new BasicNameValuePair("param2", "a2"));
//		final URI uri = new URI("http", null, "localhost", 8080, "/test-action/http/a1", URLEncodedUtils.format(pairs,
//				Charset.forName("UTF-8")), null);
		final URI uri = new URI("http", null, "localhost", 8080, "/test-action/http/a1", null, null);
		final HttpPost post = new HttpPost(uri);
//		final Header header2 = new BasicHeader("ACCEPT","application/json");
		final Header header2 = new BasicHeader("Content-Type","text/html");
		post.setHeaders(new Header[]{header2});
		post.setEntity(new UrlEncodedFormEntity(pairs,Charset.forName("UTF-8")));
		try {
			final CloseableHttpResponse response = HttpClientBuilder.create()
					.setDefaultRequestConfig(RequestConfig.custom().build()).build().execute(post);
			final HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity,Charset.forName("utf-8")));
		} catch (final ClientProtocolException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
