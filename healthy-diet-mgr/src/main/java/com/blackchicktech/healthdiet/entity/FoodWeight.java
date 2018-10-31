package com.blackchicktech.healthdiet.entity;

//食物赋值表
public class FoodWeight {

    private String foodId;

    private String foodCode;

    private String subCode;

    private int proteinWeight;

    private int fatWeight;

    private int choWeight;

    private int kWeight;

    private int naWeight;

    private int purineWeight;

    private int cholesterolWeight;

    private int pWeight;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public int getProteinWeight() {
        return proteinWeight;
    }

    public void setProteinWeight(int proteinWeight) {
        this.proteinWeight = proteinWeight;
    }

    public int getFatWeight() {
        return fatWeight;
    }

    public void setFatWeight(int fatWeight) {
        this.fatWeight = fatWeight;
    }

    public int getChoWeight() {
        return choWeight;
    }

    public void setChoWeight(int choWeight) {
        this.choWeight = choWeight;
    }

    public int getkWeight() {
        return kWeight;
    }

    public void setkWeight(int kWeight) {
        this.kWeight = kWeight;
    }

    public int getNaWeight() {
        return naWeight;
    }

    public void setNaWeight(int naWeight) {
        this.naWeight = naWeight;
    }

    public int getPurineWeight() {
        return purineWeight;
    }

    public void setPurineWeight(int purineWeight) {
        this.purineWeight = purineWeight;
    }

    public int getCholesterolWeight() {
        return cholesterolWeight;
    }

    public void setCholesterolWeight(int cholesterolWeight) {
        this.cholesterolWeight = cholesterolWeight;
    }

    public int getpWeight() {
        return pWeight;
    }

    public void setpWeight(int pWeight) {
        this.pWeight = pWeight;
    }

    @Override
    public String toString() {
        return "FoodWeight{" +
                "foodId='" + foodId + '\'' +
                ", foodCode='" + foodCode + '\'' +
                ", subCode='" + subCode + '\'' +
                ", proteinWeight=" + proteinWeight +
                ", fatWeight=" + fatWeight +
                ", choWeight=" + choWeight +
                ", kWeight=" + kWeight +
                ", naWeight=" + naWeight +
                ", purineWeight=" + purineWeight +
                ", cholesterolWeight=" + cholesterolWeight +
                ", pWeight=" + pWeight +
                '}';
    }
}
