package com.example.helloworld.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.helloworld.R;
import com.example.helloworld.entity.Article;
import com.example.helloworld.view.AvatarView;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeedListAdapter extends BaseAdapter {

	private Context context;
	private List<Article> articleList;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public FeedListAdapter(Context context, List<Article> articleList) {
		this.context = context;
		this.articleList = articleList;
	}

	@Override
	public int getCount() {
		return articleList == null ? 0 : articleList.size();
	}

	@Override
	public Object getItem(int position) {
		return articleList == null ? null : articleList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.listview_item_feed, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.avatarView = (AvatarView) convertView.findViewById(R.id.avatar);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Article article = articleList.get(position);
		holder.tvTitle.setText(article.getTitle());
		holder.tvTime.setText(sdf.format(article.getCreateDate()));
		if (TextUtils.isEmpty(article.getAuthorAvatar())) {
			holder.avatarView.load("upload/face.jpg");
		} else {
			holder.avatarView.load(article.getAuthorAvatar());
		}
		holder.tvName.setText(article.getAuthorName());
		convertView.setTag(holder);
		return convertView;
	}

	private static class ViewHolder {
		TextView tvTitle;
		TextView tvTime;
		AvatarView avatarView;
		TextView tvName;
	}

	public void setData(List<Article> articleList) {
		this.articleList = articleList;
	}

	public void addData(List<Article> data) {
		if(articleList==null){
			articleList=new ArrayList<Article>();
		}
		this.articleList.addAll(data);
	}

	public void addItem(Article article) {
		this.articleList.add(article);
	}
	public void addItem(Article article,int index) {
		this.articleList.add(0,article);
	}
}
