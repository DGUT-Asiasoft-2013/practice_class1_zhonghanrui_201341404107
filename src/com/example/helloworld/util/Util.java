package com.example.helloworld.util;

import android.app.Activity;
import android.app.ProgressDialog;

public class Util {

	public static ProgressDialog getProgressDialog(Activity act) {
		ProgressDialog progressDialog = new ProgressDialog(act);
		progressDialog.setMessage("���Ժ�");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
		return progressDialog;
	}
}
