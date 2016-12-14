package com.example.helloworld;

import java.text.SimpleDateFormat;

import com.example.helloworld.entity.Article;
import com.example.helloworld.view.AvatarView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FeedContentActivity extends Activity implements OnClickListener {

	private Article article;
	private TextView tvTitle;
	private TextView tvContent;
	private TextView tvTime;
	private AvatarView avatarView;
	private TextView tvName;
	private Button btnComment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		article=(Article) getIntent().getSerializableExtra("article");
		setContentView(R.layout.activity_feed_content_detail);
		tvTitle=(TextView) findViewById(R.id.tvTitle);
		tvContent=(TextView) findViewById(R.id.tvContent);
		tvTime=(TextView) findViewById(R.id.tvTime);
		tvName=(TextView) findViewById(R.id.tvName);
		avatarView=(AvatarView) findViewById(R.id.avatar);
		btnComment=(Button) findViewById(R.id.btnComment);
		tvTitle.setText(article.getTitle());
		tvContent.setText(article.getText());
		tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(article.getCreateDate()));
		tvName.setText(article.getAuthorName());
		avatarView.load(article.getAuthorAvatar()==null?"upload/face.jpg":article.getAuthorAvatar());
		tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
		btnComment.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnComment:
			Intent intent=new Intent(this,AddCommentActivity.class);
			intent.putExtra("article", article);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
