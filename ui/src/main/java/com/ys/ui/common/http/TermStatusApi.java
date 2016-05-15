package com.ys.ui.common.http;

import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.TermInitResult;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wangtao on 2016/4/5.
 */
public interface TermStatusApi {

    /**
     * 初始化终端参数
     * @return
     */
    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("mcReset")
    Observable<CommonResponse<TermInitResult>> mcReset(@Query("mc_no") String mc_no, @Query("time") long time, @Query("sign") String sign);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("mcInit")
    Observable<CommonResponse<TermInitResult>> mcInit(@Query("mc_no") String mc_no, @Query("time") long time, @Query("sign") String sign);


}
