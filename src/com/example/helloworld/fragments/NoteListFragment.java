package com.example.helloworld.fragments;

import java.io.IOException;
import java.util.List;

import com.example.helloworld.R;
import com.example.helloworld.adapter.CommentListAdapter;
import com.example.helloworld.api.Server;
import com.example.helloworld.entity.Comment;
import com.example.helloworld.entity.Page;
import com.example.helloworld.util.MD5;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NoteListFragment extends Fragment {

	private View view;
	protected String TAG = "NoteListFragment";
	private Switch switchComment;
	private ListView lvComment;
	private CommentListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_note_list, null);
			switchComment = (Switch) view.findViewById(R.id.switchComment);
			switchComment.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {// 获取发出的评论
						getData(false);
					} else {// 获取收到的评论
						getData(true);
					}
				}
			});
			lvComment = (ListView) view.findViewById(R.id.lvComment);
			adapter = new CommentListAdapter(getActivity(), null);
			lvComment.setAdapter(adapter);
			getData(true);
		}
		return view;
	}

	private void getData(boolean isReceive) {
		OkHttpClient client = Server.getHttpClient();
		Request request = Server.getRequestBuilderWithApi("article/comments/" + isReceive).build();
		// 异步发起请求
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {

				final String jsonString = arg1.body().string();
				Log.e(TAG, jsonString);
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ObjectMapper mapper = new ObjectMapper();
						try {
							List<Comment> data = mapper.readValue(jsonString, new TypeReference<List<Comment>>() {
							});
							adapter.setData(data);
							adapter.notifyDataSetChanged();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}
}
