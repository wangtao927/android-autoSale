package com.ys.ui.common.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/7/20.
 */
public class CreateDrawVo implements Serializable {

    @SerializedName("mc_no")
    private String mcNo;
    @SerializedName("sl_no")
    private String slNo;
    @SerializedName("user_no")
    private String userNo;
  /*  @SerializedName("user_pwd")
    private String userPwd;*/

    public String getMcNo() {
        return mcNo;
    }

    public void setMcNo(String mcNo) {
        this.mcNo = mcNo;
    }

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }


    @Override
    public String toString() {
        return "CreateDrawVo{" +
                "mcNo='" + mcNo + '\'' +
                ", slNo='" + slNo + '\'' +
                ", userNo='" + userNo + '\'' +
                '}';
    }
}
