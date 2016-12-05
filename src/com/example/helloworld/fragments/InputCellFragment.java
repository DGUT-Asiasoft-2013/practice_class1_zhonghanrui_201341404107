package com.example.helloworld.fragments;

import com.example.helloworld.R;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class InputCellFragment extends BaseInputCellFragment {

	private TextView tvLabel;
	private EditText etContent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_input_cell, container);
		tvLabel=(TextView) view.findViewById(R.id.tvLabel);
		etContent=(EditText) view.findViewById(R.id.etContent);
		return view;
	}
	
	public void setLabel(String label){
		tvLabel.setText(label);
	}
	
	public void setInputHint(String hint){
		etContent.setHint(hint);
	}
	
	public void setPassword(boolean isPwd){
		if(isPwd){
			etContent.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}else{
			etContent.setInputType(InputType.TYPE_CLASS_TEXT);
		}
	}
}
