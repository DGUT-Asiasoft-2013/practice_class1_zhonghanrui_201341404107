package com.example.helloworld.fragments;

import java.io.IOException;
import java.util.List;

import com.example.helloworld.FeedContentActivity;
import com.example.helloworld.R;
import com.example.helloworld.adapter.FeedListAdapter;
import com.example.helloworld.api.Server;
import com.example.helloworld.entity.Article;
import com.example.helloworld.entity.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeedsListFragment extends Fragment implements OnClickListener {

	private View view;
	private ListView listview;
	private List<Article> articleList;
	private FeedListAdapter listAdapter;
	protected String TAG = "OK";
	private Page<Article> page;
	private int currentPage=-1;
	private TextView tvLoadMore;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_feed_list, null);
			listview = (ListView) view.findViewById(R.id.listview);
			listAdapter = new FeedListAdapter(getActivity(), articleList);
			tvLoadMore = (TextView) inflater.inflate(R.layout.listview_loadmore, null);
			tvLoadMore.setOnClickListener(this);
			listview.addFooterView(tvLoadMore);
			listview.setAdapter(listAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					onItemSelected(position);
				}
			});
			getData();
		}
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public Page<Article> parseArticleList(String jsonString) throws Exception, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Page<Article> page = mapper.readValue(jsonString, new TypeReference<Page<Article>>() {
		});
		return page;
	}

	private void onItemSelected(int position) {

		 Intent intent=new Intent(getActivity(),FeedContentActivity.class);
		 intent.putExtra("article", (Article)listAdapter.getItem(position));
		 startActivity(intent);
	}

	public void addNewFeed(Article article){
		listAdapter.addItem(article,0);
		listAdapter.notifyDataSetChanged();
	}
	
	private void getData() {
		// 创建客户端对象
		OkHttpClient client = Server.getHttpClient();
		// 创建请求并将请求放到请求队列
		Request request = Server.getRequestBuilderWithApi("feeds/"+(currentPage+1)).build();
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
							page = parseArticleList(jsonString);
							articleList = page.getContent();
							if(articleList!=null&&articleList.size()>0){
								currentPage=page.getNumber();
								listAdapter.addData(articleList);
								listAdapter.notifyDataSetChanged();
							}
							
							tvLoadMore.setText("加载更多");
						} catch (Exception e) {
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
						tvLoadMore.setText("加载更多");
						Toast.makeText(getActivity(), arg1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	@Override
	public void onClick(View v) {
		tvLoadMore.setText("正在加载中");
		getData();
	}

}
