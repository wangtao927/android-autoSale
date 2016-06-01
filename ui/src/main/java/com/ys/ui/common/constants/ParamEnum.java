package com.ys.ui.common.constants;

/**
 * Created by wangtao on 2016/4/5.
 */
public enum ParamEnum {

    COIN_MIN_AMOUNT("06","硬币预警金额", "5000"),
    ONLINE_SEND_SPLIT("09","在线信息发送间隔","300"),
    RETRY_TIMES("0C","重发次数","3"),
    SYSTEM_TIME("99","系统时间",""),
    TRANS_TIMEOUT("23","交易超时","120"),
    OTHER_TIMEOUT("24","其他超时","120"),
    NOTICE_TIMEOUT("30","提示超时","10"),
    REFUND_TIMEOUT("31","找零超时","150"),
    CHECK_CARD_TIME("32","验卡时间","10"),
    SER_IP_PORT("22","服务端IP和端口","183.62.210.168:8098"),
    CASH_WARN("05","纸币预警（分）","2000"),
    GOODS_OUT("04","缺货预警","1"),
    AD_IP("03","广告IP",""),
    AD_START("33","广告开始时间",""),
    SERIAL_PATH("41","串口号",""),
    SERIAL_PORT("42","波特率",""),
    POS_IP("43","银联POS后台IP","211.147.64.198"),
    POS_PORT("44","银联POS后台端口号","5800"),

    ;


    String code;
    String name;
    String value;

    ParamEnum(String code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ParamEnum findParamByCode(String code) {
        for (ParamEnum param: ParamEnum.values()) {
            if (param.getCode().equals(code)) {
                return param;
            }
        }
        return null;
    }
}
