package com.ys.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ys.BytesUtil;
import com.ys.SerialPortUtil;
import com.ys.ui.R;
import com.ys.ui.base.BaseActivity;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;

/**
 * Created by wangtao on 2016/3/31.
 */
public class PrintActivity extends BaseActivity implements View.OnClickListener  {

    @Bind(R.id.et_input)
    EditText etInput;

    @Bind(R.id.et_out)
    EditText etOut;

    @Bind(R.id.btn_send)
    Button btnSend;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_product;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        btnSend.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_send:
                print();
                break;
        }
    }

    private void print() {
        byte[] bytes = BytesUtil.hexStringToBytes(etInput.getText().toString());


        SerialPortUtil util = new SerialPortUtil(new SerialPortUtil.OnDataReceiveListener() {
            @Override
            public void onDataReceive(final byte[] buffer, int size) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            etOut.append(new String(buffer, "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        util.sendBuffer(BytesUtil.hexStringToBytes("1B40"));
        util.sendBuffer(bytes);
    }
}
