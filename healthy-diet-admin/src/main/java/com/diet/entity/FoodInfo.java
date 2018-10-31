package com.diet.entity;

import com.diet.core.base.BaseEntity;

import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_food_info")
public class FoodInfo extends BaseEntity {

	/**
	 * 主键

	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 类
	 */ 
	@Column(name = "type")
	private Integer type;

	/**
	 * 类名称
	 */ 
	@Column(name = "typeName")
	private String typeName;

	/**
	 * 亚类
	 */ 
	@Column(name = "subType")
	private Integer subType;

	/**
	 * 亚类名称
	 */ 
	@Column(name = "subTypeName")
	private String subTypeName;

	/**
	 * 食材别名
	 */ 
	@Column(name = "foodAlias")
	private String foodAlias;

	/**
	 * 编码
	 */ 
	@Column(name = "foodCode")
	private String foodCode;

	/**
	 * 食材名称
	 */ 
	@Column(name = "foodName")
	private String foodName;

	/**
	 * 食部
	 */ 
	@Column(name = "edible")
	private String edible;

	/**
	 * 水分
	 */ 
	@Column(name = "water")
	private String water;

	/**
	 * 能量，单位：kcal
	 */ 
	@Column(name = "energyKcal")
	private String energyKcal;

	/**
	 * 能量，单位：kj
	 */ 
	@Column(name = "energyKj")
	private String energyKj;

	/**
	 * 蛋白质
	 */ 
	@Column(name = "protein")
	private String protein;

	/**
	 * 蛋白质赋值
	 */ 
	@Column(name = "proteinCls")
	private String proteinCls;

	/**
	 * 脂肪
	 */ 
	@Column(name = "fat")
	private String fat;

	/**
	 * 脂肪赋值
	 */ 
	@Column(name = "fatCls")
	private String fatCls;

	/**
	 * 碳水化合物
	 */ 
	@Column(name = "cho")
	private String cho;

	/**
	 * 碳水化合物赋值
	 */ 
	@Column(name = "choCls")
	private String choCls;

	/**
	 * 血糖指数
	 */ 
	@Column(name = "gi")
	private String gi;

	/**
	 * 胆固醇
	 */ 
	@Column(name = "cholesterol")
	private String cholesterol;

	/**
	 * 胆固醇赋值
	 */ 
	@Column(name = "cholesterolCls")
	private String cholesterolCls;

	/**
	 * 灰分
	 */ 
	@Column(name = "ash")
	private String ash;

	/**
	 * 嘌呤
	 */ 
	@Column(name = "purine")
	private String purine;

	/**
	 * 嘌呤赋值
	 */ 
	@Column(name = "purineCls")
	private String purineCls;

	/**
	 * 总纤维
	 */ 
	@Column(name = "fiber")
	private String fiber;

	/**
	 * 可溶性纤维
	 */ 
	@Column(name = "soluble")
	private String soluble;

	/**
	 * 不溶性纤维
	 */ 
	@Column(name = "dietaryFiber")
	private String dietaryFiber;

	/**
	 * 视黄醇
	 */ 
	@Column(name = "retinol")
	private String retinol;

	/**
	 * 硫胺素
	 */ 
	@Column(name = "thiamin")
	private String thiamin;

	/**
	 * 核黄素
	 */ 
	@Column(name = "riboflavin")
	private String riboflavin;

	/**
	 * 尼克酸
	 */ 
	@Column(name = "nicotinic")
	private String nicotinic;

	/**
	 * 叶酸
	 */ 
	@Column(name = "folate")
	private String folate;

	/**
	 * 烟酸
	 */ 
	@Column(name = "niacin")
	private String niacin;

	/**
	 * 胡萝卜素
	 */ 
	@Column(name = "carotene")
	private String carotene;

	/**
	 * 总维生素A
	 */ 
	@Column(name = "vitA")
	private String vitA;

