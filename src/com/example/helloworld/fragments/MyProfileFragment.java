package com.example.helloworld.fragments;

import java.io.IOException;

import com.example.helloworld.LoginActivity;
import com.example.helloworld.PasswordRecoverActivity;
import com.example.helloworld.R;
import com.example.helloworld.api.Server;
import com.example.helloworld.entity.User;
import com.example.helloworld.view.AvatarView;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyProfileFragment extends Fragment implements OnClickListener {
	private View view;
	private TextView tvName;
	private AvatarView avatar;
	private String TAG = "OK";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_my_profile, null);
			tvName = (TextView) view.findViewById(R.id.tvName);
			avatar = (AvatarView) view.findViewById(R.id.avatar);
			view.findViewById(R.id.btnUpdatePwd).setOnClickListener(this);
			view.findViewById(R.id.btnLogout).setOnClickListener(this);
		}
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		OkHttpClient client = Server.getHttpClient();
		Request request = Server.getRequestBuilderWithApi("me").build();
		// 异步发起请求
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				String jsonString = arg1.body().string();
				ObjectMapper mapper = new ObjectMapper();
				try {
					final User user = mapper.readValue(jsonString, User.class);
					if (user != null) {
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								tvName.setText(user.getName());
								avatar.load(user.getAvatar());
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							tvName.setText("解析失败");
						}
					});
					return;
				}

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						tvName.setText("請求失败");
					}
				});
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnUpdatePwd:
			updatePwd();
			break;

		case R.id.btnLogout:
			logout();
			break;
		}
	}

	public static interface OnUpdatePwdListener {
		void updatePwd();
	}

	private OnUpdatePwdListener onUpdatePwdListener;

	public void setOnUpdatePwdListener(OnUpdatePwdListener onUpdatePwdListener) {
		this.onUpdatePwdListener = onUpdatePwdListener;
	}

	private void updatePwd() {
		if (onUpdatePwdListener != null) {
			onUpdatePwdListener.updatePwd();
		}
	}

	private void logout() {
		Request request = Server.getRequestBuilderWithApi("logout").build();
		Server.getHttpClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				goLogin();
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				goLogin();
			}
		});
	}

	private void goLogin() {
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		startActivity(intent);
		getActivity().finish();
	}

}
