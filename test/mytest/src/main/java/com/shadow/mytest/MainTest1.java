package com.shadow.mytest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import lombok.Data;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
public class MainTest1 {
	public static void main(final String[] args) throws URISyntaxException, Exception {
		final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		String s1 = "a1";
		String s2 = "a2";
		Params params = new Params();
		params.setParam1(s1);

		// pairs.add(new BasicNameValuePair("param1", s1));
		// pairs.add(new BasicNameValuePair("param2", s2));
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update((s1 + s2).getBytes());
			byte[] tmp = md5.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : tmp) {
				sb.append(Integer.toHexString(b & 0xFF));
			}
			params.setMd5(sb.toString());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		// final URI uri = new URI("http", null, "localhost", 8080,
		// "/test-action/http/a1", URLEncodedUtils.format(pairs,
		// Charset.forName("UTF-8")), null);
		final URI uri = new URI("http", null, "localhost", 8080, "/test-action/http/a1", null, null);
		final HttpPost post = new HttpPost(uri);
		// final Header header2 = new BasicHeader("ACCEPT","application/json");
		final Header header2 = new BasicHeader("Content-Type", "text/html");
		post.setHeaders(new Header[] { header2 });
		EncrypDES3 des = new EncrypDES3();
		params.setKey(des.getDeskey());
		params.setParam2(new String(des.Encrytor(s2)));
		post.setEntity(new StringEntity((JSONObject.toJSONString(params))));
		try {
			final CloseableHttpResponse response = HttpClientBuilder.create()
					.setDefaultRequestConfig(RequestConfig.custom().build()).build().execute(post);
			final HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity, Charset.forName("utf-8")));
		} catch (final ClientProtocolException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}

@Data
class Params {
	private SecretKey key;
	private String md5;
	private String param1;
	private String param2;
}
