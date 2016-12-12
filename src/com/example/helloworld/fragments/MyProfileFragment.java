package com.example.helloworld.fragments;

import java.io.IOException;

import com.example.helloworld.R;
import com.example.helloworld.api.Server;
import com.example.helloworld.entity.User;
import com.example.helloworld.view.AvatarView;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyProfileFragment extends Fragment {
	private View view;
	private TextView tvShow;
	private AvatarView avatar;
	private String TAG="OK";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_my_profile, null);
			tvShow = (TextView) view.findViewById(R.id.tvShow);
			avatar = (AvatarView) view.findViewById(R.id.avatar);
		}
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		OkHttpClient client = Server.getHttpClient();
		Request request = Server.getRequestBuilderWithApi("me").method("GET", null).build();
		// 异步发起请求
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				Log.e(TAG, "a");
				String jsonString = arg1.body().string();
				ObjectMapper mapper = new ObjectMapper();
				try {
					final User user = mapper.readValue(jsonString, User.class);
					if (user != null) {
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								tvShow.setText(user.getName());
								avatar.load(user);
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							tvShow.setText("解析失败");
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
						tvShow.setText("請求失败");
					}
				});
			}
		});
	}

}
