package com.qf.android_zhihudaily.activity;


import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response.Listener;
import com.qf.android_zhihudaily.cache.BitmapCache;
import com.qf.android_zhihudaily.ndk.API;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
/**
 * 欢迎界面:动画.
 * @author uaige
 *
 */
public class WelcomeActivity extends Activity implements AnimationListener{
	private NetworkImageView  imgStart;
	private RequestQueue mQueue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initView();
		initData();
	}
/**
 * 初始化界面
 */
	private void initData() {
		// TODO Auto-generated method stub
		imgStart=(NetworkImageView) findViewById(R.id.img_start);
	}
/**
 * 初始化数据
 */
	private void initView() {
		// TODO Auto-generated method stub
		mQueue = Volley.newRequestQueue(getApplicationContext());//使用this行不行?
		mQueue.add(new JsonObjectRequest(Method.GET, API.getStartImgUrl(), null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					String imgUrl = response.getString("img");
					imgStart.setImageUrl(imgUrl, new ImageLoader(mQueue, new BitmapCache()));
					
					// 图片动画
					Animation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // 将图片放大1.2倍，从中心开始缩放
					animation.setDuration(2000); // 动画持续时间
					animation.setFillAfter(true); // 动画结束后停留在结束的位置
					animation.setAnimationListener(WelcomeActivity.this); // 添加动画监听
					imgStart.startAnimation(animation); // 启动动画
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, null));
	}
	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		// 动画结束时跳转至首页
				startActivity(new Intent(this, MainActivity.class));
	}
	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
}
