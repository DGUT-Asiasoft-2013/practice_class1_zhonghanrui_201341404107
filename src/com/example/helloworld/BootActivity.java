package com.example.helloworld;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BootActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boot);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Handler handler = new Handler();
		// handler.postDelayed(new Runnable() {
		// public void run() {
		// startLoginActivity();
		// }
		// }, 1000);

		getData();

	}

	private void getData() {
		//创建客户端对象
		OkHttpClient client = new OkHttpClient();
		//创建请求并将请求放到请求队列
		Request request = new Request.Builder().method("GET", null).url("http://172.27.0.34:8080/membercenter/api/hello")
				.build();
		//异步发起请求
		client.newCall(request).enqueue(new Callback() {

			/**
			 * 请求成功的回调函数
			 */
			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {
				BootActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							Toast.makeText(BootActivity.this, arg1.body().string(), Toast.LENGTH_SHORT).show();
							startLoginActivity();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}

			/**
			 * 请求失败的回调函数
			 */
			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				BootActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(BootActivity.this, arg1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	/**
	 * 跳转到新的activity
	 */
	void startLoginActivity() {
		Intent itnt = new Intent(this, LoginActivity.class);
		startActivity(itnt);
		finish();
	}
}
