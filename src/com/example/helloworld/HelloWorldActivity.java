package com.example.helloworld;

import com.example.helloworld.entity.Article;
import com.example.helloworld.fragments.FeedsListFragment;
import com.example.helloworld.fragments.MainTabbarFragment;
import com.example.helloworld.fragments.MainTabbarFragment.OnTabSelectedListener;
import com.example.helloworld.fragments.MainTabbarFragment.onAddNewFeedListener;
import com.example.helloworld.fragments.MyProfileFragment;
import com.example.helloworld.fragments.MyProfileFragment.OnUpdatePwdListener;
import com.example.helloworld.fragments.NoteListFragment;
import com.example.helloworld.fragments.SearchPageFragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
		tabbarFragment.setOnAddNewFeedListener(new onAddNewFeedListener() {

			@Override
			public void onAddNewFeed() {
				addNewArticle();
			}
		});
		contentMyProfile.setOnUpdatePwdListener(new OnUpdatePwdListener() {

			@Override
			public void updatePwd() {
				HelloWorldActivity.this.updatePwd();
			}
		});
	}

	private void updatePwd() {
		Intent intent = new Intent(this, PasswordRecoverActivity.class);
		startActivityForResult(intent, 2);
		overridePendingTransition(R.anim.slide_in_bottom, 0);
	}

	private void addNewArticle() {
		Intent intent = new Intent(this, AddContentActivity.class);
		startActivityForResult(intent, 1);
		overridePendingTransition(R.anim.slide_in_bottom, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 1) {
				Article article = (Article) data.getSerializableExtra("article");
				contentFeedList.addNewFeed(article);
			} else if (requestCode == 2) {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}

	private void contentChange(int index) {
		getFragmentManager().beginTransaction().replace(R.id.container, frags[index]).commit();
	}

}
