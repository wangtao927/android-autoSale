package com.ys.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.bugly.crashreport.CrashReport;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.CreateDrawResult;
import com.ys.ui.utils.ToastUtils;
import com.ys.ui.view.LuckyPanView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class ZhuanPanActivity extends BaseTimerActivity implements View.OnClickListener {

	@Bind(R.id.id_luckypan)
	LuckyPanView mLuckyPanView;


	@Bind(R.id.id_start_btn)
	ImageView mStartBtn;
	Handler h = new Handler();

	@Override
	protected int getLayoutId() {
		return R.layout.activity_choujiang;
	}

	@Override
	protected void create(Bundle savedInstanceState) {
		mStartBtn.setOnClickListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);

		h.removeCallbacksAndMessages(null);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.id_start_btn:
				// 弹出框， 输入订单号，用户名 密码
                initview();
				break;
			case R.id.btn_confirm:

				String userNo  = etUserNo.getText().toString();
				String slNo = etSlNo.getText().toString();
//				String pwd = etPwd.getText().toString();

				if (TextUtils.isEmpty(userNo) ) {
					ToastUtils.showShortMessage("用户名不能为空");
					return;
				}
				if (TextUtils.isEmpty(slNo)) {
					ToastUtils.showShortMessage("订单编号不能为空");

					return;
				}
				minute = 2;
				second = 0;
				createDraw(slNo, userNo);
				break;

			case R.id.btn_cancel:
				mDialog.cancel();
				break;
			default:

				break;
		}
	}

	@Override
	protected void backHome() {
		if(findViewById(R.id.btn_back_home)!=null){
			findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
					startActivity(new Intent(ZhuanPanActivity.this, VipActivity.class));
				}
			});
		}
	}


	private View view;

	EditText etSlNo;

	EditText etUserNo;

	Button btnConfirm;

	Button btnCancel;
	private Dialog mDialog;
	public void initview() {
		LayoutInflater inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.cj_input, null);

		etSlNo = (EditText) view.findViewById(R.id.et_slNo);
		etUserNo = (EditText) view.findViewById(R.id.et_userno);
		btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		btnCancel = (Button) view.findViewById(R.id.btn_cancel);


		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);


		mDialog = new Dialog(ZhuanPanActivity.this, R.style.dialog);
		mDialog.setContentView(view);
		Window dialogWindow = mDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6

		dialogWindow.setAttributes(lp);
		mDialog.show();
	}

	private void start(final int index) {

		if (!mLuckyPanView.isStart()) {

					mLuckyPanView.luckyStart(index);

					h.postDelayed(new Runnable() {
						@Override
						public void run() {
							if (!mLuckyPanView.isShouldEnd()) {
								mLuckyPanView.luckyEnd();

								if (index == 0) {
									ToastUtils.showShortMessage("很遗憾，再抽一次呗");

								} else {
									ToastUtils.showShortMessage("恭喜您，中奖了");

								}
							}

						}
					}, 4000);

				}
	}


	private void createDraw(String slNo, String userNo) {
		RetrofitManager.builder().createDraw(App.mcNo + slNo, userNo)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						//showProgress();
					}
				})
				.subscribe(new Action1<CommonResponse<CreateDrawResult>>() {
					@Override
					public void call(CommonResponse<CreateDrawResult> response) {
						//hideProgress();
						if (response.isSuccess()) {
							//
							mDialog.cancel();

							start(getIndex(response.getExt_data().getDwLevel()));

						} else {
							mDialog.cancel();
							// 没中奖
							//start(0);
							Log.d("createDraw fail:",  response.toString());

							ToastUtils.showError(response.getMsg(), ZhuanPanActivity.this);
						}

					}
				}, new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						mDialog.cancel();
						ToastUtils.showShortMessage("网络不好，请重试");
						Log.d("createDraw:", "网络不好:" + throwable.getMessage());
						CrashReport.postCatchedException(throwable);

					}
				});

	}

	private int getIndex(int level) {
		if (level == 1) {

		} else if (level == 2) {
			//
			 level = 3;
		} else if (level == 3) {
            level = 5;
		}else if (level == 4) {
            level = 3;
		}else if (level == 5) {
             level = 6;
		} else {

           level = 0;
		}
		return level;
	}

}
