package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.BufferData;
import com.ys.BytesUtil;
import com.ys.GetBytesUtils;
import com.ys.RobotEvent;
import com.ys.RobotEventArg;
import com.ys.data.bean.SaleListBean;
import com.ys.ui.R;
import com.ys.ui.common.constants.SlOutStatusEnum;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.serial.salemachine.SerialMachineActivity;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.ToastUtils;

import java.io.IOException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wangtao on 2016/4/18.
 */
public class OutGoodsActivity extends SerialMachineActivity {

    SendingThread mSendingThread;
    byte[] mBuffer;

    TextView transStatus;
     ContentLoadingProgressBar mPbLoading;


    private String channo = "";
    private String slNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_goods_main);
       // tvTimer =(TextView) findViewById(R.id.tv_timer);

        transStatus = (TextView) findViewById(R.id.transStatus);
       // mPbLoading = (ContentLoadingProgressBar)findViewById(R.id.pb_loading);
        //initTimer();

        Bundle datas = getIntent().getExtras();
        slNo = datas.getString("slNo");
        channo = datas.getString("channo");
        byte no = Integer.valueOf(channo, 16).byteValue();

        mBuffer = null;

        // 发送选货请求
        mBuffer = GetBytesUtils.goodsSelect(no);

        mSendingThread = new SendingThread();
        mSendingThread.start();
        //showProgress();
    }

    @Override
    protected void onDataReceived(final byte[] buff )  {

        runOnUiThread(new Runnable() {
            public void run() {
                // 是正确的返回结果
                ToastUtils.showShortMessage("dataReceived= " + BytesUtil.bytesToHexString(buff));

                switch (buff[1]) {

                    case 0x0E: // ------------------------------------------------------------------------------- 提货
                        if (buff[2] == 0xF2) buff[2] = 0x02;
                        if (buff[2] == 0xF3) buff[2] = 0x03;
                        switch (buff[3]) {
                            case 0x00:
                                //queue.add(new RobotEventArg(2, 2, Integer.valueOf(buff[2]).toString()));
                                break; // 提取中
                            case 0x55:
                                //queue.add(new RobotEventArg(2, 3, Integer.valueOf(buff[2]).toString()));
                                // 选货结束  发送出货命令
                                mBuffer = GetBytesUtils.goodsOuter();

                                mSendingThread = new SendingThread();
                                mSendingThread.start();
                                break; // 提取完毕
                            case (byte) 0xFF:
                                outGoodsFail();
                                //queue.add(new RobotEventArg(2, 4, Integer.valueOf(buff[2]).toString()));
                                break; // 提取失败
                            case (byte) 0xEE:
                                outGoodsFail();
                                //queue.add(new RobotEventArg(2, 4, "提取错误"));
                                break; // 提取错误
                        }
                        break;
                    case 0x08: // 商品出货
                        switch (buff[2]) {
                            case 0x00:
                                // 出货中    等待
                                // queue.add(new RobotEventArg(2, 5, "排放中"));
                                break;
                            case 0x55:
                                // 出货完成    结束出货流程
                                //queue.add(new RobotEventArg(2, 6, "排放完毕"));
                                // 出货成功  结束
                                outGoodsSuc();
//                                transStatus.setText("出货完成");
//                                ToastUtils.showShortMessage("交易成功");
                                break;
                            case (byte) 0xFF:
                                // 出货失败，结束出货流程
                                // 出货失败：
                                outGoodsFail();
                                // queue.add(new RobotEventArg(2, 7, "排放失败"));
                                break;
                            case (byte) 0xEE:
                                // 出货错误
                                outGoodsFail();
                                // queue.add(new RobotEventArg(2, 7, "排放错误"));
                                break;
                            default:
                                break;
                        }
                        break;


                }
            }


        });
    }


