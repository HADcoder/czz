package com.blackchicktech.healthdiet.util.rest;

import java.util.HashMap;

public class JsonResult extends HashMap<String, Object> {
    public JsonResult(){
       put("code", ApiCode.SUCCESS.getCode());
       put("msg", ApiCode.SUCCESS.getMsg());
    }

    public JsonResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
