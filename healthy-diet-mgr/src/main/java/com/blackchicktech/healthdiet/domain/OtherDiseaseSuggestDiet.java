package com.blackchicktech.healthdiet.domain;

/**
 * Created by Eric Cen on 2018/8/6.
 */
public class OtherDiseaseSuggestDiet {
    private String otherDisease;
    private String element;
    private String suggestDiet;

    public OtherDiseaseSuggestDiet(String otherDisease, String element, String suggestDiet) {
        this.otherDisease = otherDisease;
        this.element = element;
        this.suggestDiet = suggestDiet;
    }

    public String getOtherDisease() {
        return otherDisease;
    }

    public void setOtherDisease(String otherDisease) {
        this.otherDisease = otherDisease;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getSuggestDiet() {
        return suggestDiet;
    }

    public void setSuggestDiet(String suggestDiet) {
        this.suggestDiet = suggestDiet;
    }
}
