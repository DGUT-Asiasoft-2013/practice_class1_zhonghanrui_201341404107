package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class FeedContentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String text=getIntent().getStringExtra("text");
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
