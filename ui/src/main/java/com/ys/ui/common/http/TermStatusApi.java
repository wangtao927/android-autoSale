package com.ys.ui.common.http;

import com.ys.ui.activity.TermInitActivity;
import com.ys.ui.common.response.TermInitResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by wangtao on 2016/4/5.
 */
public interface TermStatusApi {

    /**
     * 初始化终端参数
     * @param termno
     * @param mcno
     * @return
     */
    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("/mcReset")
    TermInitResponse mcReset(String mc_no, String mc_serial_no, long time, String sign);
}
