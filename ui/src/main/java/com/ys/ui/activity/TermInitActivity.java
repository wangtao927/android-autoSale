package com.ys.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ys.BytesUtil;
import com.ys.SerialPortUtil;
import com.ys.bean.ComBean;
import com.ys.data.bean.McStatusBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.response.TermInitResponse;
import com.ys.ui.sample.MainMenu;
import com.ys.ui.utils.StringUtils;
import com.ys.ui.utils.ToastUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import butterknife.Bind;

/**
 *初始化终端参数  初始化终端号
 */
public class TermInitActivity extends Activity {
    private String TAG = "TermInitActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_main);
        setupUI();//设置UI
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();

    }
    Button btn_save;
    EditText et_termno;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        // 到后台去检验 终端号是否合法
        RetrofitManager manager = RetrofitManager.builder();
        TermInitResponse response = manager.mcReset(et_termno.getText().toString(), "12345678");
            ToastUtils.showShortMessage(response.toString(), App.getContext());
            // 存储到  sqllite 中
        App.getDaoSession(App.getContext()).getMcStatusBeanDao().insertOrReplace(getInitBean(et_termno.getText().toString()));
        McStatusBean bean = App.getDaoSession(App.getContext()).getMcStatusBeanDao().load(1);
        ToastUtils.showError(et_termno.getText().toString() + bean.getMr_id(), App.getContext());

//         LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        double latitude = location.getLatitude();     //经度
//        double longitude = location.getLongitude(); //纬度
//        double altitude =  location.getAltitude();     //海拔
        }
    };
    private McStatusBean getInitBean(String termno) {
        McStatusBean bean = new McStatusBean();
        bean.setMc_no(termno);
        bean.setAddtime(new Date());
        return bean;
    }

    private void setupUI(){
        et_termno = (EditText)findViewById(R.id.et_termno);
        btn_save = (Button)findViewById(R.id.btn_save);

        btn_save.setOnClickListener(mOnClickListener);

    }
}
