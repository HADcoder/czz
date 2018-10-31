package com.blackchicktech.healthdiet.util;

import com.blackchicktech.healthdiet.domain.OtherDiseaseSuggestDiet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Eric Cen on 2018/8/6.
 */
public class Constants {

	public final static String ADVICE_TEMPLATE = "您的肾脏功能属于第%s期，需要控制蛋白质摄入以延缓肾脏功能的进一步恶化。同时，您的%s偏高，建议%s饮食。";
	public final static String WITHOUT_NEOPATHY_ADVICE_TEMPLATE = "您的肾脏功能属于第%s期，需要控制蛋白质摄入以延缓肾脏功能的进一步恶化。";


	public final static Map<String, OtherDiseaseSuggestDiet> SUGGESTED_DIET = ImmutableMap.<String, OtherDiseaseSuggestDiet>builder()
			.put("hyperuricacidemia", new OtherDiseaseSuggestDiet("高尿酸", "尿酸", "低嘌呤"))
			.put("hyperglycemia", new OtherDiseaseSuggestDiet("高血糖", "血糖", "低糖"))
			.put("cholesterol", new OtherDiseaseSuggestDiet("高胆固醇", "胆固醇", "低胆固醇"))
			.put("triglyceride", new OtherDiseaseSuggestDiet("高甘油三酯", "甘油三酯", "低脂肪"))
			.put("hypertension", new OtherDiseaseSuggestDiet("高血压", "血压", "低钠"))
			.build();

	public final static String[] SLOGAN = {"你知道怎么吃得饱又吃得好吗？", "没有垃圾食品，只有垃圾食法！"};

	public final static Map<String, String> GENDER = ImmutableMap.of(
			"male", "男",
			"female", "女");

	public final static Map<String, String> TREATMENT_METHOD = ImmutableMap.<String, String>builder()
			.put("medication", "药物治疗")
			.put("hemodialysis", "血液透析治疗")
			.put("peritoneal-dialysis", "腹膜透析治疗")
			.put("transplantation", "肾脏移植").build();

	public final static Map<String, String> SPORT_RATE = ImmutableMap.<String, String>builder()
			.put("light", "轻度（如：长期坐办公室）")
			.put("medium", "中度（如：不时外出跑业务）")
			.put("severe", "重度（如：搬运）").build();

	public final static Map<String, String> OTHER_DISEASE = ImmutableMap.<String, String>builder()
			.put("hypertension", "高血压")
			.put("triglyceride", "高甘油三酯")
			.put("cholesterol", "高胆固醇")
			.put("hyperglycemia", "高血糖")
			.put("hyperuricacidemia", "高尿酸").build();

	public final static Map<String, String> OTHER_DISEASE_ELEMENTS = ImmutableMap.<String, String>builder()
			.put("hypertension", "血压")
			.put("triglyceride", "甘油三酯")
			.put("cholesterol", "胆固醇")
			.put("hyperglycemia", "血糖")
			.put("hyperuricacidemia", "尿酸").build();


	public final static Map<String, String> IRRITABILITY = ImmutableMap.<String, String>builder()
			.put("milk", "奶")
			.put("egg", "蛋")
			.put("crostacei", "贝壳")
			.put("fish-prawn-crab", "鱼虾蟹")
			.put("flour", "面粉")
			.put("nuts", "坚果")
			.put("soya", "黄豆")
			.put("corn", "玉米")
			.build();

	public final static Map<Integer, String> FAT_WEIGHT_ADVICE = ImmutableMap.<Integer, String>builder()
			.put(1, "脂肪含量低,")
			.put(2, "脂肪含量适宜,")
			.put(3, "脂肪含量高,")
			.build();

	public final static Map<Integer, String> PROTEIN_WEIGHT_ADVICE = ImmutableMap.<Integer, String>builder()
			.put(1, "蛋白质含量低,")
			.put(2, "蛋白质含量适宜,")
			.put(3, "蛋白质含量高,")
			.build();

	public final static Map<Integer, String> NA_WEIGHT_ADVICE = ImmutableMap.<Integer, String>builder()
			.put(1, "钠含量低,")
			.put(2, "钠含量适宜,")
			.put(3, "钠含量高,")
			.build();

	public final static Map<Integer, String> CHO_WEIGHT_ADVICE = ImmutableMap.<Integer, String>builder()
			.put(1, "血糖含量低,")
			.put(2, "血糖含量适宜,")
			.put(3, "血糖含量高,")
			.build();

	public final static Map<Integer, String> CHOLESTEROL_WEIGHT_ADVICE = ImmutableMap.<Integer, String>builder()
			.put(1, "胆固醇含量低,")
			.put(2, "胆固醇含量适宜,")
			.put(3, "胆固醇含量高,")
			.build();

