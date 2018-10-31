package com.diet.message;

public enum MsgCode {

    /**
     * 操作成功
     */
    Success(0, "操作成功"),
    /**
     * 请求参数错误
     */
    Param_Error(-1, "请求参数错误"),

    /**
     * 没有登录
     */
    Not_Login(-2, "尊敬的用户，您没有登录，请先登录"),

    /**
     * 非法授权
     */
    UNAUTHORIZED(-7, "非法授权"),

    /**
     * 内部错误
     */
    Error(-99, "系统错误，请重试");

    MsgCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

    public int value() {
        return code;
    }

    public String desc(){
        return desc;
    }

    private int code;
    private String desc;
}
