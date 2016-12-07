package com.example.helloworld.fragments;

import com.example.helloworld.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FeedsListFragment extends Fragment {

	private View view;
	private ListView listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_feed_list, null);
			listview = (ListView) view.findViewById(R.id.listview);
			listview.setAdapter(listAdapter);
		}
		return view;
	}

	BaseAdapter listAdapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				view = View.inflate(getActivity(), android.R.layout.simple_list_item_1, null);
			}else{
				view=convertView;
			}
			TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
			textView1.setText("This is row " + position);
			return view;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public Object getItem(int position) {
			return "";
		}

		@Override
		public int getCount() {
			return 20;
		}
	};
}
