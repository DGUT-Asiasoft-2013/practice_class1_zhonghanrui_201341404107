package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.fragments.InputCellFragment;
import com.example.helloworld.util.MD5;
import com.example.helloworld.util.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends Activity {

	private InputCellFragment fragmentAccount;
	private InputCellFragment fragmentPassword;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		findViewById(R.id.tvRegister).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goRegister();
			}
		});
		findViewById(R.id.btnLogin).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goLogin();
			}
		});
		findViewById(R.id.tvFindPwd).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				findPwd();
			}
		});
		fragmentAccount = (InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentAccount);
		fragmentPassword = (InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentPassword);
		progressDialog=Util.getProgressDialog(this);
	}

	private void findPwd() {
		Intent intent = new Intent(this, PasswordRecoverActivity.class);
		startActivity(intent);
	}

	private void goRegister() {
		Intent itnt = new Intent(this, RegisterActivity.class);
		startActivity(itnt);
	}

	private void goLogin() {
		String account=fragmentAccount.getText();
		String password=fragmentAccount.getText();
		progressDialog.show();
		
		OkHttpClient client = new OkHttpClient();

		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("account", account).addFormDataPart("password", MD5.getMD5(password));
		MultipartBody body = builder.build();

		Request request = new Request.Builder().method("GET", null).post(body)
				.url("http://172.27.0.34:8080/membercenter/api/login").build();
		// 异步发起请求
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						LoginActivity.this.onResponse(arg0, arg1);
					}
				});
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						LoginActivity.this.onFailure(arg0, arg1);
					}
				});
			}
		});
		
	}

	private void onResponse(Call call, Response response) {
		progressDialog.dismiss();
		try {
			new AlertDialog.Builder(this).setTitle("登陆成功").setMessage(response.body().string())
					.setNegativeButton("确定", null).show();
			Intent intent = new Intent(this, HelloWorldActivity.class);
			startActivity(intent);
		} catch (IOException e) {
			e.printStackTrace();
			onFailure(call, e);
		}
	}

	private void onFailure(Call call, Exception e) {
		progressDialog.dismiss();
		e.printStackTrace();
		new AlertDialog.Builder(this).setTitle("登陆失败").setMessage(e.getLocalizedMessage()).show();
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		fragmentAccount.setLabel("登陆名");
		fragmentAccount.setInputHint("请输入登陆名");
		fragmentAccount.setPassword(false);

		fragmentPassword.setLabel("密码");
		fragmentPassword.setInputHint("请输入密码");
		fragmentPassword.setPassword(true);

	}
}
