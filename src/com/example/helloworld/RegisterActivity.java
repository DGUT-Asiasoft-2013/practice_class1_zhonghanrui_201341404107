package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.fragments.InputCellFragment;
import com.example.helloworld.fragments.InputCellPictureFragment;
import com.example.helloworld.util.MD5;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
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
	private InputCellPictureFragment fragmentPic;
	private ProgressDialog progressDialog;
	private String TAG = "ggg";
	private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);
		fragmentAccount = (InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentAccount);
		fragmentName = (InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentName);
		fragmentPassword = (InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentPassword);
		fragmentPasswordRepeat = (InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentPasswordRepeat);
		fragmentEmail = (InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentEmail);
		fragmentPic = (InputCellPictureFragment) getFragmentManager().findFragmentById(R.id.fragmentPic);
		findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onRegister();
			}
		});
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("«Î…‘∫Û");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
	}

	private void onRegister() {
		String password = fragmentPassword.getText();
		String passwordRepeat = fragmentPasswordRepeat.getText();
		if (!password.equals(passwordRepeat)) {
			Toast.makeText(this, "¡Ω¥Œ ‰»Îµƒ√‹¬Î≤ª“ª÷¬", Toast.LENGTH_SHORT).show();
			return;
		}
		String account = fragmentAccount.getText();
		String email = fragmentEmail.getText();
		String name = fragmentName.getText();

		progressDialog.show();

		OkHttpClient client = new OkHttpClient();

		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("account", account).addFormDataPart("name", name).addFormDataPart("password", MD5.getMD5(password))
				.addFormDataPart("email", email);
		builder.addFormDataPart("avatar", "avatar", RequestBody.create(MEDIA_TYPE_PNG, fragmentPic.getPngData()));
		MultipartBody body = builder.build();

		Request request = new Request.Builder().method("GET", null).post(body)
				.url("http://172.27.0.34:8080/membercenter/api/register").build();
		// “Ï≤Ω∑¢∆«Î«Û
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

	private void onResponse(Call call, Response response) {
		progressDialog.dismiss();
		try {
			new AlertDialog.Builder(this).setTitle("«Î«Û≥…π¶").setMessage(response.body().string())
					.setNegativeButton("»∑∂®", null).show();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
			onFailure(call, e);
		}
	}

	private void onFailure(Call call, Exception e) {
		progressDialog.dismiss();
		Log.e(TAG, "“Ï≥£" + e.getMessage());
		new AlertDialog.Builder(this).setTitle("«Î«Û ß∞‹").setMessage(e.getLocalizedMessage()).show();
	}

	@Override
	protected void onResume() {
		super.onResume();

		fragmentAccount.setLabel("µ«¬Ω√˚");
		fragmentAccount.setInputHint("«Î ‰»Îµ«¬Ω√˚");
		fragmentAccount.setPassword(false);

		fragmentPassword.setLabel("√‹¬Î");
		fragmentPassword.setInputHint("«Î ‰»Î√‹¬Î");
		fragmentPassword.setPassword(true);

		fragmentPasswordRepeat.setLabel("÷ÿ∏¥√‹¬Î");
		fragmentPasswordRepeat.setInputHint("«Î ‰»Î÷ÿ∏¥√‹¬Î");
		fragmentPasswordRepeat.setPassword(true);

		fragmentEmail.setLabel("◊¢≤·” œ‰");
		fragmentEmail.setInputHint("«Î ‰»Î” œ‰");

		fragmentName.setLabel("Í«≥∆");
		fragmentName.setInputHint("«Î ‰»ÎÍ«≥∆");
	}
}
