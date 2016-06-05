package com.ys.ui.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.data.bean.SaleListBean;
import com.ys.data.dao.SaleListBeanDao;
import com.ys.ui.base.App;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.request.CommonRequest;
import com.ys.ui.common.request.McDataVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.McDataResult;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.dao.query.Query;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wangtao on 2016/4/9.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
          Intent i = new Intent(context, TimerService.class);
          context.startService(i);
     }

}
