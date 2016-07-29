package com.ys.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.zxing.WriterException;
import com.tencent.bugly.crashreport.CrashReport;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.SaleListBean;
import com.ys.ui.R;
import com.ys.ui.activity.OutGoodsActivity;
import com.ys.ui.activity.ProductDetailActivity;
import com.ys.ui.base.App;
import com.ys.ui.common.constants.ChanStatusEnum;
import com.ys.ui.common.constants.SlPayStatusEnum;
import com.ys.ui.common.constants.SlSendStatusEnum;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.request.SaleListVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.CreateOrderResult;
import com.ys.ui.common.response.UserResult;
import com.ys.ui.utils.ImageUtils;
import com.ys.ui.utils.NumberUtils;
import com.ys.ui.utils.OrderUtils;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by river on 2016/4/22.
 */
public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.Holder> {

    private List<McGoodsBean> data;
    private Context context;

    public ScoreListAdapter(Context context, List<McGoodsBean> lists) {
        this.context = context;
        data = lists;
    }


    public void setData(List<McGoodsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_goods_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final McGoodsBean mcGoodsBean = data.get(position);
        GoodsBean goodsBean = DbManagerHelper.getGoodsInfo(mcGoodsBean.getGd_no());
        holder.productName.setTag(mcGoodsBean);
         if (goodsBean == null ) {
            // 上报错误信息
            CrashReport.postCatchedException(new NullPointerException("gdNo:" + mcGoodsBean.getGd_no()
                    + "channo:" + mcGoodsBean.getMg_channo() + " goods is null"));

        } else {
            if (TextUtils.isEmpty(goodsBean.getGd_short_name())) {
                goodsBean.setGd_short_name(goodsBean.getGd_name());
            }
            holder.productName.setText(goodsBean.getGd_short_name());
            String url = PropertyUtils.getInstance().getFastDfsUrl() + ImageUtils.getImageUrl(goodsBean.getGd_img_s());

            Glide.with(context)
                    .load(url).fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.productIcon);

        }

        if (mcGoodsBean != null) {
            //holder.tvPrice.setText("￥"+NumberUtils.m2(mcGoodsBean.getMg_disc_price() * 0.01));
            holder.tvPrice.setText("积分价："+ mcGoodsBean.getMg_score_price());
           // holder.no.setText();

        } else {
            holder.tvPrice.setText("无货");
        }




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_picon)
        ImageView productIcon;

        @Bind(R.id.tv_pname)
        TextView productName;


        @Bind(R.id.tv_price)
        TextView tvPrice;


        @OnClick(R.id.iv_picon)
        void itemClick() {
            mcGoodsBean = (McGoodsBean) productName.getTag();
            goodsBean = DbManagerHelper.getGoodsInfo(mcGoodsBean.getGd_no());

            if (mcGoodsBean.getMg_gnum() <= 0 || mcGoodsBean.getMg_chann_status().intValue() == ChanStatusEnum.ERROR.getIndex()) {
                // 无货
                ToastUtils.showError("该药品已换购完，请换购其他药品", App.getContext());
                return;
            }
             initview();

        }

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    McGoodsBean mcGoodsBean;
    GoodsBean goodsBean;
    private String slNo;

    private Dialog mDialog;

    private View view;
    ImageView ivLogin;
    ImageView ivReg;
    EditText etUserNo;
    EditText etPwd;
    Button btnLogin;
    private TextView tvPwd;
    private ImageButton ibGetV;
    private boolean regFlag = false;// 默认登录

    public void initview() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.dialog_login, null);

        etUserNo = (EditText) view.findViewById(R.id.et_phone);
        etPwd = (EditText) view.findViewById(R.id.et_pwd);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.cancel();

            }
        });

        ivLogin = (ImageView) view.findViewById(R.id.ib_login);
        ivReg = (ImageView) view.findViewById(R.id.ib_reg);
        tvPwd = (TextView) view.findViewById(R.id.tv_pwd);
        ibGetV = (ImageButton) view.findViewById(R.id.ib_getv);

        ivLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                regFlag = false;
                btnLogin.setText("登录");
                ivLogin.setBackgroundResource(R.mipmap.login);
                ivReg.setBackgroundResource(R.mipmap.reg_1);
                tvPwd.setText("密 码");
                ibGetV.setVisibility(View.GONE);
            }
        });
        ivReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                regFlag = true;
                btnLogin.setText("注册");

                ivLogin.setBackgroundResource(R.mipmap.login_1);
                ivReg.setBackgroundResource(R.mipmap.reg);
                tvPwd.setText("验证码");
                ibGetV.setVisibility(View.VISIBLE);
            }
        });
        ibGetV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
            }
        });

        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(view);
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
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
//                            acountNo = userNo;

                            mDialog.cancel();
                            createOrder(userNo, userPwd);
//                            layPay.setVisibility(View.VISIBLE);
//                            laySelectPay.setVisibility(View.GONE);
//                            // 显示会员价格
//                            tvSalePrice.setText(String.format(gdVipPrice, getPrice(mcGoodsBean.getMg_vip_price())));
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
                        ToastUtils.showShortMessage("获取验证码失败");
                        //CrashReport.postCatchedException(throwable);

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
                            ToastUtils.showShortMessage("注册成功");
