package com.example.helloworld;

import com.example.helloworld.fragments.InputCellFragment;

import android.app.Activity;
import android.os.Bundle;

public class RegisterActivity extends Activity {
	
	private InputCellFragment fragmentAccount;
	private InputCellFragment fragmentPassword;
	private InputCellFragment fragmentPasswordRepeat;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_register);
		fragmentAccount=(InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentAccount);
		fragmentPassword=(InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentPassword);
		fragmentPasswordRepeat=(InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentPasswordRepeat);
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
	}
}
