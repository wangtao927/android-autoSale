package com.ys.ui.common.http;

import com.ys.ui.common.request.CommonRequest;
import com.ys.ui.common.request.McDataVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.McDataResult;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wangtao on 2016/4/9.
 */
public interface McDataApi {

    /**
     *  心跳发送数据
     * @return
     */
    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @POST("postMcData")
    Observable<CommonResponse<McDataResult>> postMcData(@Body CommonRequest<McDataVo> request);

    /**
     * 接收心跳数据后， 上传接收数据的处理结果
     * @param request
     * @return
     */
    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @POST("postOprStatus")
    Observable<CommonResponse<String>> postOprStatus(@Body CommonRequest<Map<String, String>> request);

    /**
     * 创建订单
     * @param request
     * @return
     */
//    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
//    @POST("createOrder")
//    Observable<CommonResponse<String>> createOrder(@Body CommonRequest<SaleListVo> request);


}