//                            acountNo = phoneNo;
//                            //ToastUtils.showShortMessage("注册成功，默认密码是:123456");
//                            // 生成订单
//                            createOrder(String.valueOf(slType.getIndex()), 1);
//                            layPay.setVisibility(View.VISIBLE);
//                            laySelectPay.setVisibility(View.GONE);
//                            // 显示会员价格
//                            tvSalePrice.setText(String.format(gdVipPrice, getPrice(mcGoodsBean.getMg_vip_price())));

                        } else {
                            ToastUtils.showShortMessage("注册失败");

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


//    public void initview() {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        view = inflater.inflate(R.layout.dialog_login, null);
//
//        etUserNo = (EditText) view.findViewById(R.id.et_userno);
//        etPwd = (EditText) view.findViewById(R.id.et_pwd);
//        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
//        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
//
//
//        btnConfirm.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if (!TextUtils.isEmpty(etUserNo.getText().toString())
//                        && !TextUtils.isEmpty(etPwd.getText().toString())) {
//                    createOrder(etUserNo.getText().toString(), etPwd.getText().toString());
//
//                } else {
//                    //
//                    ToastUtils.showShortMessage("请输入正确的会员信息");
//
//                }
//
//            }
//        });
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                mcGoodsBean = null;
//                goodsBean = null;
//                mDialog.cancel();
//            }
//        });
//
//        mDialog = new Dialog(context, R.style.dialog);
//        mDialog.setContentView(view);
//        Window dialogWindow = mDialog.getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
//
//        dialogWindow.setAttributes(lp);
//        mDialog.show();
//    }

    // 普通支付  取折扣价   会员支付取 会员价  vip 0 不是vip  1 是vip

    private void createOrder(String accNo,String pwd) {

        slNo = OrderUtils.getOrderNo();
        createSaleList(slNo, accNo, goodsBean, mcGoodsBean);

        final SaleListVo saleListVo = new SaleListVo();
        saleListVo.setMcNo(App.mcNo);
        saleListVo.setSlType(SlTypeEnum.SCORE.getIndex() + "");
        saleListVo.setSlGdName(goodsBean.getGd_name());
        saleListVo.setSlGdNo(goodsBean.getGd_no());
        saleListVo.setSlChann(mcGoodsBean.getMg_channo());
        saleListVo.setSlScore(mcGoodsBean.getMg_score_price());
        saleListVo.setSlAmt(mcGoodsBean.getMg_score_price());
        saleListVo.setSlAccNo(accNo);
        saleListVo.setSlNo(slNo);
        saleListVo.setSlThPwd(pwd);
        createOrder(saleListVo);



    }



    private void createOrder(final SaleListVo saleListVo) {
        RetrofitManager.builder().createOrder(saleListVo.getMcNo(), saleListVo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //showProgress();
                    }
                })
                .subscribe(new Action1<CommonResponse<CreateOrderResult>>() {
                    @Override
                    public void call(CommonResponse<CreateOrderResult> response) {
                        //hideProgress();
                        if (response.isSuccess()) {
                            // 调用出货

                            Intent intent = new Intent(context, OutGoodsActivity.class);

                            intent.putExtra("slType", SlTypeEnum.SCORE.getIndex());
                            intent.putExtra("slNo", slNo);
                            intent.putExtra("channo", mcGoodsBean.getMg_channo());

                            context.startActivity(intent);
                        } else {
                            Log.d("createOrder:", "创建订单失败:" + response.toString());

                            ToastUtils.showShortMessage("请输入正确的会员信息");
                         }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //hideProgress();
                        //Toast.makeText(GetProductActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                        //ToastUtils.showShortMessage("网络不好，请重试");
                        Log.d("createOrder:", "网络不好:" + throwable.getMessage());
                        //CrashReport.postCatchedException(throwable);

                    }
                });
    }
    private void createSaleList(String slNo, String accNo, GoodsBean goodsBean, McGoodsBean mcGoodsBean) {
        SaleListBean saleListBean = new SaleListBean();
        saleListBean.setSl_pay_status(String.valueOf(SlPayStatusEnum.INIT.getIndex()));
        saleListBean.setMc_no(App.mcNo);
        saleListBean.setSl_chann(mcGoodsBean.getMg_channo());
        saleListBean.setSl_amt(mcGoodsBean.getMg_score_price());
        saleListBean.setSl_isvip("1");

        saleListBean.setSl_disc_price(mcGoodsBean.getMg_disc_price());
        saleListBean.setSl_vip_price(mcGoodsBean.getMg_vip_price());
        saleListBean.setSl_pre_price(mcGoodsBean.getMg_pre_price());
        saleListBean.setSl_score(mcGoodsBean.getMg_score_price());
        saleListBean.setSl_gd_no(mcGoodsBean.getGd_no());
        saleListBean.setSl_send_status(Long.valueOf(SlSendStatusEnum.INIT.getIndex()));
        saleListBean.setSl_gd_name(goodsBean.getGd_name());
        saleListBean.setSl_no(slNo);
        saleListBean.setSl_num(1L);
        saleListBean.setSl_type(String.valueOf(SlTypeEnum.SCORE.getIndex()));
        saleListBean.setSl_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        saleListBean.setSl_acc_no(accNo);
        App.getDaoSession(App.getContext()).getSaleListBeanDao().insertOrReplace(saleListBean);
    }


}
