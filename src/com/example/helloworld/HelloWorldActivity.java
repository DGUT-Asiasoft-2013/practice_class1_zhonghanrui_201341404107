package com.example.helloworld;

import com.example.helloworld.fragments.FeedsListFragment;
import com.example.helloworld.fragments.MainTabbarFragment;
import com.example.helloworld.fragments.MainTabbarFragment.OnTabSelectedListener;
import com.example.helloworld.fragments.MyProfileFragment;
import com.example.helloworld.fragments.NoteListFragment;
import com.example.helloworld.fragments.SearchPageFragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class HelloWorldActivity extends Activity {

	private FeedsListFragment contentFeedList = new FeedsListFragment();
	private NoteListFragment contentNoteList = new NoteListFragment();
	private SearchPageFragment contentSearchPage = new SearchPageFragment();
	private MyProfileFragment contentMyProfile = new MyProfileFragment();
	private MainTabbarFragment tabbarFragment = new MainTabbarFragment();
	private Fragment[] frags;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helloworld);
		frags = new Fragment[] { contentFeedList, contentNoteList, contentSearchPage, contentMyProfile };
		tabbarFragment = (MainTabbarFragment) getFragmentManager().findFragmentById(R.id.fragmentTabbar);
		tabbarFragment.setOnTabSelectedListener(new OnTabSelectedListener() {

			@Override
			public void onTabSelected(int index) {
				contentChange(index);
			}
		});
		tabbarFragment.setSelectedItem(0);
	}

	private void contentChange(int index) {
		getFragmentManager().beginTransaction().replace(R.id.container, frags[index]).commit();
	}

}
