package com.ys.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.ys.ui.R;
import com.ys.ui.view.LuckyPanView;


public class ZhuanPanActivity extends Activity
{
	private LuckyPanView mLuckyPanView;
	private ImageView mStartBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choujiang);

		mLuckyPanView = (LuckyPanView) findViewById(R.id.id_luckypan);
		mStartBtn = (ImageView) findViewById(R.id.id_start_btn);

		mStartBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!mLuckyPanView.isStart())
				{
					mStartBtn.setImageResource(R.mipmap.stop);
					mLuckyPanView.luckyStart(1);
				} else
				{
					if (!mLuckyPanView.isShouldEnd())

					{
						mStartBtn.setImageResource(R.mipmap.start);
						mLuckyPanView.luckyEnd();
					}
				}
			}
		});
	}

}
