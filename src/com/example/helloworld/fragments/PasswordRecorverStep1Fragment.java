package com.example.helloworld.fragments;

import com.example.helloworld.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class PasswordRecorverStep1Fragment extends Fragment {

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_password_recorver_step1, null);

			InputCellFragment inputCellFragment = (InputCellFragment) getFragmentManager()
					.findFragmentById(R.id.fragmentEmail);
			inputCellFragment.setLabel("◊¢≤·” œ‰");
			inputCellFragment.setInputHint(" ‰»Î◊¢≤·” œ‰µÿ÷∑");
			view.findViewById(R.id.btnNext).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					goNext();
				}
			});
		}
		return view;
	}

	private void goNext() {
		if (onGoNextListener != null) {
			onGoNextListener.goNext();
		}
	}

	public static interface OnGoNextListener {
		void goNext();
	}

	private OnGoNextListener onGoNextListener;

	public void setOnNextListener(OnGoNextListener onGoNextListener) {
		this.onGoNextListener = onGoNextListener;
	}
}
