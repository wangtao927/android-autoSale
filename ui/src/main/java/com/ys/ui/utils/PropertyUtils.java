package com.ys.ui.utils;


import android.text.TextUtils;

import com.ys.data.bean.McParamsBean;
import com.ys.data.dao.McParamsBeanDao;
import com.ys.ui.base.App;
import com.ys.ui.common.constants.ParamEnum;

import de.greenrobot.dao.query.Query;


/**
 * Created by wangtao on 2016/4/9.
 */
public class PropertyUtils {

    static PropertyUtils propertyUtils;


    public PropertyUtils() {
    }

    public static PropertyUtils  getInstance() {
        if (null == propertyUtils) {
            propertyUtils = new PropertyUtils();
        }
        return propertyUtils;
    }
    /**
     * 获取心跳时间
     * @return
     */
    public int getOnlineSendSplit() {
//        return 1;
       return Integer.valueOf(getValue(ParamEnum.ONLINE_SEND_SPLIT));
    }

    public String getFastDfsUrl() {
        return "http://112.74.69.111:8090/";
    }
    /**
     * 获取重试时间
     * @return
     */
    public int getRetryTimes() {
        return Integer.valueOf(getValue(ParamEnum.RETRY_TIMES));
    }

    public int getTransTimeout(){
        return Integer.valueOf(getValue(ParamEnum.TRANS_TIMEOUT));

    }
    public int getNoticeTimeout() {
        return Integer.valueOf(getValue(ParamEnum.NOTICE_TIMEOUT));

    }
    public int getCheckCardTime() {
        return Integer.valueOf(getValue(ParamEnum.CHECK_CARD_TIME));
    }
    public int getSerIpPort() {
        return Integer.valueOf(getValue(ParamEnum.SER_IP_PORT));
    }


    public int getPosPort() {
        return Integer.valueOf(getValue(ParamEnum.POS_PORT));

    }
    public String getPosIp() {
        return getValue(ParamEnum.POS_IP);
    }


    private String getValue(ParamEnum paramEnum) {
        try {
            Query<McParamsBean> query =  App.getDaoSession(App.getContext()).getMcParamsBeanDao()
                    .queryBuilder().where(McParamsBeanDao.Properties.Pcode.eq(paramEnum.getCode())).build();

            McParamsBean bean = query.unique();
            if (bean == null || TextUtils.isEmpty(bean.getPvalue())) {
                return paramEnum.getValue();
            }
            return bean.getPvalue();
        } catch (Exception e) {
            return paramEnum.getValue();
        }

    }
}
