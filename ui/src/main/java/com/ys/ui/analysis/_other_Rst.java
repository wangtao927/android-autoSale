package com.ys.ui.analysis;

import java.lang.reflect.Field;

/**
 * Created by thinkpad on 2016/1/31.
 */
public class _other_Rst extends _common_rst{
    /*//4位地区区号+3位缴费行业代号+3位缴费内容码（缴费内容码目前只有有线电视有）+20位用户号+80位客户姓名
    */
    public enum _f {
        AreaCode,// 0;//4位地区区号
        PaymentIndustryCode,// 1;//3位缴费行业代号
        //PaymentContentCode ,// 0;//3位缴费内容码
        UserNumber,// 2;//20位用户号
        CustomerName,// 3;//80位客户姓名

        //除电费、有线电视费：
        // 16位查询流水号+12位应缴金额+保留字段1+保留字段2+（上期余额+滞纳金 注：只有景德镇水费才有此内容）

        QuerySerial,//4;//16位查询流水号
        PayableAmount,//5;//12位应缴金额
        Reserved_1,//6;//保留字段1
        Reserved_2,//7;//保留字段2
        LastBalance,//8;//上期余额
        LateFee,//9;//滞纳金
        }
}
