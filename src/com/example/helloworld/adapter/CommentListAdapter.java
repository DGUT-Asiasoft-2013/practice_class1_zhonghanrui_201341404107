package com.example.helloworld.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.helloworld.R;
import com.example.helloworld.entity.Article;
import com.example.helloworld.entity.Comment;
import com.example.helloworld.view.AvatarView;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentListAdapter extends BaseAdapter {

	private Context context;
	private List<Comment> commentList;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public CommentListAdapter(Context context, List<Comment> commentList) {
		this.context = context;
		this.commentList = commentList;
	}

	@Override
	public int getCount() {
		return commentList == null ? 0 : commentList.size();
	}

	@Override
	public Object getItem(int position) {
		return commentList == null ? null : commentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.listview_item_comment, null);
			holder = new ViewHolder();
			holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.avatarView = (AvatarView) convertView.findViewById(R.id.avatar);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Comment comment = commentList.get(position);
		holder.tvContent.setText(comment.getText());
		holder.tvTime.setText(sdf.format(comment.getCreateDate()));
		if (TextUtils.isEmpty(comment.getAuthor().getAvatar())) {
			holder.avatarView.load("upload/face.jpg");
		} else {
			holder.avatarView.load(comment.getAuthor().getAvatar());
		}
		holder.tvName.setText(comment.getAuthor().getName());
		convertView.setTag(holder);
		return convertView;
	}

	private static class ViewHolder {
		TextView tvContent;
		TextView tvTime;
		AvatarView avatarView;
		TextView tvName;
	}

	public void setData(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public void addData(List<Comment> data) {
		if(commentList==null){
			commentList=new ArrayList<Comment>();
		}
		this.commentList.addAll(data);
	}

}
