package com.example.helloworld;

import com.example.helloworld.fragments.InputCellFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginActivity extends Activity {

	private InputCellFragment fragmentAccount;
	private InputCellFragment fragmentPassword;

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
		Intent intent = new Intent(this, HelloWorldActivity.class);
		startActivity(intent);
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

	}
}
