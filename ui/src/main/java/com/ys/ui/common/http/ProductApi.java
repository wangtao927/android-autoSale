package com.ys.ui.common.http;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lif on 2016/3/25.
 */
public interface ProductApi {
    /**
     * 根据商品码查询商品
     * @param code
     * @return
     */
    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("/product/{code}")
    Observable<Object> queryProductByCode(@Path("code") String code);




}
