package com.ys.ui.common.http;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.ys.ui.base.App;
import com.ys.ui.common.request.CommonRequest;
import com.ys.ui.common.request.McDataVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.McDataResult;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.common.sign.Signature;
import com.ys.ui.utils.NetUtil;

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
import rx.Observable;

public class RetrofitManager {

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

    public static RetrofitManager builder() {
        return new RetrofitManager();
    }

    private RetrofitManager() {

        initOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mProductApi = retrofit.create(ProductApi.class);
        termStatusApi = retrofit.create(TermStatusApi.class);
        mcDataApi = retrofit.create(McDataApi.class);
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
        Map map = new HashMap<String, Object>();
        long time = Calendar.getInstance().getTimeInMillis();
        map.put("mc_serial_no", mcno);
        map.put("time", time);
        String sign = Signature.getSign(map);

        return termStatusApi.mcReset(mcno, time, sign);
    }

    public Observable<CommonResponse<McDataResult>> postMcData(CommonRequest<McDataVo> request) {
        Log.w("request:", request.toString());
        return mcDataApi.postMcData(request);
    }

}
