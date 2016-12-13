package com.example.helloworld;

import com.example.helloworld.entity.Article;
import com.example.helloworld.view.AvatarView;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

public class FeedContentActivity extends Activity {

	private Article article;
	private TextView tvTitle;
	private TextView tvContent;
	private TextView tvTime;
	private AvatarView avatarView;
	private TextView tvName;
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
		tvTitle.setText(article.getTitle());
		tvContent.setText(article.getText());
		tvTime.setText(new DateFormat().format("yyyy-MM-dd HH:mm", article.getCreateDate()));
		tvName.setText(article.getAuthorName());
		avatarView.load(article.getAuthorAvatar());
	}
}
