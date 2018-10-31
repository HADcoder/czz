package com.blackchicktech.healthdiet.domain;

/**
 * Created by Eric Cen on 2018/8/4.
 */
public enum BodyType {
    MALNOURISHED("营养不良"),
    NORMAL("正常"),
    OVERWEIGHT("超重"),
    FATTY("肥胖");


    private String info;

    BodyType(String info){
        this.info = info;
    }

    public String info(){
        return info;
    }
}
