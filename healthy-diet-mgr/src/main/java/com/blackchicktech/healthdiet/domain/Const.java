package com.blackchicktech.healthdiet.domain;

public final class Const {

    public enum FoodAdviceLevel {
        SUGGEST("建议"),
        WARNING("警告"),
        FORBIDDEN("禁止");

        private String info;

        FoodAdviceLevel(String info){
            this.info = info;
        }

        public String info(){
            return info;
        }
    }
}
