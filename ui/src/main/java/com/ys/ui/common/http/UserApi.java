package com.ys.ui.common.http;

import com.ys.ui.common.request.CommonRequest;
import com.ys.ui.common.request.UserVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.CreateOrderResult;
import com.ys.ui.common.response.UserResult;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wangtao on 2016/5/27.
 */
public interface UserApi {

    /**
     * 创建订单
     * @param request
     * @return
     */
    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @POST("userLogin")
    Observable<CommonResponse<UserResult>> userLogin (@Body CommonRequest<Map<String, String>> request);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @POST("getVerifyCode")
    Observable<CommonResponse<String>> getVerifyCode (@Body CommonRequest<Map<String, String>> request);

     @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @POST("regUser")
    Observable<CommonResponse<String>> userReg (@Body CommonRequest<Map<String, String>> request);

}
