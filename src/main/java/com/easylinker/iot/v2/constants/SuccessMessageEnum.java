package com.easylinker.iot.v2.constants;

/**
 * Created by wwhai on 2017/11/15.
 */
public enum SuccessMessageEnum {
    LOGIN_SUCCESS(1, "登录成功!"),
    LOG_OUT_SUCCESS(2, "注销成功!");


    private int code;
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    private SuccessMessageEnum(int code, String message) {
        this.code = code;
        this.message = message;

    }

    @Override
    public String toString() {
        return super.toString();

    }
}
