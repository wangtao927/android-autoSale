package com.ys.ui.common.http;

import com.ys.data.bean.SaleListBean;
import com.ys.ui.common.request.CommonRequest;
import com.ys.ui.common.request.CreateDrawVo;
import com.ys.ui.common.request.ReFundVo;
import com.ys.ui.common.request.SaleListVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.CreateDrawResult;
import com.ys.ui.common.response.CreateOrderResult;
import com.ys.ui.common.response.SaleListResult;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wangtao on 2016/4/17.
 */
public interface OrderApi {

    /**
     * 创建订单
     * @param request
     * @return
     */
    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @POST("createOrder")
    Observable<CommonResponse<CreateOrderResult>> createOrder(@Body CommonRequest<SaleListVo> request);

    /**
     * 查下订单状态
     * @param slNo
     * @return
     */
    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("getOrderStatus")
    Observable<CommonResponse<SaleListResult>> queryOrderByNo(@Query("sl_no") String slNo, @Query("time") long time, @Query("sign") String sign);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @POST("refund")
    Observable<CommonResponse<String>> refund(@Body CommonRequest<Map<String, String>>  request);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @POST("createDraw")
    Observable<CommonResponse<CreateDrawResult>> createDraw(@Body CommonRequest<CreateDrawVo>  request);

}
