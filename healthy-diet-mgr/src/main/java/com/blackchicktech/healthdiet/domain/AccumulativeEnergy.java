package com.blackchicktech.healthdiet.domain;


//用于计算每日摄入的总能量
public class AccumulativeEnergy {

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

    private boolean isEmpty = false;

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

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public static AccumulativeEnergy emptyEnergy() {
        AccumulativeEnergy accumulativeEnergy = new AccumulativeEnergy();
        accumulativeEnergy.setEmpty(true);
        return accumulativeEnergy;
    }

    @Override
    public String toString() {
        return "AccumulativeEnergy{" +
                "totalEnergy=" + totalEnergy +
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
                ", isEmpty=" + isEmpty +
                '}';
    }
}
