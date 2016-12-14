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
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import okhttp3.MultipartBody;
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
	private TextView btnLike;
	private ListView lvComment;
	protected String TAG = "FeedContentActivity";
	private CommentListAdapter adapter;
	private boolean likeState;

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
		btnLike = (TextView) findViewById(R.id.btnLike);
		lvComment = (ListView) findViewById(R.id.lvComment);
		tvTitle.setText(article.getTitle());
		tvContent.setText(article.getText());
		tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(article.getCreateDate()));
		tvName.setText(article.getAuthorName());
		avatarView.load(article.getAuthorAvatar() == null ? "upload/face.jpg" : article.getAuthorAvatar());
		tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
		btnComment.setOnClickListener(this);
		btnLike.setOnClickListener(this);
		adapter = new CommentListAdapter(this, null);
		lvComment.setAdapter(adapter);
		checkLiked();
		getLikeCount();
	}

	private void getLikeCount() {

		OkHttpClient client = Server.getHttpClient();
		Request request = Server.getRequestBuilderWithApi("article/" + article.getId() + "/likes").build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				final String response = arg1.body().string();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						int count=Integer.parseInt(response);
						btnLike.setText(""+count);
					}
				});
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	
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

		case R.id.btnLike:
			changeLikeState();
			break;
		}
	}

	private void checkLiked() {
		OkHttpClient client = Server.getHttpClient();
		Request request = Server.getRequestBuilderWithApi("article/" + article.getId() + "/isliked").build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				final String response = arg1.body().string();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						likeState = Boolean.parseBoolean(response);
						setLikeStateDrawable();
					}
				});
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	private void changeLikeState() {
		// 如果当前是处于点赞状态，则将点赞取消
		boolean state=false;
		if (likeState) {
			state=false;
		} else {
			state=true;
		}
		OkHttpClient client = Server.getHttpClient();

		MultipartBody body = new MultipartBody.Builder().addFormDataPart("likes", state+"").build();

		Request request = Server.getRequestBuilderWithApi("article/" + article.getId() + "/likes").post(body).build();
		// 异步发起请求
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				final String response=arg1.body().string();
				Log.e(TAG, response);
				likeState=!likeState;
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						setLikeStateDrawable();
						int count=Integer.parseInt(response);
						btnLike.setText(""+count);
					}
				});
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}
	
	private void setLikeStateDrawable(){
		Drawable nav_up=null;
		if(likeState){
			nav_up=getResources().getDrawable(R.drawable.like_press); 
		}else{
			nav_up=getResources().getDrawable(R.drawable.like);
		}
		nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());  
		btnLike.setCompoundDrawables(nav_up, null, null, null); 
	}
}
