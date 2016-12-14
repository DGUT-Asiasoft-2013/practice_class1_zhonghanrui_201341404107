package com.example.helloworld;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.example.helloworld.adapter.CommentListAdapter;
import com.example.helloworld.api.Server;
import com.example.helloworld.entity.Article;
import com.example.helloworld.entity.Comment;
import com.example.helloworld.entity.Page;
import com.example.helloworld.view.AvatarView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeedContentActivity extends Activity implements OnClickListener {

	private Article article;
	private TextView tvTitle;
	private TextView tvContent;
	private TextView tvTime;
	private AvatarView avatarView;
	private TextView tvName;
	private Button btnComment;
	private ListView lvComment;
	protected String TAG = "FeedContentActivity";
	private CommentListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		article = (Article) getIntent().getSerializableExtra("article");
		setContentView(R.layout.activity_feed_content_detail);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvContent = (TextView) findViewById(R.id.tvContent);
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvName = (TextView) findViewById(R.id.tvName);
		avatarView = (AvatarView) findViewById(R.id.avatar);
		btnComment = (Button) findViewById(R.id.btnComment);
		lvComment = (ListView) findViewById(R.id.lvComment);
		tvTitle.setText(article.getTitle());
		tvContent.setText(article.getText());
		tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(article.getCreateDate()));
		tvName.setText(article.getAuthorName());
		avatarView.load(article.getAuthorAvatar() == null ? "upload/face.jpg" : article.getAuthorAvatar());
		tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
		btnComment.setOnClickListener(this);
		adapter = new CommentListAdapter(this, null);
		lvComment.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getComment();
	}

	private void getComment() {
		OkHttpClient client = Server.getHttpClient();
		Request request = Server.getRequestBuilderWithApi("article/" + article.getId() + "/comments").build();
		// 异步发起请求
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				final String jsonString = arg1.body().string();
				Log.e(TAG, jsonString);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ObjectMapper mapper = new ObjectMapper();
						Page<Comment> page;
						try {
							page = mapper.readValue(jsonString, new TypeReference<Page<Comment>>() {
							});
							adapter.setData(page.getContent());
							adapter.notifyDataSetChanged();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnComment:
			Intent intent = new Intent(this, AddCommentActivity.class);
			intent.putExtra("article", article);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
