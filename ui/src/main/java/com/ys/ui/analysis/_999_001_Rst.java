package com.ys.ui.analysis;

import java.lang.reflect.Field;

/**
 * Created by thinkpad on 2016/1/31.
 */
public class _999_001_Rst extends _common_rst{
    /*//4位地区区号+3位缴费行业代号+3位缴费内容码（缴费内容码目前只有有线电视有）+20位用户号+80位客户姓名
    */
    public enum _f {
        AreaCode,// 0;//4位地区区号
        PaymentIndustryCode,// 1;//3位缴费行业代号
        PaymentContentCode,// 0;//3位缴费内容码
        UserNumber,// 2;//20位用户号
        CustomerName,// 3;//80位客户姓名

        //缴费内容码为001
//20位账户编码+20位客户编码+2位业务编码+4位运营区域编码+25收款账号+60位收款户名+2位返回记录条数（最大返回5条）+20位余额账本编码+60位余额账本名称+20位账本余额；
//20位账户编码+20位客户编码+2位业务编码+4位运营区域编码+25收款账号+60位收款户名+2位返回记录条数（最大返回5条）[XML报文处理受限，最大只接受5条记录]+20位余额账本编码+60位余额账本名称+20位账本余额；
        AccountCode,//4;//20位账户编码
        CustomerCode,//5;//20位客户编码
        BusinessCode,//6;//2位业务编码
        OperationAreaCode,//7;//4位运营区域编码
        CollectionAccountNumber,//8;//25收款账号
        CollectionAccountName,//9;//60位收款户名
        ReturnsRecordsCnt,//10;//2位返回记录条数（最大返回5条）
        BalanceAccountCode,//11;//20位余额账本编码
        BalanceAccountName,//12;//60位余额账本名称
        Balance,//13;//20位账本余额

        }
}
