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

	private void onTabClicked(View tab) {
		for(View otherTab:tabs){
			otherTab.setSelected(otherTab==tab);
		}
	}
}