	public final static Map<Integer, String> PURINE_WEIGHT_ADVICE = ImmutableMap.<Integer, String>builder()
			.put(1, "嘌呤含量低,")
			.put(2, "嘌呤含量适宜,")
			.put(3, "嘌呤含量高,")
			.build();

	public final static Map<String, String> WEIGHT_FILED_DISEASE_MAP = ImmutableMap.<String, String>builder()
			.put("hypertension","na_weight")
			.put("triglyceride", "fat_weight")
			.put("cholesterol", "cholesterol_weight")
			.put("hyperglycemia","cho_weight")
			.put("hyperuricacidemia", "purine_weight")
			.build();


	public final static String DIETICIAN_ADVICE_TEMPLATE = "您的肾脏功能属于第%s期，而且%s偏高，";
	public final static String DIETICIAN_ADVICE_WITHOUT_NEOPATHY_TEMPLATE = "您的肾脏功能属于第%s期，";

	public final static String[] CKD_FOOD = {"谷薯类", "淀粉", "绿叶蔬菜", "瓜果蔬菜", "奶类", "肉蛋类", "油脂类"};
	public final static String[] CKD_FOOD_WITHOUT_MILK = {"谷薯类", "淀粉", "绿叶蔬菜", "瓜果蔬菜", "肉蛋类", "油脂类"};
	public final static Map<String, Integer> CKD_FOOD_SPEC_BASE = ImmutableMap.<String, Integer>builder()
			.put("谷薯类",50)
			.put("淀粉", 100)
			.put("绿叶蔬菜", 250)
			.put("瓜果蔬菜", 200)
			.put("奶类", 230)
			.put("肉蛋类", 55)
			.put("油脂类", 10)
			.build();
	public final static Map<String, Integer> CKD_FOOD_WITHOUT_MILK_SPEC_BASE = ImmutableMap.<String, Integer>builder()
			.put("谷薯类",50)
			.put("淀粉", 100)
			.put("绿叶蔬菜", 250)
			.put("瓜果蔬菜", 200)
			.put("肉蛋类", 55)
			.put("油脂类", 10)
			.build();

	public final static double[] CKD_FOOD_40_6 = {1.5,1.5,1.0,1.0,1.0,1.0,4.0};
	public final static double[] CKD_FOOD_45_6 = {1.5,1.5,1.0,1.0,1.0,1.5,4.5};
	public final static double[] CKD_FOOD_50_6 = {2.0,2.0,1.0,2.0,1.0,1.5,3.0};
	public final static double[] CKD_FOOD_55_6 = {2.0,2.5,1.0,2.0,1.0,2.0,3.5};
	public final static double[] CKD_FOOD_60_6 = {2.5,2.5,1.0,2.0,1.0,2.0,4.0};
	public final static double[] CKD_FOOD_65_6 = {3.0,2.5,1.0,2.0,1.0,2.0,4.5};
	public final static double[] CKD_FOOD_70_6 = {3.0,2.5,1.0,2.0,1.0,2.5,5.0};
	public final static double[] CKD_FOOD_75_6 = {3.0,3.0,1.0,2.0,1.0,3.0,5.5};

	public final static double[] CKD_FOOD_WITHOUTMILK_40_6 = {1.5,1.5,1.0,1.0,1.64,4.0};
	public final static double[] CKD_FOOD_WITHOUTMILK_45_6 = {1.5,1.5,1.0,1.0,2.14,4.5};
	public final static double[] CKD_FOOD_WITHOUTMILK_50_6 = {2.0,2.0,1.0,2.0,2.14,3.0};
	public final static double[] CKD_FOOD_WITHOUTMILK_55_6 = {2.0,2.5,1.0,2.0,2.64,3.5};
	public final static double[] CKD_FOOD_WITHOUTMILK_60_6 = {2.5,2.5,1.0,2.0,2.64,4.0};
	public final static double[] CKD_FOOD_WITHOUTMILK_65_6 = {3.0,2.5,1.0,2.0,2.64,4.5};
	public final static double[] CKD_FOOD_WITHOUTMILK_70_6 = {3.0,2.5,1.0,2.0,3.14,5.0};
	public final static double[] CKD_FOOD_WITHOUTMILK_75_6 = {3.0,3.0,1.0,2.0,3.64,5.5};

