package com.diet.message;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class RequestMsg implements Serializable {
    /**
     * 开始索引,从1开始
     */
    private int currPage = 1;

    /**
     * 最大记录数
     */
    private int pageSize = 10;

    private JSONObject params;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }
}
