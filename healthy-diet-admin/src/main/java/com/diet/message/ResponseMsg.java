package com.diet.message;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 响应消息
 *
 * @author LiuYu
 **/
public class ResponseMsg implements Serializable {
    private int code = MsgCode.Success.value();
    private String desc = MsgCode.Success.desc();
    private Object data = new JSONObject();

    public ResponseMsg() {
    }

    public ResponseMsg(MsgCode msgCode) {
        this.code = msgCode.value();
        this.desc = msgCode.desc();
    }

    public ResponseMsg(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public ResponseMsg(int code, Object data, String desc) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
