package com.blackchicktech.healthdiet.util.rest;

public enum ApiCode {
    SUCCESS(200, "操作成功"),
    UNAUTHORIZED_ERROR(403, "权限异常，请联系我们"),
    REQUEST_NOT_FOUND_ERROR(404, "请求不存在"),
    ERROR(500, "访问异常，请联系我们");

    private int code;
    private String msg;

    ApiCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
