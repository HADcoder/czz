package com.blackchicktech.healthdiet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class Food implements Entity {

    private int id; //保留字段做key

    private String food_code;

    private String food_group;

    private String sub_code;

    private String sub_group;

    private String code;

    private String food_name;

    private String edible;

    private String water;

    private String energy_kcal;

    private String energy_kj;

    private String protein;

    private int protein_weight;

    private String fat;

    private int fat_weight;

    private String cho;

    private int cho_weight;

    private String glisemik_index;

    private String dietary_fiber;

    private String cholesterol;

    private int cholesterol_weight;

    private String ash;

    private String purine;

    private int purine_weight;

    private String vit_a_total;

    private String carotene;

    private String retinol;

    private String thiamin;

    private String riboflavin;

    private String niacin;

    private String vit_c;

    private String vit_e_total;

    private String vit_e_alpha;

    private String vit_e_beta_garma;

    private String vit_e_delta;

    private String ca;

    private String p;

    private String k;

    private String na;

    private String mg;

    private String fe;

    private String zn;

    private String se;

    private String cu;

    private String mn;

    private String i;

    private String total_cellulose;

    private String soluble;

    private String vit_b_6;

    private String vit_b_12;

    private String folate;

    private String niacin_1;

    private String version;

    private String glisemik_index_1;

    private String remark;

    private String protein_devide_p;




    private String foodName;

    private String picUrl;

    private String description; //预留字段用于说明

    private String type; //类型

    private String subType; //子类型

    private String unit; //一份单位 例如卡路里

    private String value; //默认一份所包含的数值

    public Food() {
    }

    public Food(String code, String foodName, String picUrl, String description, String type, String subType, String unit, String value) {
        this.code = code;
        this.foodName = foodName;
        this.picUrl = picUrl;
        this.description = description;
        this.type = type;
        this.subType = subType;
        this.unit = unit;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFood_code() {
        return food_code;
    }

    public void setFood_code(String food_code) {
        this.food_code = food_code;
    }

    public String getFood_group() {
        return food_group;
    }

    public void setFood_group(String food_group) {
        this.food_group = food_group;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getSub_group() {
        return sub_group;
    }

    public void setSub_group(String sub_group) {
        this.sub_group = sub_group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getEdible() {
        return edible;
    }

    public void setEdible(String edible) {
        this.edible = edible;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getEnergy_kcal() {
        return energy_kcal;
    }

    public void setEnergy_kcal(String energy_kcal) {
        this.energy_kcal = energy_kcal;
    }

    public String getEnergy_kj() {
        return energy_kj;
    }

    public void setEnergy_kj(String energy_kj) {
        this.energy_kj = energy_kj;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public int getProtein_weight() {
        return protein_weight;
    }

    public void setProtein_weight(int protein_weight) {
        this.protein_weight = protein_weight;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public int getFat_weight() {
        return fat_weight;
    }

    public void setFat_weight(int fat_weight) {
        this.fat_weight = fat_weight;
    }

    public String getCho() {
        return cho;
    }

    public void setCho(String cho) {
        this.cho = cho;
    }

    public int getCho_weight() {
        return cho_weight;
    }

    public void setCho_weight(int cho_weight) {
        this.cho_weight = cho_weight;
    }

    public String getGlisemik_index() {
        return glisemik_index;
    }

    public void setGlisemik_index(String glisemik_index) {
        this.glisemik_index = glisemik_index;
    }

    public String getDietary_fiber() {
        return dietary_fiber;
    }

    public void setDietary_fiber(String dietary_fiber) {
        this.dietary_fiber = dietary_fiber;
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getCholesterol_weight() {
        return cholesterol_weight;
    }

    public void setCholesterol_weight(int cholesterol_weight) {
        this.cholesterol_weight = cholesterol_weight;
    }

    public String getAsh() {
        return ash;
    }

    public void setAsh(String ash) {
        this.ash = ash;
    }

    public String getPurine() {
        return purine;
    }

    public void setPurine(String purine) {
        this.purine = purine;
    }

    public int getPurine_weight() {
        return purine_weight;
    }

    public void setPurine_weight(int purine_weight) {
        this.purine_weight = purine_weight;
    }

    public String getVit_a_total() {
        return vit_a_total;
    }

    public void setVit_a_total(String vit_a_total) {
        this.vit_a_total = vit_a_total;
    }

    public String getCarotene() {
        return carotene;
    }

    public void setCarotene(String carotene) {
        this.carotene = carotene;
    }

    public String getRetinol() {
        return retinol;
    }

    public void setRetinol(String retinol) {
        this.retinol = retinol;
    }

    public String getThiamin() {
        return thiamin;
    }

    public void setThiamin(String thiamin) {
        this.thiamin = thiamin;
    }

    public String getRiboflavin() {
        return riboflavin;
    }

    public void setRiboflavin(String riboflavin) {
        this.riboflavin = riboflavin;
    }

    public String getNiacin() {
        return niacin;
    }

    public void setNiacin(String niacin) {
        this.niacin = niacin;
    }

    public String getVit_c() {
        return vit_c;
    }

    public void setVit_c(String vit_c) {
        this.vit_c = vit_c;
    }

    public String getVit_e_total() {
        return vit_e_total;
    }

    public void setVit_e_total(String vit_e_total) {
        this.vit_e_total = vit_e_total;
    }

    public String getVit_e_alpha() {
        return vit_e_alpha;
    }

    public void setVit_e_alpha(String vit_e_alpha) {
        this.vit_e_alpha = vit_e_alpha;
    }

    public String getVit_e_beta_garma() {
        return vit_e_beta_garma;
    }

    public void setVit_e_beta_garma(String vit_e_beta_garma) {
        this.vit_e_beta_garma = vit_e_beta_garma;
    }

    public String getVit_e_delta() {
        return vit_e_delta;
    }

    public void setVit_e_delta(String vit_e_delta) {
        this.vit_e_delta = vit_e_delta;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getMg() {
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }

    public String getFe() {
        return fe;
    }

    public void setFe(String fe) {
        this.fe = fe;
    }

    public String getZn() {
        return zn;
    }

    public void setZn(String zn) {
        this.zn = zn;
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }

    public String getCu() {
        return cu;
    }

    public void setCu(String cu) {
        this.cu = cu;
    }

    public String getMn() {
        return mn;
    }

    public void setMn(String mn) {
        this.mn = mn;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getTotal_cellulose() {
        return total_cellulose;
    }

    public void setTotal_cellulose(String total_cellulose) {
        this.total_cellulose = total_cellulose;
    }

    public String getSoluble() {
        return soluble;
    }

    public void setSoluble(String soluble) {
        this.soluble = soluble;
    }

    public String getVit_b_6() {
        return vit_b_6;
    }

    public void setVit_b_6(String vit_b_6) {
        this.vit_b_6 = vit_b_6;
    }

    public String getVit_b_12() {
        return vit_b_12;
    }

    public void setVit_b_12(String vit_b_12) {
        this.vit_b_12 = vit_b_12;
    }

    public String getFolate() {
        return folate;
    }

    public void setFolate(String folate) {
        this.folate = folate;
    }

    public String getNiacin_1() {
        return niacin_1;
    }

    public void setNiacin_1(String niacin_1) {
        this.niacin_1 = niacin_1;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGlisemik_index_1() {
        return glisemik_index_1;
    }

    public void setGlisemik_index_1(String glisemik_index_1) {
        this.glisemik_index_1 = glisemik_index_1;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProtein_devide_p() {
        return protein_devide_p;
    }

    public void setProtein_devide_p(String protein_devide_p) {
        this.protein_devide_p = protein_devide_p;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    @JsonIgnore
    public Map<String, Object[]> getFieldInfo() {
        throw new IllegalStateException("not supported yet");
    }
}
