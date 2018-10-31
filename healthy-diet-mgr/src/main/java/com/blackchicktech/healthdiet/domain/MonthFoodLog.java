package com.blackchicktech.healthdiet.domain;

import com.blackchicktech.healthdiet.entity.FoodLog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

//每月历史记录
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonthFoodLog {

    @JsonProperty("logDate")
    private Date date;

    @JsonProperty("isLogged")
    private boolean logged;//是否三餐记录完毕

    @JsonProperty("totalEnergy")
    private String totalEnergy;

    @JsonProperty("totalProtein")
    private String totalProtein;

    @JsonProperty("peRatio")
    private String peRatio;

    @JsonProperty("fat")
    private String fat;

    @JsonProperty("feRatio")
    private String feRatio;

    @JsonProperty("cho")
    private String cho;

    @JsonProperty("ceRatio")
    private String ceRatio;

    @JsonProperty("na")
    private String na;

    @JsonProperty("k")
    private String k;

    @JsonProperty("p")
    private String p;

    @JsonProperty("ca")
    private String ca;

    @JsonProperty("expectEnergy")
    private String expectEnergy;

    @JsonProperty("expectProtein")
    private String expectProtein;

    public MonthFoodLog(FoodLog foodLog) {
        this.date = foodLog.getDate();
        this.logged = foodLog.isCompletedLog();
        this.totalEnergy = formatDouble(foodLog.getTotalEnergy());
        this.totalProtein = formatDouble(foodLog.getTotalProtein());
        this.peRatio = formatDouble(foodLog.getPeRatio());
        this.fat = formatDouble(foodLog.getFat());
        this.feRatio = formatDouble(foodLog.getFeRatio());
        this.cho = formatDouble(foodLog.getCho());
        this.ceRatio = formatDouble(foodLog.getCeRatio());
        this.na = formatDouble(foodLog.getNa());
        this.k = formatDouble(foodLog.getK());
        this.p = formatDouble(foodLog.getP());
        this.ca = formatDouble(foodLog.getCa());
    }

	public MonthFoodLog(String expectProtein, String expectEnergy) {
		this.expectEnergy = expectEnergy;
        this.expectProtein = expectProtein;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(String totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public String getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(String totalProtein) {
        this.totalProtein = totalProtein;
    }

    public String getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(String peRatio) {
        this.peRatio = peRatio;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getFeRatio() {
        return feRatio;
    }

    public void setFeRatio(String feRatio) {
        this.feRatio = feRatio;
    }

    public String getCho() {
        return cho;
    }

    public void setCho(String cho) {
        this.cho = cho;
    }

    public String getCeRatio() {
        return ceRatio;
    }

    public void setCeRatio(String ceRatio) {
        this.ceRatio = ceRatio;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public void setExpectEnergy(String expectEnergy) {
        this.expectEnergy = expectEnergy;
    }

    public void setExpectProtein(String expectProtein) {
        this.expectProtein = expectProtein;
    }

    public String getExpectEnergy() {
        return expectEnergy;
    }

    public String getExpectProtein() {
        return expectProtein;
    }

    @Override
    public String toString() {
        return "MonthFoodLog{" +
                "date=" + date +
                ", logged=" + logged +
                ", totalEnergy='" + totalEnergy + '\'' +
                ", totalProtein='" + totalProtein + '\'' +
                ", peRatio='" + peRatio + '\'' +
                ", fat='" + fat + '\'' +
                ", feRatio='" + feRatio + '\'' +
                ", cho='" + cho + '\'' +
                ", ceRatio='" + ceRatio + '\'' +
                ", na='" + na + '\'' +
                ", k='" + k + '\'' +
                ", p='" + p + '\'' +
                ", ca='" + ca + '\'' +
                ", expectEnergy='" + expectEnergy + '\'' +
                ", expectProtein='" + expectProtein + '\'' +
                '}';
    }

    @JsonIgnore
    private static String formatDouble(double d) {
        return String.format("%.2f",d);
    }
}
