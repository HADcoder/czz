package com.blackchicktech.healthdiet.entity;

import com.blackchicktech.healthdiet.domain.AccumulativeEnergy;

import java.util.Date;

public class FoodLog {

    private String openId;

    private Date date;

    private boolean isCompletedLog;

    private double totalEnergy;

    private double totalProtein;

    private double peRatio; //蛋白质供能比

    private double fat;

    private double feRatio; //脂肪供能比

    private double cho;

    private double ceRatio; //碳水化物功能比

    private double na;

    private double k;

    private double p;

    private double ca;

    public FoodLog(String openId, Date d, boolean isCompletedLog, AccumulativeEnergy energy) {
        this.openId = openId;
        this.date = d;
        this.isCompletedLog = isCompletedLog;
        this.totalEnergy = energy.getTotalEnergy();
        this.totalProtein = energy.getTotalProtein();
        this.peRatio = energy.getPeRatio();
        this.fat = energy.getFat();
        this.feRatio = energy.getFeRatio();
        this.cho = energy.getCho();
        this.ceRatio = energy.getCeRatio();
        this.na = energy.getNa();
        this.k = energy.getK();
        this.p = energy.getP();
        this.ca = energy.getCa();
    }

    public FoodLog(String openId, Date date, boolean isCompletedLog, double totalEnergy, double totalProtein, double peRatio, double fat, double feRatio, double cho, double ceRatio, double na, double k, double p, double ca) {
        this.openId = openId;
        this.date = date;
        this.isCompletedLog = isCompletedLog;
        this.totalEnergy = totalEnergy;
        this.totalProtein = totalProtein;
        this.peRatio = peRatio;
        this.fat = fat;
        this.feRatio = feRatio;
        this.cho = cho;
        this.ceRatio = ceRatio;
        this.na = na;
        this.k = k;
        this.p = p;
        this.ca = ca;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCompletedLog() {
        return isCompletedLog;
    }

    public void setCompletedLog(boolean completedLog) {
        isCompletedLog = completedLog;
    }

    public double getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(double totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public double getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(double totalProtein) {
        this.totalProtein = totalProtein;
    }

    public double getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(double peRatio) {
        this.peRatio = peRatio;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getFeRatio() {
        return feRatio;
    }

    public void setFeRatio(double feRatio) {
        this.feRatio = feRatio;
    }

    public double getCho() {
        return cho;
    }

    public void setCho(double cho) {
        this.cho = cho;
    }

    public double getCeRatio() {
        return ceRatio;
    }

    public void setCeRatio(double ceRatio) {
        this.ceRatio = ceRatio;
    }

    public double getNa() {
        return na;
    }

    public void setNa(double na) {
        this.na = na;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getCa() {
        return ca;
    }

    public void setCa(double ca) {
        this.ca = ca;
    }

    @Override
    public String toString() {
        return "FoodLog{" +
                "openId='" + openId + '\'' +
                ", date=" + date +
                ", isCompletedLog=" + isCompletedLog +
                ", totalEnergy=" + totalEnergy +
                ", totalProtein=" + totalProtein +
                ", peRatio=" + peRatio +
                ", fat=" + fat +
                ", feRatio=" + feRatio +
                ", cho=" + cho +
                ", ceRatio=" + ceRatio +
                ", na=" + na +
                ", k=" + k +
                ", p=" + p +
                ", ca=" + ca +
                '}';
    }
}
