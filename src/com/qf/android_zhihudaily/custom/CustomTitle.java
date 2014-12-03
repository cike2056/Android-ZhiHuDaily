package com.qf.android_zhihudaily.custom;

import com.qf.android_zhihudaily.activity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * �Զ������
 * @author Lusifer
 *
 * 2014��12��1������2:32:33
 */
public class CustomTitle extends FrameLayout {
	private TextView txTitle;

	public CustomTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// �󶨲���
		LayoutInflater.from(context).inflate(R.layout.custom_title, this);
		
		initView();
	}

	private void initView() {
		txTitle = (TextView) findViewById(R.id.tx_title);
	}
	
	public void setTitle(String title) {
		txTitle.setText(title);
	}

}
