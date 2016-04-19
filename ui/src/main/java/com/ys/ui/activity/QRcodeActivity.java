package com.ys.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.request.SaleListVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.CreateOrderResult;
import com.ys.ui.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by river on 2016/4/19.
 */
public class QRcodeActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.iv_wx_qrcode)
    ImageView wxQrcodeImage;

    @Bind(R.id.wx_pay)
    Button wxPay;

    @Bind(R.id.ali_pay)
    Button aliPay;

    private GoodsBean goodsBean;
    private McStatusBean statusBean;
    /**
     * 用字符串生成二维码
     * @param str
     * @return
     * @throws WriterException
     */
    public Bitmap create2DCode(String str) throws WriterException {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(matrix.get(x, y)){
                    pixels[y * width + x] = 0xff000000;
                }

            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        //调用接口获取地址
        goodsBean =  App.getDaoSession(App.getContext()).getGoodsBeanDao().loadAll().get(0);
        statusBean =  App.getDaoSession(App.getContext()).getMcStatusBeanDao().loadAll().get(0);
        //createOrder();
        wxPay.setOnClickListener(this);
        aliPay.setOnClickListener(this);

    }

    private void createOrder(String type) {

        SaleListVo saleListVo = new SaleListVo();
        saleListVo.setMcNo(statusBean.getMc_no());
        saleListVo.setSlType(type);
        saleListVo.setSlGdName(goodsBean.getGd_name());
        saleListVo.setSlGdNo(goodsBean.getGd_no());
        saleListVo.setSlAmt(goodsBean.getGd_sale_price());

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
                            try {
                                if (TextUtils.isEmpty(response.getExt_data().getQrcodeUrl())) {
                                    Bitmap qrcodeBitmap = create2DCode(response.getExt_data().getQrcode());
                                    wxQrcodeImage.setImageBitmap(qrcodeBitmap);
                                } else {
                                    Bitmap qrcodeBitmap = create2DCode(response.getExt_data().getQrcodeUrl());
                                    wxQrcodeImage.setImageBitmap(qrcodeBitmap);
                                }


                            } catch (WriterException e) {
                                Log.e("error:", e.getMessage());
                            }
//                            finish();
//                            startActivity(new Intent(GetProductActivity.this, OutGoodsActivity.class));
                            ToastUtils.showShortMessage("支付成功，请等药品出货");
                        }else{
                            ToastUtils.showError("支付失败", QRcodeActivity.this);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //hideProgress();
                        //Toast.makeText(GetProductActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                        ToastUtils.showShortMessage("网络不好，请重试");

                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.wx_pay:
                 createOrder(String.valueOf(SlTypeEnum.WX.getIndex()));
                break;

            case R.id.ali_pay:
                createOrder(String.valueOf(SlTypeEnum.ALIPAY.getIndex()));

                break;
        }
    }
}
