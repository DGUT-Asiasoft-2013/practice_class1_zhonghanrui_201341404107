package com.example.helloworld.fragments;

import com.example.helloworld.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class PasswordRecorverStep2Fragment extends Fragment {

	private InputCellFragment fragmentCode;
	private InputCellFragment fragmentPassword;
	private InputCellFragment fragmentPasswordRepeat;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (view == null) {
			view = inflater.inflate(R.layout.fragment_password_recorver_step2, null);

			fragmentCode = (InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentCode);
			fragmentPassword = (InputCellFragment) getFragmentManager().findFragmentById(R.id.fragmentPassword);
			fragmentPasswordRepeat = (InputCellFragment) getFragmentManager()
					.findFragmentById(R.id.fragmentPasswordRepeat);
			view.findViewById(R.id.btnSubmit).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onSubmit();
				}
			});
		}
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		fragmentCode.setLabel("验证码");
		fragmentCode.setInputHint("请输入验证码");

		fragmentPassword.setLabel("新密码");
		fragmentPassword.setInputHint("请输入新密码");
		fragmentPassword.setPassword(true);

		fragmentPasswordRepeat.setLabel("重复密码");
		fragmentPasswordRepeat.setInputHint("请再次输入密码");
		fragmentPasswordRepeat.setPassword(true);
	}
	
	private void onSubmit() {
		if (onSubmitListener != null) {
			onSubmitListener.onSubmit();
		}
	}
	

	public static interface OnSubmitListener {
		void onSubmit();
	}

	private OnSubmitListener onSubmitListener;

	public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
		this.onSubmitListener = onSubmitListener;
	}
	
	public String getCode(){
		return fragmentCode.getText();
	}
	public String getPassword(){
		return fragmentPassword.getText();
	}
	public String getPasswordRepeat(){
		return fragmentPasswordRepeat.getText();
	}
}
