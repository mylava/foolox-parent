package com.foolox.base.constant.sysdic;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 13/08/2019
 */
public enum SysDicCode {
    //系统字典表中的code 和 name
    WX_APP_ID("wx_app_id","微信appID"),
    WX_APP_SECRET("wx_app_secret","微信secret"),
    ;
    private String code;
    private String desc;

    SysDicCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