//    protected int minute;
//    protected int second;
//    protected Timer timer;
//    protected TimerTask timerTask;
//     TextView tvTimer;
//    private void initTimer() {
//        int timeout = PropertyUtils.getInstance().getTransTimeout();
//        minute = timeout/60;
//        second = timeout%60;
//
//        tvTimer.setText(getTime());
//
//        timerTask = new TimerTask() {
//
//            @Override
//            public void run() {
//                Message msg = new Message();
//                msg.what = 0;
//                handler.sendMessage(msg);
//            }
//        };
//
//        timer = new Timer();
//        timer.schedule(timerTask, 0, 1000);
//    }
//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//
//            String timer = getTime();
//            if (TextUtils.isEmpty(timer)) {
//                startActivity(new Intent(OutGoodsActivity.this, HomeActivity.class));
//                finish();
//                return;
//            }
//            tvTimer.setText(timer);
//        }
//    };

    private void outGoodsSuc() {
        if (mSendingThread != null) {
            mSendingThread.interrupt();

        }
        // 支付成功才会 走到出货
        DbManagerHelper.updateOutStatus(slNo, SlOutStatusEnum.FINISH);
        DbManagerHelper.reduceStore(channo);
        //hideProgress();
        transStatus.setText("出货完成");
        ToastUtils.showShortMessage("交易成功, 欢迎下次光临");

        //finish();
        // 打印凭条


        // 出货成功， 显示继续购买
        //startActivity(new Intent(OutGoodsActivity.this, HomeActivity.class));

    }

    private void outGoodsFail() {
        // 出货失败， 考虑退款
        DbManagerHelper.updateOutStatus(slNo, SlOutStatusEnum.FAIL);
        //hideProgress();
        transStatus.setText("出货失败 \n" +
                "如果您已支付成功， 将与24小时内退款到您的账户中，\n" +
                "      如有疑问， 请联系客服 400-060-0289");


        // 先判断下是否是微信或者支付宝支付， 如果是就退款
        SaleListBean saleListBean = DbManagerHelper.getSaleRecord(slNo);
        if (saleListBean.getSl_type().equals(SlTypeEnum.ALIPAY.getIndex())
                || saleListBean.getSl_type().equals(SlTypeEnum.WX.getIndex())) {
            // 退款
            this.refund(slNo);
        }

//        finish();
//        // 跳转
//        startActivity(new Intent(OutGoodsActivity.this, MainActivity.class));

    }
//    protected String getTime() {
//        if (minute == 0) {
//            if (second == 0) {
//                if (timer != null) {
//                    timer.cancel();
//                    timerTask.cancel();
//                }
//                return null;
//            }else {
//                second--;
//                if (second >= 10) {
//                    return "0"+minute + ":" + second;
//                }else {
//                    return "0"+minute + ":0" + second;
//                }
//            }
//        }else {
//            if (second == 0) {
//
//                second = 59;
//                minute--;
//                if (minute >= 10) {
//                    return minute + ":" + second;
//                } else {
//                    return "0" + minute + ":" + second;
//                }
//            } else {
//                second--;
//                if (second >= 10) {
//                    if (minute >= 10) {
//                        return minute + ":" + second;
//                    } else {
//                        return "0" + minute + ":" + second;
//                    }
//                } else {
//                    if (minute >= 10) {
//                        return minute + ":0" + second;
//                    } else {
//                        return "0" + minute + ":0" + second;
//                    }
//                }
//            }
//        }
//
//    }
    private void refund(String slNo) {
        RetrofitManager.builder().refund(slNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();

                    }
                })
                .subscribe(new Action1<CommonResponse<String>>() {
                    @Override
                    public void call(CommonResponse<String> response) {
                        Log.d("result", response.toString());
                        if (response.isSuccess()) {
                            Toast.makeText(OutGoodsActivity.this, "退款请求已成功发送", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OutGoodsActivity.this, "退款请求发送失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideProgress();
                        Toast.makeText(OutGoodsActivity.this, "退款失败，请联系工作人员", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private class SendingThread extends Thread {
        @Override
        public void run() {
                try {
                    if (mOutputStream != null) {
                        mOutputStream.write(mBuffer, 0, mBuffer.length);
                    } else {
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
        }
    }
    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSendingThread != null) {
            mSendingThread.interrupt();

        }
//        if(handler!=null){
//            handler.removeCallbacksAndMessages(null);
//        }
//
//        if(timerTask!=null){
//            timerTask.cancel();
//        }
//
//        if(timer!=null){
//            timer.cancel();
//        }
     }
}
