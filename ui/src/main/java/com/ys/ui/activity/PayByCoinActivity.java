package com.ys.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.bugly.crashreport.CrashReport;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.SaleListBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.PayTimerActivity;
import com.ys.ui.common.constants.SlPayStatusEnum;
import com.ys.ui.common.constants.SlSendStatusEnum;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.request.SaleListVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.UserResult;
import com.ys.ui.utils.ImageUtils;
import com.ys.ui.utils.OrderUtils;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.ToastUtils;
import com.ys.ui.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by river on 2016/4/19.
 */
public class PayByCoinActivity extends PayTimerActivity implements View.OnClickListener {

    @Bind(R.id.tv_gd_name)
    TextView tvGdName;

    @Bind(R.id.tv_price)
    TextView tvPrice;

    @Bind(R.id.tv_vip_price)
    TextView tvVipPrice;

    @Bind(R.id.iv_gd_detail_image)
    ImageView gdDetailImage;
    @Bind(R.id.tv_sale_price)
    TextView tvSalePrice;

    @Bind(R.id.tv_pay_type)
    TextView tvPayType;

    @Bind(R.id.tv_pay_type0)
    TextView tvPayType0;
    @Bind(R.id.tv_pay_second)
    TextView tvPaySecond;

    @Bind(R.id.btn_dir_buy)
    Button btnDirPay;
    @Bind(R.id.btn_vip_buy)
    Button btnVipPay;


    private GoodsBean goodsBean;
    private McGoodsBean mcGoodsBean;

    private SlTypeEnum slType;

    @Bind(R.id.lay_pay)
    LinearLayout layPay;


    @Bind(R.id.tv_reselect_paytype)
    TextView tvReselectPaytype;


    @Bind(R.id.lay_select_payway)
    LinearLayout laySelectPay;

    String gdNameValue = "商品名：%s";
    String gdPrice = "价    格：%s 元";
    String gdVipPrice = "会员价：%s 元";

    private Dialog mDialog;
    private TextView tvPwd;
    private ImageButton ibGetV;

    private String slNo;
    private String acountNo;



    // 判断是否可以返回首页
    private boolean backHomeFlag = true;

