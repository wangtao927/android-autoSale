package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ys.ui.R;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.utils.PropertyUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangtao on 2016/6/9.
 */
public class HelpActivity extends BaseTimerActivity {


   @Bind(R.id.tv_help)
   TextView tvHelp;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        tvHelp.setText(String.format(tvHelp.getText().toString(), PropertyUtils.getInstance().getLinkPhone(),
                PropertyUtils.getInstance().getKfPhone()));
    }

    protected void backHome() {
        if (findViewById(R.id.btn_back_home) != null) {
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(HelpActivity.this, HomeActivity.class));
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
