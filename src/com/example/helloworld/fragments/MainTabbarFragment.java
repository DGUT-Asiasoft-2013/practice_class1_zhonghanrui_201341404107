package com.example.helloworld.fragments;

import com.example.helloworld.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MainTabbarFragment extends Fragment {

	View btnNew,tabFeed,tabNote,tabSearch,tabMe;
	View[] tabs;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_main_tabbar, null);
		
		btnNew=view.findViewById(R.id.btnAdd);
		tabFeed=view.findViewById(R.id.layoutFeeds);
		tabNote=view.findViewById(R.id.layoutNotes);
		tabSearch=view.findViewById(R.id.layoutSearch);
		tabMe=view.findViewById(R.id.layoutMe);
		
		tabs=new View[]{tabFeed,tabNote,tabSearch,tabMe};
		for(final View tab:tabs){
			tab.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onTabClicked(tab);
				}
			});
		}
		return view;
	}
	
	public void setSelectedItem(int index){
		if(index>=0&&index<tabs.length){
			onTabClicked(tabs[index]);
		}
	}
	
	public static interface OnTabSelectedListener{
		void onTabSelected(int index);
	}
	private OnTabSelectedListener onTabSelectedListener;
	
	public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
		this.onTabSelectedListener = onTabSelectedListener;
	}

	private void onTabClicked(View tab) {
		int selectedIndex=-1;
		for(int i=0;i<tabs.length;i++){
			View view=tabs[i];
			if(view==tab){
				view.setSelected(true);
				selectedIndex=i;
			}else{
				view.setSelected(false);
			}
		}
		if(onTabSelectedListener!=null&&selectedIndex>=0){
			onTabSelectedListener.onTabSelected(selectedIndex);
		}
	}
}
