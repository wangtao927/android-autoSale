package com.ys.ui.utils;

import java.util.Calendar;

/**
 * Created by wangtao on 2016/4/17.
 */
public class OrderUtils {

    // 交易流水生成规则  终端号+yyyymmddhhiiss


    public static String getOrderNo(String mcNo) {
          return mcNo+TimeUtils.getCurrentTime();
    }
}
