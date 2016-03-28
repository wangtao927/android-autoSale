package com.ys.ui.analysis;

import java.lang.reflect.Field;

/**
 * Created by thinkpad on 2016/1/31.
 */
public class _300_Rst extends _common_rst{
    public enum _f{
        AreaCode,
        PaymentIndustryCode,
        UserNumber,// 2;//20位用户号
        CustomerName,// 3;//80位客户姓名

        //12位应缴纳金额+12用电客户余额+7位供电公司编号+32位代收内部账号+80位代收内部户名+6位摘要码+50摘要信息
        //|应缴纳金额|用电客户余额|商户编码|代收内部账号|客户地址|子商户编码|用电客户余额|
        PayableAmount,//4;//12位应缴纳金额
        ElectricityCustomerBalance,//5;//12用电客户余额
        PowerSupplyCompanyNumber,//6;//7位供电公司编号
        InternalCollectionAccountNumber,//7;//32位代收内部账号
        InternalCollectionAccountName,//8;//80位代收内部户名
        AbstractCode,//9;//6位摘要码
        AbstractInformation,//10;//50摘要信息
    }
/*
    //4位地区区号+3位缴费行业代号+3位缴费内容码（缴费内容码目前只有有线电视有）+20位用户号+80位客户姓名

*/


}
