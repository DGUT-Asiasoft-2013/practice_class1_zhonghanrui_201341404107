package com.example.helloworld;

import com.example.helloworld.fragments.PasswordRecorverStep1Fragment;
import com.example.helloworld.fragments.PasswordRecorverStep1Fragment.OnGoNextListener;
import com.example.helloworld.fragments.PasswordRecorverStep2Fragment;

import android.app.Activity;
import android.os.Bundle;

public class PasswordRecoverActivity extends Activity {

	private PasswordRecorverStep1Fragment step1=new PasswordRecorverStep1Fragment();
	private PasswordRecorverStep2Fragment step2=new PasswordRecorverStep2Fragment();
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
	}

	private void goStep2() {
		getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, step2).commit();
	}
}
