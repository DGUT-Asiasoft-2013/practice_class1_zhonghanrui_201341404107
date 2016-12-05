package com.example.helloworld.fragments;

import com.example.helloworld.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class InputCellPictureFragment extends BaseInputCellFragment {

	private TextView tvLabel;
	private TextView tvHint;
	private ImageView ivImg;
	private static final String TAG = "ggg";
	private static final int REQUEST_PHOTO=0;
	private static final int REQUEST_CHOOSE_PHOTO=1;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_inputcell_picutre, container);
		tvLabel = (TextView) view.findViewById(R.id.tvLabel);
		tvHint = (TextView) view.findViewById(R.id.tvHint);
		ivImg = (ImageView) view.findViewById(R.id.ivImg);
		ivImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setImg();
			}
		});
		return view;
	}

	private void setImg() {
		Log.e(TAG, "HELLO");
		String[] items = new String[] { "≈ƒ’’", "±æµÿ" };
		new AlertDialog.Builder(getActivity()).setTitle("—°‘ÒÕº∆¨").setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					takePhoto();
					break;

				case 1:
					choosePhoto();
					break;
				}
			}
		}).show();
	}

	private void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, REQUEST_PHOTO);
	}

	private void choosePhoto() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_CANCELED)
			return;
		if (requestCode == REQUEST_PHOTO) {
			Bitmap bmp = (Bitmap) data.getExtras().get("data");
			ivImg.setImageBitmap(bmp);
		}else if(requestCode==REQUEST_CHOOSE_PHOTO){
			try {
				Bitmap bmp=Media.getBitmap(getActivity().getContentResolver(), data.getData());
				ivImg.setImageBitmap(bmp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setLabel(String label) {
		tvLabel.setText(label);
	}

	public void setInputHint(String hint) {
		tvHint.setHint(hint);
	}

}