    protected void backHome() {

        findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (backHomeFlag) {

                    finish();
                    startActivity(new Intent(PayByCoinActivity.this, ProductActivity.class));
                }

            }
        });


    }

    protected void getIntent(Bundle savedInstanceState) {
        //调用接口获取地址
        Bundle datas = getIntent().getExtras();

        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goods");
        mcGoodsBean = (McGoodsBean) getIntent().getSerializableExtra("mcGoods");

        int type = datas.getInt("type");
        slType = SlTypeEnum.findByIndex(type);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_pay_cash;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        getIntent(savedInstanceState);
        //调用接口获取地址
        init();
        tvPayType.setText(slType.getDesc());
        tvPayType0.setText(slType.getDesc());
        if (slType == SlTypeEnum.CARD) {
            tvPaySecond.setText("请投币");
        }
        btnDirPay.setOnClickListener(this);
        btnVipPay.setOnClickListener(this);
    }

    private void init() {

        Glide.with(App.getContext())
                .load(PropertyUtils.getInstance().getFastDfsUrl() + ImageUtils.getImageUrl(goodsBean.getGd_img_s()))
                .into(gdDetailImage);
        tvSalePrice.setText(String.format(gdPrice, getPrice(mcGoodsBean.getMg_disc_price())));
        tvGdName.setText(String.format(gdNameValue, goodsBean.getGd_short_name()));
        tvPrice.setText(String.format(gdPrice, getPrice(mcGoodsBean.getMg_disc_price())));
        tvVipPrice.setText(String.format(gdVipPrice, getPrice(mcGoodsBean.getMg_vip_price())));
    }


    // 普通支付  取折扣价   会员支付取 会员价  vip 0 不是vip  1 是vip

    private void createOrder(String type, final int vip) {

        tvReselectPaytype.setVisibility(View.VISIBLE);
        slNo = OrderUtils.getOrderNo();
        final SaleListVo saleListVo = new SaleListVo();
        saleListVo.setMcNo(App.mcNo);
        saleListVo.setSlType(type);
        saleListVo.setSlGdName(goodsBean.getGd_name());
        saleListVo.setSlGdNo(goodsBean.getGd_no());
        saleListVo.setSlChann(mcGoodsBean.getMg_channo());
        final long amount = vip == 0 ? mcGoodsBean.getMg_disc_price() : mcGoodsBean.getMg_vip_price();
        saleListVo.setSlAmt(amount);
        saleListVo.setSlAccNo(acountNo);
        createSaleList(saleListVo.getSlAmt(), vip, slNo);

        // 重新计时
        minute = 2;
        second = 0;

        backHomeFlag = false;
        //mQCodeImageView.setImageResource(R.mipmap.bankcard_flag);
        //waitPay(saleListVo.getSlAmt());




    }


    private void createSaleList(long amount, int vip, String slNo) {
        SaleListBean saleListBean = new SaleListBean();
        saleListBean.setSl_pay_status(String.valueOf(SlPayStatusEnum.INIT.getIndex()));
        saleListBean.setMc_no(App.mcNo);
        saleListBean.setSl_chann(mcGoodsBean.getMg_channo());
        saleListBean.setSl_amt(amount);
        saleListBean.setSl_isvip(String.valueOf(vip));

        saleListBean.setSl_disc_price(mcGoodsBean.getMg_disc_price());
        saleListBean.setSl_vip_price(mcGoodsBean.getMg_vip_price());
        saleListBean.setSl_pre_price(mcGoodsBean.getMg_pre_price());
        saleListBean.setSl_score(mcGoodsBean.getMg_score_price());
        saleListBean.setSl_gd_no(mcGoodsBean.getGd_no());
        saleListBean.setSl_send_status(Long.valueOf(SlSendStatusEnum.INIT.getIndex()));
        saleListBean.setSl_gd_name(goodsBean.getGd_name());
        saleListBean.setSl_no(slNo);
        saleListBean.setSl_num(1L);
        saleListBean.setSl_type(String.valueOf(slType.getIndex()));
        saleListBean.setSl_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        saleListBean.setSl_acc_no(acountNo);
        App.getDaoSession(App.getContext()).getSaleListBeanDao().insertOrReplace(saleListBean);
    }

    private long startTime = 0;
    private int timeout = PropertyUtils.getInstance().getTransTimeout();
    TimerTask pay_task;
    java.util.Timer pay_timer;
    boolean finish_flag = false;


    long slAmount;

    private void startOutGoods(String slNo) {
        finish();

        Intent intent = new Intent(PayByCoinActivity.this, OutGoodsActivity.class);

        intent.putExtra("slType", slType.getIndex());
        intent.putExtra("slNo", slNo);
        intent.putExtra("channo", mcGoodsBean.getMg_channo());
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if (Utils.isFastClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_dir_buy:
               // backHomeFlag = false;
                createOrder(String.valueOf(slType.getIndex()), 0);
                layPay.setVisibility(View.VISIBLE);
                laySelectPay.setVisibility(View.GONE);

                break;
            case R.id.btn_vip_buy:

                // 弹出登录框
                initview();
                break;

            case R.id.btn_login:

                if (etUserNo != null) {

                    if (regFlag) {
                        // 注册
                        if (TextUtils.isEmpty(etUserNo.getText()) || TextUtils.isEmpty(etPwd.getText())) {
                            ToastUtils.showShortMessage("手机号和验证码不能为空");
                        } else {
                            userReg(etUserNo.getText().toString(), etPwd.getText().toString());

                        }
                    } else {
                        if (TextUtils.isEmpty(etUserNo.getText()) || TextUtils.isEmpty(etPwd.getText())) {
                            ToastUtils.showShortMessage("用户名密码不能为空");
                        } else {
                            userLogin(etUserNo.getText().toString(), etPwd.getText().toString());
                        }
                    }
                    //backHomeFlag = false;
                 }
                break;
            case R.id.btn_cancel:
                mDialog.cancel();
                break;
            case R.id.ib_login:
                regFlag = false;
                btnLogin.setText("登录");
                ivLogin.setBackgroundResource(R.mipmap.login);
                ivReg.setBackgroundResource(R.mipmap.reg_1);
                tvPwd.setText("密 码");
                ibGetV.setVisibility(View.GONE);
                break;
            case R.id.ib_reg:
                regFlag = true;
                btnLogin.setText("注册");

                ivLogin.setBackgroundResource(R.mipmap.login_1);
                ivReg.setBackgroundResource(R.mipmap.reg);
                tvPwd.setText("验证码");
                ibGetV.setVisibility(View.VISIBLE);
                break;
            case R.id.ib_getv:
                regFlag = true;
                ivLogin.setBackgroundResource(R.mipmap.login_1);
                ivReg.setBackgroundResource(R.mipmap.reg);
                tvPwd.setText("验证码");

                // 发送验证码
                if (etUserNo != null && !TextUtils.isEmpty(etUserNo.getText())) {

                    getValideCode(etUserNo.getText().toString());

                    ibGetV.setBackgroundResource(R.mipmap.getv1);
                    ibGetV.setClickable(false);
                } else {
                   ToastUtils.showShortMessage("请填写手机号");
                }
                break;
            default:
                break;

        }
    }

    private View view;
    ImageView ivLogin;
    ImageView ivReg;
    EditText etUserNo;
    EditText etPwd;
    Button btnLogin;
    private boolean regFlag = false;// 默认登录

    public void initview() {
        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.dialog_login, null);

        etUserNo = (EditText) view.findViewById(R.id.et_phone);
        etPwd = (EditText) view.findViewById(R.id.et_pwd);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        btnLogin.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        ivLogin = (ImageView) view.findViewById(R.id.ib_login);
        ivReg = (ImageView) view.findViewById(R.id.ib_reg);
        tvPwd = (TextView) view.findViewById(R.id.tv_pwd);
        ibGetV = (ImageButton) view.findViewById(R.id.ib_getv);

        ivLogin.setOnClickListener(this);
        ivReg.setOnClickListener(this);
        ibGetV.setOnClickListener(this);

        mDialog = new Dialog(PayByCoinActivity.this, R.style.dialog);
        mDialog.setContentView(view);
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6

        dialogWindow.setAttributes(lp);
        mDialog.show();
    }

    private void userLogin(final String userNo, final String userPwd) {
        //
                RetrofitManager.builder().userLogin(userNo, userPwd)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnSubscribe(new Action0() {
//                            @Override
//                            public void call() {
//                                //showProgress();
//                            }
//                        })
                        .subscribe(new Action1<CommonResponse<UserResult>>() {
                            @Override
                            public void call(CommonResponse<UserResult> response) {
                                //hideProgress();

                                Log.d("userLogin response", response.toString());
                                if (response.getCode() == 0) {
                                    acountNo = userNo;

                                    mDialog.cancel();
                                    createOrder(String.valueOf(slType.getIndex()), 1);
                                    layPay.setVisibility(View.VISIBLE);
                                    laySelectPay.setVisibility(View.GONE);
                                    // 显示会员价格
                                    tvSalePrice.setText(String.format(gdVipPrice, getPrice(mcGoodsBean.getMg_vip_price())));
                                } else {
                                    Log.d("mcNo="+ App.mcNo , "用户名或密码错误" + userNo + "--"+userPwd);
                                    ToastUtils.showShortMessage("用户名或密码错误");
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ToastUtils.showShortMessage("登录失败");
                                CrashReport.postCatchedException(throwable);
                            }
                        });



    }

    private void getValideCode(String phoneNo) {

        RetrofitManager.builder().getVerifyCode(phoneNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //showProgress();
                    }
                })
                .subscribe(new Action1<CommonResponse<String>>() {
                    @Override
                    public void call(CommonResponse<String> response) {
                        //hideProgress();
                        Log.d("getVerifyCode response", response.toString());

                        if (response.getCode() == 0) {

                            ToastUtils.showShortMessage("验证码已发送");

                        } else {
                            ToastUtils.showShortMessage(response.getMsg());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShortMessage("发送验证码失败");
                        CrashReport.postCatchedException(throwable);

                    }
                });

    }
    private void userReg(final String phoneNo, String valideCode) {
        RetrofitManager.builder().userReg(phoneNo, valideCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        //showProgress();
//                    }
//                })
                .subscribe(new Action1<CommonResponse<String>>() {
                    @Override
                    public void call(CommonResponse<String> response) {
                        Log.d("userReg response", response.toString());

                        if (response.getCode() == 0) {
                            mDialog.cancel();
                            acountNo = phoneNo;
                            //ToastUtils.showShortMessage("注册成功，默认密码是:123456");
                            // 生成订单
                            createOrder(String.valueOf(slType.getIndex()), 1);
                            layPay.setVisibility(View.VISIBLE);
                            laySelectPay.setVisibility(View.GONE);
                            // 显示会员价格
                            tvSalePrice.setText(String.format(gdVipPrice, getPrice(mcGoodsBean.getMg_vip_price())));

                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShortMessage("注册失败");
                        CrashReport.postCatchedException(throwable);

                    }
                });

     }


    private void payFaild() {

        if (!finish_flag) {
            Log.d("payActivity payFaild", "finish payed");

            finish();
            startActivity(new Intent(PayByCoinActivity.this, PayFailActivity.class));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish_flag = true;
        if (pay_timer != null) {
            pay_timer.cancel();
        }
        if (pay_task != null) {
            pay_task.cancel();
        }
        ButterKnife.unbind(this);


    }

    @Override
    protected void refund() {

        if (TextUtils.isEmpty(slNo)) {
            Log.d("payActivity refund", "slNo is null");
            CrashReport.postCatchedException(new NullPointerException("PayActivity refund : slNo is null"));
            return;
        }
        SaleListBean saleListBean = DbManagerHelper.getSaleRecord(slNo);
         // 退款
    }

}
