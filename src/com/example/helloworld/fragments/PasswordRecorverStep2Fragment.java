package com.example.helloworld.fragments;

import com.example.helloworld.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
				}
			});
		}
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		fragmentCode.setLabel("��֤��");
		fragmentCode.setInputHint("��������֤��");

		fragmentPassword.setLabel("������");
		fragmentPassword.setInputHint("������������");
		fragmentPassword.setPassword(true);

		fragmentPasswordRepeat.setLabel("�ظ�����");
		fragmentPasswordRepeat.setInputHint("���ٴ���������");
		fragmentPasswordRepeat.setPassword(true);
	}
}
