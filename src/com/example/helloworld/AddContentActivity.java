package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.api.Server;
import com.example.helloworld.entity.Article;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddContentActivity extends Activity {

	private EditText etTitle;
	private EditText etContent;
	private Button btnSubmit;
	private String TAG="OK";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_content);
		etTitle = (EditText) findViewById(R.id.etTitle);
		etContent = (EditText) findViewById(R.id.etContent);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onSubmit();
			}
		});
	}

	private void onSubmit() {
		String title = etTitle.getText().toString();
		String content = etContent.getText().toString();
		if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
			Toast.makeText(this, "标题或内容不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		OkHttpClient client = Server.getHttpClient();
		MultipartBody.Builder builder = new MultipartBody.Builder().addFormDataPart("title", title)
				.addFormDataPart("text", content);
		MultipartBody body = builder.build();
		Request request = Server.getRequestBuilderWithApi("article").post(body).build();
		// 异步发起请求
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				String jsonString = arg1.body().string();
				Log.e(TAG, jsonString);
				ObjectMapper mapper = new ObjectMapper();
				try {
					final Article article = mapper.readValue(jsonString, Article.class);
					if (article != null) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								onSuccess(article);
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(AddContentActivity.this, "解析文章失败", Toast.LENGTH_SHORT).show();
						}
					});
				}

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(AddContentActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});

	}
	
	private void onSuccess(Article article){
		Toast.makeText(this, "添加文章成功", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent();
		intent.putExtra("article", article);
		setResult(RESULT_OK, intent);
		finish();
	}

	/**
	 * 后退动画
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.slide_out_bottom);
	}
}
