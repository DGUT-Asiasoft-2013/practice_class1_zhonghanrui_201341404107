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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchPageFragment extends Fragment {

	private View view;
	private EditText etSearch;
	private ListView lvSearchArticle;
	private FeedListAdapter listAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_search_page, null);
			etSearch = (EditText) view.findViewById(R.id.etSearch);
			view.findViewById(R.id.ivSearch).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					search();
				}
			});
			lvSearchArticle = (ListView) view.findViewById(R.id.listview);
			listAdapter = new FeedListAdapter(getActivity(), null);
			lvSearchArticle.setAdapter(listAdapter);
			lvSearchArticle.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					onItemSelected(position);
				}
			});
			etSearch.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					search();
				}
			});
		}
		return view;
	}

	private void onItemSelected(int position) {

		Intent intent = new Intent(getActivity(), FeedContentActivity.class);
		intent.putExtra("article", (Article) listAdapter.getItem(position));
		startActivity(intent);
	}

	private void search() {
		String keyword = etSearch.getText().toString();
		OkHttpClient client = Server.getHttpClient();
		Request request = Server.getRequestBuilderWithApi("article/s/" + keyword).build();
		// 异步发起请求
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {

				final String jsonString = arg1.body().string();
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							Page<Article> page = parseArticleList(jsonString);
							List<Article> articleList = page.getContent();
							listAdapter.setData(articleList);
							listAdapter.notifyDataSetChanged();
						} catch (Exception e) {
							e.printStackTrace();
							listAdapter.setData(null);
							listAdapter.notifyDataSetChanged();
						}
					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	public Page<Article> parseArticleList(String jsonString) throws Exception, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Page<Article> page = mapper.readValue(jsonString, new TypeReference<Page<Article>>() {
		});
		return page;
	}
}
