package com.blackchicktech.healthdiet.util.rest;

import com.blackchicktech.healthdiet.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class ResultHelper {
    public static JsonResult build(ApiCode apiCode, Object obj) {
        JsonResult jsonResult = new JsonResult()
                .put("code", apiCode.getCode())
                .put("msg", apiCode.getMsg());
        if(null != obj){
            jsonResult.put("data", obj);
        }else{
            jsonResult.put("data", new ArrayList<>());
        }
        return jsonResult;
    }

    public static JsonResult build(ApiCode apiCode) {
        return new JsonResult()
                .put("code", apiCode.getCode())
                .put("msg", apiCode.getMsg());
    }

    public static JsonResult success(List<Entity> data) {
        return build(ApiCode.SUCCESS, data);
    }

    public static JsonResult success(Object obj) {
        return build(ApiCode.SUCCESS, obj);
    }

    public static JsonResult success() {
        return new JsonResult();
    }

    public static JsonResult error(Object obj) {
        return build(ApiCode.ERROR, obj);
    }

    public static JsonResult error() {
        return build(ApiCode.ERROR);
    }

    public static JsonResult forbidden(){
        return build(ApiCode.UNAUTHORIZED_ERROR);
    }

    public static JsonResult unauthorizedError() {
        return build(ApiCode.UNAUTHORIZED_ERROR);
    }
}