	/**
	 * 维生素C
	 */ 
	@Column(name = "vitC")
	private String vitC;

	/**
	 * 维生素E
	 */ 
	@Column(name = "vitE")
	private String vitE;

	/**
	 * 维生素E1
	 */ 
	@Column(name = "vitE1")
	private String vitE1;

	/**
	 * 维生素E2
	 */ 
	@Column(name = "vitE2")
	private String vitE2;

	/**
	 * 维生素E3
	 */ 
	@Column(name = "vitE3")
	private String vitE3;

	/**
	 * 维生素B6
	 */ 
	@Column(name = "vitB6")
	private String vitB6;

	/**
	 * 维生素B12
	 */ 
	@Column(name = "vitB12")
	private String vitB12;

	/**
	 * 钙
	 */ 
	@Column(name = "aceEleCa")
	private String aceEleCa;

	/**
	 * 磷
	 */ 
	@Column(name = "aceEleP")
	private String aceEleP;

	/**
	 * 磷赋值
	 */ 
	@Column(name = "aceElePCls")
	private String aceElePCls;

	/**
	 * 蛋白质/麟
	 */ 
	@Column(name = "proteinDivP")
	private String proteinDivP;

	/**
	 * 钾
	 */ 
	@Column(name = "aceEleK")
	private String aceEleK;

	/**
	 * 钾赋值
	 */ 
	@Column(name = "aceEleKCls")
	private String aceEleKCls;

	/**
	 * 钠
	 */ 
	@Column(name = "aceEleNa")
	private String aceEleNa;

	/**
	 * 钠赋值
	 */ 
	@Column(name = "aceEleNaCls")
	private String aceEleNaCls;

	/**
	 * 镁
	 */ 
	@Column(name = "aceEleMg")
	private String aceEleMg;

	/**
	 * 铁
	 */ 
	@Column(name = "aceEleFe")
	private String aceEleFe;

	/**
	 * 锌
	 */ 
	@Column(name = "aceEleZn")
	private String aceEleZn;

	/**
	 * 硒
	 */ 
	@Column(name = "aceEleSe")
	private String aceEleSe;

	/**
	 * 铜
	 */ 
	@Column(name = "aceEleCu")
	private String aceEleCu;

	/**
	 * 锰
	 */ 
	@Column(name = "aceEleMn")
	private String aceEleMn;

	/**
	 * 碘
	 */ 
	@Column(name = "aceEleI")
	private String aceEleI;

	/**
	 * 版本
	 */ 
	@Column(name = "version")
	private String version;

	/**
	 * 备注
	 */ 
	@Column(name = "remark")
	private String remark;

	/**
	 * 摄入份量
	 */ 
	@Column(name = "intake")
	private String intake;

	/**
	 * CKD对应食物分类
	 */ 
	@Column(name = "ckd")
	private String ckd;

	/**
	 * 状态：0、未确认，1、已确认
	 */
	@Column(name = "state")
	private Integer state;

	@Transient
	private Float weight;

	public FoodInfo() {

	}

	public FoodInfo(Integer type, String typeName) {
		this.type = type;
		this.typeName = typeName;
	}

	public FoodInfo(Integer type, String typeName, Integer subType, String subTypeName) {
		this.type = type;
		this.typeName = typeName;
		this.subType = subType;
		this.subTypeName = subTypeName;
	}

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getType(){
		return type;
	}

	public void setType(Integer type){
		this.type=type;
	}

	public String getTypeName(){
		return typeName;
	}

	public void setTypeName(String typeName){
		this.typeName=typeName;
	}

	public Integer getSubType(){
		return subType;
	}

	public void setSubType(Integer subType){
		this.subType=subType;
	}

	public String getSubTypeName(){
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName){
		this.subTypeName=subTypeName;
	}

	public String getFoodAlias(){
		return foodAlias;
	}

	public void setFoodAlias(String foodAlias){
		this.foodAlias=foodAlias;
	}

