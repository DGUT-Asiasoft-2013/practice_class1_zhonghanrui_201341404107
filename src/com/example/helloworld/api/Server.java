package com.example.helloworld.api;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Server {

	private static OkHttpClient client;
	public static String serverAddress = "http://172.27.0.24:8080/membercenter/";
	static {
		CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

		client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(cookieManager)).build();
	}

	public static OkHttpClient getHttpClient() {
		return client;
	}

	public static Request.Builder getRequestBuilderWithApi(String api) {
		return new Request.Builder().url(serverAddress+"api/" + api);
	}
}
