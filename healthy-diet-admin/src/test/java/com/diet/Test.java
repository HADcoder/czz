package com.diet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author LiuYu
 * @date 2018/4/24
 */
public class Test {
    public static void main(String[] args) {
        String json = "{[{\"食材\":\"藕粉\",\"重量\":\"100克\"}]}";
        JSONObject a = JSON.parseObject(json);
        System.out.println(a.toJSONString());
    }
}
