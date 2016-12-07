package com.example.helloworld.fragments;

import com.example.helloworld.FeedContentActivity;
import com.example.helloworld.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FeedsListFragment extends Fragment {

	private View view;
	private ListView listview;
	private String[] arrays;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_feed_list, null);
			listview = (ListView) view.findViewById(R.id.listview);
			initList();
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
	
	private void onItemSelected(int position){
		String text=arrays[position];
		Intent intent=new Intent(getActivity(),FeedContentActivity.class);
		intent.putExtra("text", text);
		startActivity(intent);
	}

	private void initList() {
		arrays = new String[] { "刘一", "陈二", "张三", "李四", "王五", "赵六", "孙七", "周八", "吴九", "郑十", "刘一", "陈二", "张三", "李四",
				"王五", "赵六", "孙七", "周八", "吴九", "郑十", "刘一", "陈二", "张三", "李四", "王五", "赵六", "孙七", "周八", "吴九", "郑十" };
	}

	BaseAdapter listAdapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				view = View.inflate(getActivity(), android.R.layout.simple_list_item_1, null);
			} else {
				view = convertView;
			}
			TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
			textView1.setText("Name: " +arrays[position]);
			return view;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public Object getItem(int position) {
			return arrays[position];
		}

		@Override
		public int getCount() {
			return arrays.length;
		}
	};
}
