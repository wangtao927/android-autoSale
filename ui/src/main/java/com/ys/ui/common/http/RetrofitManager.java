package com.ys.ui.common.http;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.ys.ui.base.App;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.request.CommonRequest;
import com.ys.ui.common.request.CreateDrawVo;
import com.ys.ui.common.request.McDataVo;
import com.ys.ui.common.request.ReFundVo;
import com.ys.ui.common.request.SaleListVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.CreateDrawResult;
import com.ys.ui.common.response.CreateOrderResult;
import com.ys.ui.common.response.McDataResult;
import com.ys.ui.common.response.SaleListResult;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.common.response.UserResult;
import com.ys.ui.common.sign.Signature;
import com.ys.ui.utils.NetUtil;
import com.ys.ui.utils.OrderUtils;
import com.ys.ui.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Observable;

public class RetrofitManager {

//    public static final String BASE_URL = "http://boai.fccb001.com:8088/api/";
    public static final String BASE_URL = "http://112.74.69.111:8088/api/";
//    public static final String BASE_URL = "http://192.168.1.130:8088/api/";

    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
    private static OkHttpClient mOkHttpClient;
    private final ProductApi mProductApi;
    private final TermStatusApi termStatusApi;
    private final McDataApi mcDataApi;
    private final OrderApi orderApi;
    private final UserApi userApi;

    public static RetrofitManager builder() {
        return new RetrofitManager();
    }

    private RetrofitManager() {

        initOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create()))
                .build();
        mProductApi = retrofit.create(ProductApi.class);
        termStatusApi = retrofit.create(TermStatusApi.class);
        mcDataApi = retrofit.create(McDataApi.class);
        orderApi = retrofit.create(OrderApi.class);
        userApi = retrofit.create(UserApi.class);
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isNetworkConnected()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                //.header("content-type", "application/json")
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
//                        .header("content-type", "application/json")
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };

    public Observable<Object> queryProductByCode(String code) {
        return mProductApi.queryProductByCode(code);
    }



    public Observable<CommonResponse<TermInitResult>>  mcReset(String mcno) {
        Map map = new HashMap<>();
        long time = Calendar.getInstance().getTimeInMillis();
        map.put("mc_no", mcno);
        map.put("time", time);
        String sign = Signature.getSign(map);

        return termStatusApi.mcReset(mcno, time, sign);
    }
    public Observable<CommonResponse<TermInitResult>>  mcInit(String mcno) {
        Map map = new HashMap<>();
        long time = Calendar.getInstance().getTimeInMillis();
        map.put("mc_no", mcno);
        map.put("time", time);
        String sign = Signature.getSign(map);

        return termStatusApi.mcInit(mcno, time, sign);
    }

    public Observable<CommonResponse<McDataResult>> postMcData(CommonRequest<McDataVo> request) {
        Log.d("request-json:", new GsonBuilder().create().toJson(request));
        return mcDataApi.postMcData(request);
    }

    /**
     * 上传操作状态
     * @param mcNo
     * @param map
     * @return
     */
    public Observable<CommonResponse<String>> postOprStatus(String mcNo, Map<String, String> map) {

        CommonRequest<Map<String, String>> request= new CommonRequest<>(mcNo,
                System.currentTimeMillis(), map);
        Log.d("postOprStatus request:", new GsonBuilder().create().toJson(request));

        return mcDataApi.postOprStatus(request);
    }

    /**
     * 创建订单
     * @param mcNo
     * @param saleListVo
     * @return
     */
    public Observable<CommonResponse<CreateOrderResult>> createOrder(String mcNo, SaleListVo saleListVo) {
        // 创建订单号
        saleListVo.setSlNo(OrderUtils.getOrderNo(mcNo));
        CommonRequest<SaleListVo> request = new CommonRequest<>(mcNo, System.currentTimeMillis(), saleListVo);
        Log.d("createOrder request:", new GsonBuilder().create().toJson(request));

        return orderApi.createOrder(request);

    }

    public Observable<CommonResponse<SaleListResult>> getOrderStatus(String slNo) {

        long time = System.currentTimeMillis();
        Map map = new HashMap<>();

        map.put("sl_no", slNo);
        map.put("time", time);
        String sign = Signature.getSign(map);
        return orderApi.queryOrderByNo(slNo, time, sign);

    }

    public Observable<CommonResponse<String>> refund(String slNo) {
        String mcNo = App.mcNo;
        long time = System.currentTimeMillis();


        Map<String, String> data = new HashMap<>();
        data.put("sl_no", slNo);

        CommonRequest<Map<String, String>> request = new CommonRequest<>(mcNo, time, data);
        Log.d("refund request-json:", new GsonBuilder().create().toJson(request));

        return orderApi.refund(request);
    }

    public Observable<CommonResponse<UserResult>> userLogin(String userNo, String userPwd) {
        String mcNo = App.mcNo;
        long time = System.currentTimeMillis();
        Map<String, String> data = new HashMap<>();
        data.put("user_no", userNo);
        data.put("user_pwd", userPwd);

        CommonRequest<Map<String, String>> request = new CommonRequest<>(mcNo, time, data);
        Log.d("userLogin request-json:", new GsonBuilder().create().toJson(request));

        return userApi.userLogin(request);
    }
     public Observable<CommonResponse<String>> userReg(String userNo, String verifyCode) {
        String mcNo = App.mcNo;
        long time = System.currentTimeMillis();


        Map<String, String> data = new HashMap<>();
        data.put("user_no", userNo);
        data.put("verify_code", verifyCode);

        CommonRequest<Map<String, String>> request = new CommonRequest<>(mcNo, time, data);
         Log.d("userReg request-json:", new GsonBuilder().create().toJson(request));
        return userApi.userReg(request);
     }
     public Observable<CommonResponse<String>> getVerifyCode(String userNo) {
        String mcNo = App.mcNo;
        long time = System.currentTimeMillis();

        Map<String, String> data = new HashMap<>();
        data.put("user_no", userNo);

        CommonRequest<Map<String, String>> request = new CommonRequest<>(mcNo, time, data);
         Log.d("getVerifyCode request:", new GsonBuilder().create().toJson(request));

        return userApi.getVerifyCode(request);
     }


    public Observable<CommonResponse<CreateDrawResult>> createDraw(String slNo, String userNo) {

        String mcNo =App.mcNo;
        CreateDrawVo createDrawVo = new CreateDrawVo();
        createDrawVo.setMcNo(mcNo);
        createDrawVo.setSlNo(slNo);
        createDrawVo.setUserNo(userNo);

        Log.d("createDraw request", createDrawVo.toString());
        CommonRequest<CreateDrawVo> request = new CommonRequest<>(mcNo, System.currentTimeMillis(), createDrawVo);

        return orderApi.createDraw(request);

    }

}
