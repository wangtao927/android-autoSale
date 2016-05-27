package com.ys.ui.common.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/5/27.
 */
public class UserVo implements Serializable {

    @SerializedName("user_no")
    private String userNo;

    @SerializedName("user_pwd")
    private String userPwd;

    public UserVo(String userNo, String userPwd) {
        this.userNo = userNo;
        this.userPwd = userPwd;
    }

    public UserVo() {
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
