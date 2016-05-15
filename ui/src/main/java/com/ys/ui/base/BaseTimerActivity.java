package com.ys.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ys.ui.R;
import com.ys.ui.activity.HomeActivity;
import com.ys.ui.utils.PropertyUtils;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BaseTimerActivity extends AppCompatActivity {

    @Bind(R.id.tv_timer)
    TextView tvTimer;

    Timer timer;
    TimerTask timerTask;
    protected int minute;
    protected int second;

    private Window window;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        window.setAttributes(params);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        getIntent(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        create(savedInstanceState);
        initTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        resetTimeOut();
    }

    public void close(View v){
        finish();
    }

    protected void resetTimeOut() {
        int timeout = PropertyUtils.getInstance().getTransTimeout();
        minute = timeout / 60;
        second = timeout % 60;

    }

    protected String getPrice(Long price) {

        if (price == null) {
            return "0";
        }
        return new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString();
    }

    private void initTimer() {
        int timeout = PropertyUtils.getInstance().getTransTimeout();
        minute = timeout / 60;
        second = timeout % 60;

        tvTimer.setText(getTime());

        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            String timer = getTime();
            if (TextUtils.isEmpty(timer)) {
                finish();
                startActivity(new Intent(BaseTimerActivity.this, HomeActivity.class));
                return;
            }
            tvTimer.setText(timer);
        }
    };
    String getTime() {
        if (minute == 0) {
            if (second == 0) {
                return "";
            } else {
                second--;
                if (second >= 10) {
                    return "0" + minute + ":" + second;
                } else {
                    return "0" + minute + ":0" + second;
                }
            }
        } else {
            if (second == 0) {

                second = 59;
                minute--;
                if (minute >= 10) {
                    return minute + ":" + second;
                } else {
                    return "0" + minute + ":" + second;
                }
            } else {
                second--;
                if (second >= 10) {
                    if (minute >= 10) {
                        return minute + ":" + second;
                    } else {
                        return "0" + minute + ":" + second;
                    }
                } else {
                    if (minute >= 10) {
                        return minute + ":0" + second;
                    } else {
                        return "0" + minute + ":0" + second;
                    }
                }
            }
        }

    }

    protected void backHome(View v) {
        finish();
        startActivity(new Intent(this, HomeActivity.class));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

        if (timerTask != null) {
            timerTask.cancel();
        }

        if (timer != null) {
            timer.cancel();
        }
    }

    protected void getIntent(Bundle savedInstanceState){};

    protected abstract int getLayoutId();

    protected abstract void create(Bundle savedInstanceState);

    protected Context getCtx() {
        return App.getContext();
    }

    protected String getMcNo() {
        return App.mcNo;
    }

}