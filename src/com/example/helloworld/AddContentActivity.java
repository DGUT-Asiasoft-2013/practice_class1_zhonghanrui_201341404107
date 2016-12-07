package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;

public class AddContentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_content);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.slide_out_bottom);
	}
}
