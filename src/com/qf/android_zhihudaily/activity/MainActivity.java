package com.qf.android_zhihudaily.activity;


import com.qf.android_zhihudaily.custom.CustomSlideAndList;
import com.qf.android_zhihudaily.custom.CustomTitle;
import com.qf.android_zhihudaily.ndk.API;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	private CustomTitle cTitle;
	private CustomSlideAndList cSlideList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

	}
	/**
	 * 初始化界面
	 */
	private void initView() {
		cTitle = (CustomTitle) findViewById(R.id.custom_title);
		cTitle.setTitle("首页");
		
		cSlideList = (CustomSlideAndList) findViewById(R.id.custom_slide_list);
		cSlideList.init(API.getLatestUrl());
	}
}