	public String getFoodCode(){
		return foodCode;
	}

	public void setFoodCode(String foodCode){
		this.foodCode=foodCode;
	}

	public String getFoodName(){
		return foodName;
	}

	public void setFoodName(String foodName){
		this.foodName=foodName;
	}

	public String getEdible(){
		return edible;
	}

	public void setEdible(String edible){
		this.edible=edible;
	}

	public String getWater(){
		return water;
	}

	public void setWater(String water){
		this.water=water;
	}

	public String getEnergyKcal(){
		return energyKcal;
	}

	public void setEnergyKcal(String energyKcal){
		this.energyKcal=energyKcal;
	}

	public String getEnergyKj(){
		return energyKj;
	}

	public void setEnergyKj(String energyKj){
		this.energyKj=energyKj;
	}

	public String getProtein(){
		return protein;
	}

	public void setProtein(String protein){
		this.protein=protein;
	}

	public String getProteinCls(){
		return proteinCls;
	}

	public void setProteinCls(String proteinCls){
		this.proteinCls=proteinCls;
	}

	public String getFat(){
		return fat;
	}

	public void setFat(String fat){
		this.fat=fat;
	}

	public String getFatCls(){
		return fatCls;
	}

	public void setFatCls(String fatCls){
		this.fatCls=fatCls;
	}

	public String getCho(){
		return cho;
	}

	public void setCho(String cho){
		this.cho=cho;
	}

	public String getChoCls(){
		return choCls;
	}

	public void setChoCls(String choCls){
		this.choCls=choCls;
	}

	public String getGi(){
		return gi;
	}

	public void setGi(String gi){
		this.gi=gi;
	}

	public String getCholesterol(){
		return cholesterol;
	}

	public void setCholesterol(String cholesterol){
		this.cholesterol=cholesterol;
	}

	public String getCholesterolCls(){
		return cholesterolCls;
	}

	public void setCholesterolCls(String cholesterolCls){
		this.cholesterolCls=cholesterolCls;
	}

	public String getAsh(){
		return ash;
	}

	public void setAsh(String ash){
		this.ash=ash;
	}

	public String getPurine(){
		return purine;
	}

	public void setPurine(String purine){
		this.purine=purine;
	}

	public String getPurineCls(){
		return purineCls;
	}

	public void setPurineCls(String purineCls){
		this.purineCls=purineCls;
	}

	public String getFiber(){
		return fiber;
	}

	public void setFiber(String fiber){
		this.fiber=fiber;
	}

	public String getSoluble(){
		return soluble;
	}

	public void setSoluble(String soluble){
		this.soluble=soluble;
	}

	public String getDietaryFiber(){
		return dietaryFiber;
	}

	public void setDietaryFiber(String dietaryFiber){
		this.dietaryFiber=dietaryFiber;
	}

	public String getRetinol(){
		return retinol;
	}

	public void setRetinol(String retinol){
		this.retinol=retinol;
	}

	public String getThiamin(){
		return thiamin;
	}

	public void setThiamin(String thiamin){
		this.thiamin=thiamin;
	}

	public String getRiboflavin(){
		return riboflavin;
	}

	public void setRiboflavin(String riboflavin){
		this.riboflavin=riboflavin;
	}

	public String getNicotinic(){
		return nicotinic;
	}

	public void setNicotinic(String nicotinic){
		this.nicotinic=nicotinic;
	}

	public String getFolate(){
		return folate;
	}

	public void setFolate(String folate){
		this.folate=folate;
	}

	public String getNiacin(){
		return niacin;
	}

	public void setNiacin(String niacin){
		this.niacin=niacin;
	}

	public String getCarotene(){
		return carotene;
	}

	public void setCarotene(String carotene){
		this.carotene=carotene;
	}

	public String getVitA(){
		return vitA;
	}

	public void setVitA(String vitA){
		this.vitA=vitA;
	}

