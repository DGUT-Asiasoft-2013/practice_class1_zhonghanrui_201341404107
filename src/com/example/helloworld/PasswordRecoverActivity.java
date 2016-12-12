package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.api.Server;
import com.example.helloworld.entity.User;
import com.example.helloworld.fragments.PasswordRecorverStep1Fragment;
import com.example.helloworld.fragments.PasswordRecorverStep1Fragment.OnGoNextListener;
import com.example.helloworld.fragments.PasswordRecorverStep2Fragment;
import com.example.helloworld.fragments.PasswordRecorverStep2Fragment.OnSubmitListener;
import com.example.helloworld.util.MD5;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PasswordRecoverActivity extends Activity {

	private PasswordRecorverStep1Fragment step1 = new PasswordRecorverStep1Fragment();
	private PasswordRecorverStep2Fragment step2 = new PasswordRecorverStep2Fragment();
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_recorver);
		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();
		step1.setOnNextListener(new OnGoNextListener() {

			@Override
			public void goNext() {
				goStep2();
			}
		});
		step2.setOnSubmitListener(new OnSubmitListener() {

			@Override
			public void onSubmit() {
				PasswordRecoverActivity.this.onSubmit();
			}
		});
	}

	private void goStep2() {
		OkHttpClient client = Server.getHttpClient();
		MultipartBody.Builder builder = new MultipartBody.Builder().addFormDataPart("email", step1.getEmailText());
		MultipartBody body = builder.build();
		Request request = Server.getRequestBuilderWithApi("inputEmail").post(body).build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {

				String jsonString = arg1.body().string();
				ObjectMapper mapper = new ObjectMapper();
				try {
					user = mapper.readValue(jsonString, User.class);
					if (user != null) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								PasswordRecoverActivity.this.onResponse(arg0, arg1);
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(PasswordRecoverActivity.this, "数据解析异常", Toast.LENGTH_SHORT).show();
						}
					});
					return;
				}

			}

			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						new AlertDialog.Builder(PasswordRecoverActivity.this).setTitle("请求失败")
								.setMessage(arg1.getLocalizedMessage()).show();
					}
				});
			}
		});

	}

	private void onSubmit() {
		String code = step2.getCode();
		String password = step2.getPassword();
		String passwordRepeat = step2.getPasswordRepeat();
		if (!password.equals(passwordRepeat)) {
			Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
			return;
		}
		OkHttpClient client = Server.getHttpClient();
		MultipartBody.Builder builder = new MultipartBody.Builder().addFormDataPart("code", code)
				.addFormDataPart("account", user.getAccount()).addFormDataPart("password", MD5.getMD5(password));
		MultipartBody body = builder.build();
		Request request = Server.getRequestBuilderWithApi("updatePwd").post(body).build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				String response = arg1.body().string();
				Log.e("ggg", response);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						step2OnResponse(arg0, arg1);
					}
				});
				return;

			}

			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						new AlertDialog.Builder(PasswordRecoverActivity.this).setTitle("请求失败")
								.setMessage(arg1.getLocalizedMessage()).show();
					}
				});
			}
		});

	}

	private void onResponse(Call call, Response response) {
		// setCustomAnimations()方法必须在add、remove、replace调用之前被设置，否则不起作用
		getFragmentManager()
				.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
						R.anim.slide_in_left, R.anim.slide_out_right)
				.replace(R.id.container, step2).addToBackStack(null).commit();
	}

	private void step2OnResponse(Call call, Response response) {
		Toast.makeText(this, "修改密码成功，请重新登录", Toast.LENGTH_SHORT).show();
		finish();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
}
