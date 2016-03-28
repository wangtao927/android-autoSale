package com.ys.ui.analysis;

import java.io.UnsupportedEncodingException;

/**
 * Created by thinkpad on 2016/2/1.
 */
public class PassbookRenewRecord {

        public String pageNum = "";//(页号A (2)+
        public String lineNum = "";// 行号A (2)+
        public String date = "";// 交易日期D(8)+
        public String summary = "";// 交易摘要A(10)+
        public String loanCreditFlag = "";// 借贷标志A(1)+
        public String amount = "";// 交易金额S(15,2)+
        public String balance = "";// 账户余额S(15,2)+
        public String operater = "";// 交易柜员A(6)+
        public String site = "";// 交易网点号A(5)

    public static PassbookRenewRecord Unpack(byte[] data, int offset) throws UnsupportedEncodingException {
        PassbookRenewRecord ret = new PassbookRenewRecord();
        int i = offset;
        byte[] b_pageNum = new byte[2];//(页号A (2)+
        byte[] b_lineNum = new byte[2];// 行号A (2)+
        byte[] b_date = new byte[8];// 交易日期D(8)+
        byte[] b_summary = new byte[10];// 交易摘要A(10)+
        byte[] b_loanCreditFlag = new byte[1];// 借贷标志A(1)+
        byte[] b_amount = new byte[15];// 交易金额S(15,2)+
        byte[] b_balance = new byte[15];// 账户余额S(15,2)+
        byte[] b_operater = new byte[6];// 交易柜员A(6)+
        byte[] b_site = new byte[5];// 交易网点号A(5)

        System.arraycopy(data, i, b_pageNum, 0, b_pageNum.length);
        i+=b_pageNum.length;
        System.arraycopy(data, i, b_lineNum, 0, b_lineNum.length);
        i+=b_lineNum.length;
        System.arraycopy(data, i, b_date, 0, b_date.length);
        i+=b_date.length;
        System.arraycopy(data, i, b_summary, 0, b_summary.length);
        i+=b_summary.length;
        System.arraycopy(data, i, b_loanCreditFlag, 0, b_loanCreditFlag.length);
        i+=b_loanCreditFlag.length;
        System.arraycopy(data, i, b_amount, 0, b_amount.length);
        i+=b_amount.length;
        System.arraycopy(data, i, b_balance, 0, b_balance.length);
        i+=b_balance.length;
        System.arraycopy(data, i, b_operater, 0, b_operater.length);
        i+=b_operater.length;
        System.arraycopy(data, i, b_site, 0, b_site.length);
        i+=b_site.length;

        ret.pageNum = new String(b_pageNum,"GBK");;//(页号A (2)+
        ret.lineNum=(new String(b_lineNum,"GBK"));;// 行号A (2)+
        ret.date=(new String(b_date,"GBK"));;// 交易日期D(8)+
        ret.summary=(new String(b_summary,"GBK"));;// 交易摘要A(10)+
        ret.loanCreditFlag=(new String(b_loanCreditFlag,"GBK"));;// 借贷标志A(1)+
        ret.amount=(new String(b_amount,"GBK"));;// 交易金额S(15,2)+
        ret.balance=(new String(b_balance,"GBK"));;// 账户余额S(15,2)+
        ret.operater=(new String(b_operater,"GBK"));;// 交易柜员A(6)+
        ret.site=(new String(b_site,"GBK"));;// 交易网点号A(5)

        return ret;
    }
}