	public String getVitC(){
		return vitC;
	}

	public void setVitC(String vitC){
		this.vitC=vitC;
	}

	public String getVitE(){
		return vitE;
	}

	public void setVitE(String vitE){
		this.vitE=vitE;
	}

	public String getVitE1(){
		return vitE1;
	}

	public void setVitE1(String vitE1){
		this.vitE1=vitE1;
	}

	public String getVitE2(){
		return vitE2;
	}

	public void setVitE2(String vitE2){
		this.vitE2=vitE2;
	}

	public String getVitE3(){
		return vitE3;
	}

	public void setVitE3(String vitE3){
		this.vitE3=vitE3;
	}

	public String getVitB6(){
		return vitB6;
	}

	public void setVitB6(String vitB6){
		this.vitB6=vitB6;
	}

	public String getVitB12(){
		return vitB12;
	}

	public void setVitB12(String vitB12){
		this.vitB12=vitB12;
	}

	public String getAceEleCa(){
		return aceEleCa;
	}

	public void setAceEleCa(String aceEleCa){
		this.aceEleCa=aceEleCa;
	}

	public String getAceEleP(){
		return aceEleP;
	}

	public void setAceEleP(String aceEleP){
		this.aceEleP=aceEleP;
	}

	public String getAceElePCls(){
		return aceElePCls;
	}

	public void setAceElePCls(String aceElePCls){
		this.aceElePCls=aceElePCls;
	}

	public String getProteinDivP(){
		return proteinDivP;
	}

	public void setProteinDivP(String proteinDivP){
		this.proteinDivP=proteinDivP;
	}

	public String getAceEleK(){
		return aceEleK;
	}

	public void setAceEleK(String aceEleK){
		this.aceEleK=aceEleK;
	}

	public String getAceEleKCls(){
		return aceEleKCls;
	}

	public void setAceEleKCls(String aceEleKCls){
		this.aceEleKCls=aceEleKCls;
	}

	public String getAceEleNa(){
		return aceEleNa;
	}

	public void setAceEleNa(String aceEleNa){
		this.aceEleNa=aceEleNa;
	}

	public String getAceEleNaCls(){
		return aceEleNaCls;
	}

	public void setAceEleNaCls(String aceEleNaCls){
		this.aceEleNaCls=aceEleNaCls;
	}

	public String getAceEleMg(){
		return aceEleMg;
	}

	public void setAceEleMg(String aceEleMg){
		this.aceEleMg=aceEleMg;
	}

	public String getAceEleFe(){
		return aceEleFe;
	}

	public void setAceEleFe(String aceEleFe){
		this.aceEleFe=aceEleFe;
	}

	public String getAceEleZn(){
		return aceEleZn;
	}

	public void setAceEleZn(String aceEleZn){
		this.aceEleZn=aceEleZn;
	}

	public String getAceEleSe(){
		return aceEleSe;
	}

	public void setAceEleSe(String aceEleSe){
		this.aceEleSe=aceEleSe;
	}

	public String getAceEleCu(){
		return aceEleCu;
	}

	public void setAceEleCu(String aceEleCu){
		this.aceEleCu=aceEleCu;
	}

	public String getAceEleMn(){
		return aceEleMn;
	}

	public void setAceEleMn(String aceEleMn){
		this.aceEleMn=aceEleMn;
	}

	public String getAceEleI(){
		return aceEleI;
	}

	public void setAceEleI(String aceEleI){
		this.aceEleI=aceEleI;
	}

	public String getVersion(){
		return version;
	}

	public void setVersion(String version){
		this.version=version;
	}

	public String getRemark(){
		return remark;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getIntake(){
		return intake;
	}

	public void setIntake(String intake){
		this.intake=intake;
	}

	public String getCkd(){
		return ckd;
	}

	public void setCkd(String ckd){
		this.ckd=ckd;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}
}

