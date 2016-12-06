package com.example.helloworld;

import com.example.helloworld.fragments.PasswordRecorverStep1Fragment;
import com.example.helloworld.fragments.PasswordRecorverStep1Fragment.OnGoNextListener;
import com.example.helloworld.fragments.PasswordRecorverStep2Fragment;

import android.app.Activity;
import android.os.Bundle;

public class PasswordRecoverActivity extends Activity {

	private PasswordRecorverStep1Fragment step1 = new PasswordRecorverStep1Fragment();
	private PasswordRecorverStep2Fragment step2 = new PasswordRecorverStep2Fragment();

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
		// setCustomAnimations()方法必须在add、remove、replace调用之前被设置，否则不起作用
		getFragmentManager()
				.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
						R.anim.slide_in_left, R.anim.slide_out_right)
				.replace(R.id.container, step2).addToBackStack(null).commit();

	}
}
