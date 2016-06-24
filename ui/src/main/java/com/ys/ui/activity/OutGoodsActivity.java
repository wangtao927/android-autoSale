package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.bugly.crashreport.CrashReport;
import com.ys.BytesUtil;
import com.ys.GetBytesUtils;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.SaleListBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.common.constants.ChanStatusEnum;
import com.ys.ui.common.constants.SlOutStatusEnum;
import com.ys.ui.common.constants.SlPayStatusEnum;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.serial.print.activity.PrintHelper;
import com.ys.ui.serial.salemachine.SerialMachineActivity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

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
    TextView transFailDetail;

    Button btnJxBuy;

    private String channo = "";
    private String slNo = "";
    private int slType;

    ImageButton btnBackHome;
    boolean transFinish = false;
    boolean selectGoodsFlag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_goods_main);

        tvTimer = (TextView) findViewById(R.id.tv_timer);
        transStatus = (TextView) findViewById(R.id.transStatus);
        transFailDetail = (TextView) findViewById(R.id.transFailDetail);
        btnJxBuy = (Button)findViewById(R.id.btn_jx_buy);
        btnJxBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(OutGoodsActivity.this, ProductActivity.class));
            }
        });
        btnBackHome = (ImageButton) findViewById(R.id.btn_back_home);


        Bundle datas = getIntent().getExtras();
        slNo = datas.getString("slNo");
        channo = datas.getString("channo");
        slType = datas.getInt("slType");
        byte no = Integer.valueOf(channo, 16).byteValue();

        mBuffer = null;

        // 发送选货请求
        mBuffer = GetBytesUtils.goodsSelect(no);

        mSendingThread = new SendingThread();
        mSendingThread.start();

        initTimer(2,0);

    }

    @Override
    protected void onDataReceived(final byte[] buff) {

        runOnUiThread(new Runnable() {
            public void run() {
                // 是正确的返回结果
                Log.e("outGoodsActivity 105", App.mcNo + "dataReceived= " + BytesUtil.bytesToHexString(buff));
                switch (buff[1]) {

                    case 0x0E: // ------------------------------------------------------------------------------- 提货
                        if (buff[2] == 0xF2) buff[2] = 0x02;
                        if (buff[2] == 0xF3) buff[2] = 0x03;
                        switch (buff[3]) {
                            case 0x00:
                                selectGoodsFlag = true;
                                //queue.add(new RobotEventArg(2, 2, Integer.valueOf(buff[2]).toString()));
                                break; // 提取中
                            case 0x55:
                                selectGoodsFlag = true;
                                //queue.add(new RobotEventArg(2, 3, Integer.valueOf(buff[2]).toString()));
                                // 选货结束  发送出货命令
                                mBuffer = GetBytesUtils.goodsOuter();

                                mSendingThread = new SendingThread();
                                mSendingThread.start();
                                break; // 提取完毕
                            case (byte) 0xFF:
                                selectGoodsFaild();
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
                                selectGoodsFlag = true;
                                // 出货中    等待
                                // queue.add(new RobotEventArg(2, 5, "排放中"));
                                break;
                            case 0x55:
                                selectGoodsFlag = true;
                                // 出货完成    结束出货流程
                                //queue.add(new RobotEventArg(2, 6, "排放完毕"));
                                // 出货成功  结束
                                outGoodsSuc();
//                                transStatus.setText("出货完成");
//                                ToastUtils.showShortMessage("交易成功");
                                break;
                            case (byte) 0xFF:
                                selectGoodsFlag = true;
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



    private void selectGoodsFaild() {
        transFinish = true;
        updateTransFailDesc();
        try {
            // 复位
            reback();

            DbManagerHelper.updateMcStoreChannStatus(channo, ChanStatusEnum.ERROR);
            DbManagerHelper.updateOutStatus(slNo, SlOutStatusEnum.FAIL);

            refund(slNo);

            //ToastUtils.showShortMessage("退款请求已发送：订单号：" + slNo);
        } catch (Exception e) {
           // ToastUtils.showShortMessage("slNo=" + slNo + " 退款异常:" + e);
            Log.e("outGoodsActivity 193", App.mcNo+ "slNo=" + slNo + " 出货异常:" + e);
            CrashReport.postCatchedException(e);
        }
    }

    private void updateTransFailDesc() {
        reInitTimer(5, 0);
        //  锁定货道
        transStatus.setText("出货失败!");
        transStatus.setTextColor(getResources().getColor(R.color.red));
        transFailDetail.setVisibility(View.VISIBLE);
        btnJxBuy.setVisibility(View.VISIBLE);

        if (slType == SlTypeEnum.CODE.getIndex()) {
            transFailDetail.setText("请5分钟后重新操作，如有疑问， 请联系客服 400-060-0289。");
        }
    }
    private void transTimeout() {
        try {
            Log.d("transTimeout", "transTimeout slNo="+slNo +"slType=" + slType);
            // 复位
            reback();

            if (!transFinish) {
                DbManagerHelper.updateOutStatusFail(slNo, SlOutStatusEnum.FAIL, SlOutStatusEnum.INIT);

                refund(slNo);
            }
            //ToastUtils.showShortMessage("退款请求已发送：订单号：" + slNo);
        } catch (Exception e) {
            Log.e("outGoodsActivity 222", "slNo=" + slNo + " 退款异常:" + e);
           CrashReport.postCatchedException(e);
        }
    }

    private void outGoodsFail() {
        // 出货失败， 考虑退款
         transFinish = true;
        updateTransFailDesc();
        try {
            reback();
            App.getDaoSession(App.getContext()).getMcGoodsBeanDao().updateChanStatusByChanno(
                    channo, Long.valueOf(ChanStatusEnum.ERROR.getIndex()));

            DbManagerHelper.updateOutStatus(slNo, SlOutStatusEnum.FAIL);

            refund(slNo);

        } catch (Exception e) {
            //ToastUtils.showShortMessage("出货失败");
            Log.e("outGoodsActivity 243", App.mcNo +"outGoodsFail" + e);
            CrashReport.postCatchedException(e);

        }

    }

    private void outGoodsSuc() {
        try {
            reInitTimer(0, 30);
            if (mSendingThread != null) {
                mSendingThread.interrupt();

            }
            transFinish = true;

            reback();
            transStatus.setText("交易完成！");
            btnJxBuy.setVisibility(View.VISIBLE);
            transFailDetail.setVisibility(View.VISIBLE);
            transFailDetail.setText("请从取货口取走您的货物，欢迎继续购买!");
            // 支付成功才会 走到出货
            DbManagerHelper.updateOutStatus(slNo, SlOutStatusEnum.FINISH);
            DbManagerHelper.reduceStore(channo);
            // 打印凭条
            printPayNote();



        } catch (Exception e) {
            Log.e("outGoodsActivitySUC 273", App.mcNo  + e);
            CrashReport.postCatchedException(e);
        }

    }

    private void printPayNote() {
        Long vipPrice = 0L;

        SaleListBean bean = DbManagerHelper.getSaleRecord(slNo);
        if (bean.getSl_vip_price() != null) {
            vipPrice = bean.getSl_vip_price();
        }
        GoodsBean goodsBean = DbManagerHelper.getGoodsInfo(bean.getSl_gd_no());
        PrintHelper.getInstance().gdPrint(slNo.substring(App.mcNo.length()), App.mcNo, goodsBean.getGd_name(),
                goodsBean.getGd_spec(), getPrice(bean.getSl_pre_price()),
                getPrice(vipPrice), getPrice(bean.getSl_amt()), SlTypeEnum.findByIndex(slType).getDesc());
    }

    protected String getPrice(Long price) {

        if (price == null) {
            return "0";
        }
        return new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString();
    }



    protected String getTime() {
        if (minute == 0) {
            if (second == 0) {
                if (timer != null) {
                    timer.cancel();
                    timerTask.cancel();
                }
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


    private void refund(final String slNo) {
        SaleListBean saleListBean = DbManagerHelper.getSaleRecord(slNo);

        if (saleListBean.getSl_type().equals(String.valueOf(SlTypeEnum.ALIPAY.getIndex()))
                || saleListBean.getSl_type().equals(String.valueOf(SlTypeEnum.WX.getIndex()))
                || saleListBean.getSl_type().equals(String.valueOf(SlTypeEnum.CODE.getIndex()))) {
            // 退款

            RetrofitManager.builder().refund(slNo)
                    .subscribeOn(Schedulers.io())
                    //.observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            //showProgress();

                        }
                    })
                    .subscribe(new Action1<CommonResponse<String>>() {
                        @Override
                        public void call(CommonResponse<String> response) {
                            Log.d("result", response.toString());
                            //ToastUtils.showShortMessage("退款返回：" + response);

                            if (response.isSuccess()) {

                                //
                                DbManagerHelper.updatePayStatus(slNo, SlPayStatusEnum.REFUNDED);

                                //Toast.makeText(OutGoodsActivity.this, "退款请求已成功发送", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(OutGoodsActivity.this, "退款请求发送失败", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.e("out goods refund", "退款失败：slNo=" + slNo + throwable.getMessage());
                            // hideProgress();
                           // Toast.makeText(OutGoodsActivity.this, "退款失败，请联系工作人员", Toast.LENGTH_SHORT).show();
                            CrashReport.postCatchedException(throwable);

                        }
                    });
        }

    }

    private void reback() {


        // 发送选货请求
        mBuffer = GetBytesUtils.reback();

        mSendingThread = new SendingThread();
        mSendingThread.start();
    }
    private class SendingThread extends Thread {
        @Override
        public void run() {
            try {
                Log.d("sending thread", "buffer:" + BytesUtil.bytesToHexString(mBuffer));
                if (mOutputStream != null) {
                    mOutputStream.write(mBuffer, 0, mBuffer.length);
                } else {
                    return;
                }
            } catch (IOException e) {
                Log.e("SendingThread error", e.getMessage());
                 CrashReport.postCatchedException(e);
                // 重试发送
                try {
                    if (mOutputStream != null) {
                        mOutputStream.write(mBuffer, 0, mBuffer.length);
                    } else {
                        return;
                    }
                } catch (IOException e1) {
                    Log.e("SendingThread error2", e.getMessage());
                    CrashReport.postCatchedException(e1);

                }
            }
        }
    }


    int minute;
    int second;
    Timer timer;
    TimerTask timerTask;
    TextView tvTimer;

    private void initTimer(int mins, int secs) {

        minute = mins;

        second = secs;

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

                if (!TextUtils.isEmpty(slNo)) {
                    transTimeout();
                }
                startActivity(new Intent(OutGoodsActivity.this, HomeActivity.class));
                finish();
                return;
            }
            if ("01:40".equals(timer) || "01:45".equals(timer) || "01:50".equals(timer)) {
                Log.e("retry selectGoods:", timer + "retry start selectGoods" + mBuffer);
                if (!selectGoodsFlag) {
                    mSendingThread = new SendingThread();
                    mSendingThread.start();
                }

            }
            tvTimer.setText(timer);
        }
    };

    private void reInitTimer(int minute, int second) {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        initTimer(minute, second);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSendingThread != null) {
            mSendingThread.interrupt();
            mSendingThread = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
