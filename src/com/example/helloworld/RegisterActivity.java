package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.fragments.InputCellFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends Activity {
	
	private InputCellFragment fragmentAccount;
	private InputCellFragment fragmentName;
	private InputCellFragment fragmentPassword;
	private InputCellFragment fragmentPasswordRepeat;
	private InputCellFragment fragmentEmail;
	private ProgressDialog progressDialog;
	private String TAG="ggg";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_register);
		fragmentAccount=(InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentAccount);
		fragmentName=(InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentName);
		fragmentPassword=(InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentPassword);
		fragmentPasswordRepeat=(InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentPasswordRepeat);
		fragmentEmail=(InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentEmail);
		findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onRegister();
			}
		});
		progressDialog=new ProgressDialog(this);
		progressDialog.setMessage("���Ժ�");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
	}

	private void onRegister() {
		String password=fragmentPassword.getText();
		String passwordRepeat=fragmentPasswordRepeat.getText();
		if(!password.equals(passwordRepeat)){
			Toast.makeText(this, "������������벻һ��", Toast.LENGTH_SHORT).show();
			return;
		}
		String account=fragmentAccount.getText();
		String email=fragmentEmail.getText();
		String name=fragmentName.getText();
		
		progressDialog.show();
		
		OkHttpClient client=new OkHttpClient();
		RequestBody body=new MultipartBody.Builder().addFormDataPart("account", account)
				.addFormDataPart("name", name)
				.addFormDataPart("password", password)
				.addFormDataPart("email", email).build();
		Request request = new Request.Builder().method("GET", null).
				post(body)
				.url("http://172.27.0.34:8080/membercenter/api/register")
				.build();
		//�첽��������
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						RegisterActivity.this.onResponse(arg0, arg1);
					}
				});
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				RegisterActivity.this.onFailure(arg0, arg1);
			}
		});
	}

	private void onResponse(Call call, Response response){
		progressDialog.dismiss();
		try {
			new AlertDialog.Builder(this).setTitle("����ɹ�").setMessage(response.body().string())
			.setNegativeButton("ȷ��", null)
			.show();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
			onFailure(call, e);
		}
	}
	
	private void onFailure(Call call, Exception e){
		progressDialog.dismiss();
		Log.e(TAG, "�쳣"+e.getMessage());
		new AlertDialog.Builder(this).setTitle("����ʧ��").setMessage(e.getLocalizedMessage()).show();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		fragmentAccount.setLabel("��½��");
		fragmentAccount.setInputHint("�������½��");
		fragmentAccount.setPassword(false);
		
		fragmentPassword.setLabel("����");
		fragmentPassword.setInputHint("����������");
		fragmentPassword.setPassword(true);
		
		fragmentPasswordRepeat.setLabel("�ظ�����");
		fragmentPasswordRepeat.setInputHint("�������ظ�����");
		fragmentPasswordRepeat.setPassword(true);
		
		fragmentEmail.setLabel("ע������");
		fragmentEmail.setInputHint("����������");
		
		fragmentName.setLabel("�ǳ�");
		fragmentName.setInputHint("�������ǳ�");
	}
}
