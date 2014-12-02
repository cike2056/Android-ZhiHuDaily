package com.qf.android_zhihudaily.activity;


import com.qf.android_zhihudaily.ndk.API;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	private API api ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		api= new API();
		Log.i("MainActivity",api.getLatestUrl());
		Log.i("MainActivity",api.getBefore());
		Log.i("MainActivity",api.getStartImgUrl());
		Log.i("MainActivity",api.getThemesUrl());
	}
}
