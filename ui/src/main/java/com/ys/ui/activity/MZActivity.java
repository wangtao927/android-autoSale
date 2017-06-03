package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.ui.R;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.utils.PropertyUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangtao on 2016/6/9.
 */
public class MZActivity extends BaseTimerActivity  implements View.OnClickListener {

    @Bind(R.id.tv_mz)
    TextView tvMz;

    @Bind(R.id.logo)
    ImageView logo;

    long[] mHits = new long[5];

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mz;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        logo.setOnClickListener(this);
        tvMz.setText(String.format(tvMz.getText().toString(), PropertyUtils.getInstance().getKfPhone()));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.logo:

                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于3000，即双击
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - 3000)) {

                    Intent detailIntent = new Intent(this, AdminLoginActivity.class);
                    finish();
                    startActivity(detailIntent);
                }

                break;

            default:

                break;
        }

    }
    protected void backHome() {
        if (findViewById(R.id.btn_back_home) != null) {
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(MZActivity.this, HomeActivity.class));
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
