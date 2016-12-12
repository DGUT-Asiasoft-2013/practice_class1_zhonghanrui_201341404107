package com.example.helloworld.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.helloworld.R;
import com.example.helloworld.adapter.FeedListAdapter;
import com.example.helloworld.api.Server;
import com.example.helloworld.entity.Article;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeedsListFragment extends Fragment {

	private View view;
	private ListView listview;
	private List<Article> articleList;
	private FeedListAdapter listAdapter;
	protected String TAG="OK";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_feed_list, null);
			listview = (ListView) view.findViewById(R.id.listview);
			listAdapter=new FeedListAdapter(getActivity(), articleList);
			listview.setAdapter(listAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					onItemSelected(position);
				}
			});
		}
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();

		// 创建客户端对象
		OkHttpClient client = Server.getHttpClient();
		// 创建请求并将请求放到请求队列
		Request request = Server.getRequestBuilderWithApi("articles/7").build();
		// 异步发起请求
		client.newCall(request).enqueue(new Callback() {

			/**
			 * 请求成功的回调函数
			 */
			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {
				final String jsonString = arg1.body().string();
				Log.e(TAG, jsonString);
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							try {
								articleList=parseArticleList(jsonString);
								listAdapter.setData(articleList);
								listAdapter.notifyDataSetChanged();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
			}

			/**
			 * 请求失败的回调函数
			 */
			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(getActivity(), arg1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	
	}
	public List<Article> parseArticleList(String jsonString) throws Exception, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Article.class);
		List<Article> list = (List<Article>) mapper.readValue(jsonString, javaType);
		return list;
	}
	private void onItemSelected(int position){
		
//		Intent intent=new Intent(getActivity(),FeedContentActivity.class);
//		intent.putExtra("text", text);
//		startActivity(intent);
	}

}