	public final static double[] CKD_FOOD_40_8 = {2,0.5,1.0,1.0,1.0,2.0,3.5};
	public final static double[] CKD_FOOD_45_8 = {2.5,0.5,1.0,1.0,1.0,2.0,4.0};
	public final static double[] CKD_FOOD_50_8 = {3.0,0.5,1.0,1.0,1.0,2.5,4.5};
	public final static double[] CKD_FOOD_55_8 = {3.5,0.5,1.0,2.0,1.0,2.5,5.0};
	public final static double[] CKD_FOOD_60_8 = {4.0,0.5,1.0,2.0,1.0,3.0,5.0};
	public final static double[] CKD_FOOD_65_8 = {4.0,0.5,1.0,2.0,1.0,3.5,5.5};
	public final static double[] CKD_FOOD_70_8 = {4.5,0.75,1.0,2.0,1.0,4.0,5.5};
	public final static double[] CKD_FOOD_75_8 = {5.0,0.75,1.0,2.0,1.0,4.0,6};

	public final static double[] CKD_FOOD_WITHOUTMILK_40_8 = {2,0.5,1.0,1.0,2.64,3.5};
	public final static double[] CKD_FOOD_WITHOUTMILK_45_8 = {2.5,0.5,1.0,1.0,2.64,4.0};
	public final static double[] CKD_FOOD_WITHOUTMILK_50_8 = {3.0,0.5,1.0,2.0,3.14,4.5};
	public final static double[] CKD_FOOD_WITHOUTMILK_55_8 = {3.5,0.5,1.0,2.0,3.14,5.0};
	public final static double[] CKD_FOOD_WITHOUTMILK_60_8 = {4.0,0.5,1.0,2.0,3.64,5.0};
	public final static double[] CKD_FOOD_WITHOUTMILK_65_8 = {4.0,0.5,1.0,2.0,4.14,5.5};
	public final static double[] CKD_FOOD_WITHOUTMILK_70_8 = {4.5,0.75,1.0,2.0,4.64,5.5};
	public final static double[] CKD_FOOD_WITHOUTMILK_75_8 = {5.0,0.75,1.0,2.0,4.64,6};

	public final static Map<String, Set<String>> CKD_FOOD_CATAGARIES = ImmutableMap.<String, Set<String>>builder()
																	   .put("Grains", ImmutableSet.<String>builder().add("A1").add("A2").build())
																	   .put("Starch", ImmutableSet.<String>builder().add("A3").build())
			                                                           .put("Wege", ImmutableSet.<String>builder().add("B").build())
																	   .put("WegeFruit", ImmutableSet.<String>builder().add("C1").build())
			                                                           .put("Milk", ImmutableSet.<String>builder().add("D").build())
                                                                       .put("Eggs", ImmutableSet.<String>builder().add("E1").add("E2").add("E3").add("E4").build())
																	   .put("Fat", ImmutableSet.<String>builder().add("F").build())
			                                                           .put("Fruit", ImmutableSet.<String>builder().add("C2").build())
			                                                           .build();


	//高血压烹饪
	public final static List<String> COOKING_FILTER_HYPERTENSION = ImmutableList.<String>builder()
																	.add("熏")
																	.add("卤")
																	.add("干煸")
																	.add("干锅")
																	.add("炸")
																	.add("腌")
																	.add("腊")
																	.build();
	//高血脂烹饪
	public final static List<String> COOKING_FILTER_TRIGLYCERIDE = ImmutableList.<String>builder()
																	.add("干煸")
																	.add("干锅")
																	.add("炸")
																	.add("煎")
																	.add("熏")
																	.add("腌")
																	.add("腊").build();

	//高胆固醇烹饪
	public final static List<String> COOKING_FILTER_CHOLESTEROL = ImmutableList.<String>builder()
			.add("卤")
			.add("腌")
			.add("酱")
			.build();

	//高血糖烹饪
	public final static List<String> COOKING_FILTER_HYPERGLYCEMIA = ImmutableList.<String>builder()
			.add("干煸")
			.add("干锅")
			.add("拔丝")
			.add("炸")
			.add("糖蘸")
			.add("熏")
			.add("腌")
			.add("腊")
			.build();

	//高尿酸烹饪
	public final static List<String> COOKING_FILTER_HYPERURICACIDEMIA = ImmutableList.<String>builder()
			.add("炖")
			.add("炸")
			.add("煨")
			.add("熏")
			.add("腌")
			.add("腊")
			.build();

	public final static Map<String, List<String>> COOKING_FILETER = ImmutableMap.<String, List<String>>builder()
			.put("hypertension", COOKING_FILTER_HYPERTENSION)
			.put("triglyceride", COOKING_FILTER_TRIGLYCERIDE)
			.put("cholesterol", COOKING_FILTER_CHOLESTEROL)
			.put("hyperglycemia", COOKING_FILTER_HYPERGLYCEMIA)
			.put("hyperuricacidemia", COOKING_FILTER_HYPERURICACIDEMIA).build();


}
