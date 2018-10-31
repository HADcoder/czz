package com.blackchicktech.healthdiet.service;

import com.blackchicktech.healthdiet.domain.FoodListItem;
import com.blackchicktech.healthdiet.domain.MainIngredient;
import com.blackchicktech.healthdiet.domain.RecipeListItem;
import com.blackchicktech.healthdiet.entity.Recipe;
import com.blackchicktech.healthdiet.entity.RecipeWeight;
import com.blackchicktech.healthdiet.entity.User;
import com.blackchicktech.healthdiet.repository.FoodDaoImpl;
import com.blackchicktech.healthdiet.repository.RecipeDaoImpl;
import com.blackchicktech.healthdiet.repository.RecipeWeightDaoImpl;
import com.blackchicktech.healthdiet.repository.UserDaoImpl;
import com.blackchicktech.healthdiet.util.Constants;
import com.blackchicktech.healthdiet.util.IngredientHelper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//食谱
@Service
public class RecipeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

	@Autowired
	private RecipeDaoImpl recipeDao;

	@Autowired
	private FoodDaoImpl foodDao;

	@Autowired
	private PreferenceService preferenceService;

	@Autowired
	private UserDaoImpl userDao;

	@Autowired
	private RecipeWeightDaoImpl recipeWeightDao;


	public List<String> getCategoryList() {
		return recipeDao.getAllCategory();
	}

	/**
	 *
	 * @return List<String>
	 */
	public List<String> getMealTimeList() {
		return recipeDao.getAllMealTime();
	}

	/**
	 *
	 * @param category 荤素分类
	 * @return List<Recipe> 菜品集合
	 */
	public List<Recipe> getRecipeListByCategroy(String category) {
		return recipeDao.getRecipeByCategory(category);
	}

	/**
	 *
	 * @param mealTime 用餐时间
	 * @return List<Recipe> 菜品集合
	 */
	public List<Recipe> getRecipeListByMealTime(String mealTime) {
		return recipeDao.getRecipeByMealTime(mealTime);
	}

	/**
	 *
	 * @param recipeId 菜品ID
	 * @return 菜品ID对应菜品
	 */
	public Recipe getRecipeById(String recipeId) {
		return recipeDao.getRecipeById(recipeId);
	}

	public List<Recipe> getRecommendRecipe(String foodName) {
		return recipeDao.getRecommendRecipe(foodName);
	}

	public List<RecipeListItem> getRecommendRecipeList(String foodName) {
		List<RecipeListItem> result = new ArrayList<>();
		String[] aliasArr = foodName.substring(1, foodName.length() - 1).split("\\|");
		for (String alias : aliasArr) {
			result.addAll(recipeDao.getRecommendRecipe(alias).stream().map(RecipeListItem::new).collect(Collectors.toList()));
		}
		return result;
	}

	/**
	 *
	 * @param name 菜名关键字
	 * @return List<Recipe>  菜品集合
	 */
	public List<Recipe> getByName(String name) {
		if (StringUtils.isBlank(name)) {
			return Lists.newArrayList();
		}
		return recipeDao.getRecipeByName(name);
	}

	public List<MainIngredient> getMappedMainIngredients(String mainIngredients) {
		List<MainIngredient> list = IngredientHelper.parse(mainIngredients);
		for (MainIngredient item : list) {
			FoodListItem food = foodDao.getFoodByAlias(item.getFoodName());
			if (food != null) {
				item.setFoodId(food.getFoodId());
			}
		}
		return list;
	}

	/**
	 *
	 * @param recipe 根据菜品id获得 菜品
	 * @param openId 用户唯一id
	 * @return 根据菜品和和用户身体情况推送一条营养师对该菜品的建议
	 */
	public String deduceDieticianAdvice(Recipe recipe, String openId) {
		User user = userDao.getUserByOpenId(openId);
		//TODO: user empty validation
		if (user == null) {
			return "";
		}
		//根据菜品id得到对应菜品的成分含量-RecipeWeight   RecipeWeight中包含营养成分的 低中高值
		RecipeWeight recipeWeight = recipeWeightDao.getRecipeWeightByRecipeId(recipe.getRecipeId());

		//用户肾病对应期数
		int nephroticPeriod = Integer.valueOf(user.getNephroticPeriod());
		//用户的肾病并发症
		String otherDiseases = user.getOtherDiseases();

		//获得菜品的蛋白含量
		int proteinWeight = recipeWeight.getProteinWeight();

		//菜品食材
		String material = recipe.getMaterial().split("|")[0];

		//营养师建议
		StringBuilder dieticianAdvice = new StringBuilder();
		//用于下面拼接用户所有并发症的敏感信息,例  如果有hypertension则拼接 血压
		StringBuilder otherDiseasesConbinations = new StringBuilder();

		//用于存放用户所有并发症的敏感食物成分 例  如果有hypertension 则存入 na_weight
		List<String> otherDiseaseFoodWeightFields = new ArrayList<>();

		//如果用户有肾病并发症
		if (otherDiseases != null && !StringUtils.isEmpty(otherDiseases)) {
			String[] otherDiseasesArray = otherDiseases.split(",");
			LOGGER.info("Other Diseases: \n");
			Arrays.stream(otherDiseasesArray).forEach(LOGGER::info);
			List<String> otherDiseasesList = Arrays.asList(otherDiseasesArray);

			//菜品成分含量 低/中/高 的三个集合
			List<String> lowWeight = new ArrayList<>();
			List<String> mediumWeight = new ArrayList<>();
			List<String> highWeight = new ArrayList<>();

			//根据并发症的不同,从菜品中获得该并发症对应的敏感食物 存入对应的低/中/高集合中
			deduceWeight(lowWeight, mediumWeight, highWeight, recipeWeight, otherDiseasesList);

			//根据用户的肾病并发症,把对应并发症的敏感食物成分如na_weidht 存入 otherDiseaseFoodWeightFields
			for (int i = 0; i < otherDiseasesArray.length; i++) {
				otherDiseaseFoodWeightFields.add(Constants.WEIGHT_FILED_DISEASE_MAP.get(otherDiseasesArray[i]));
				if (i != otherDiseasesArray.length - 1) {
					//拼接用户所有并发症的敏感信息,例  如果有hypertension则拼接 血压
					otherDiseasesConbinations.append(Constants.OTHER_DISEASE_ELEMENTS.get(otherDiseasesArray[i]))
							.append(",");
				} else {
					otherDiseasesConbinations.append(Constants.OTHER_DISEASE_ELEMENTS.get(otherDiseasesArray[i]));
				}

			}

			//营养师建议拼接
			dieticianAdvice.append(String.format(Constants.DIETICIAN_ADVICE_TEMPLATE,
					nephroticPeriod, otherDiseasesConbinations));
			dieticianAdvice.append("该食谱");
			if (!lowWeight.isEmpty()) {
				for (int i = 0; i < lowWeight.size(); i++) {
					if (i != lowWeight.size() - 1) {
						dieticianAdvice.append(lowWeight.get(i)).append("、");
					} else {
						dieticianAdvice.append(lowWeight.get(i)).append("含量低, ");
					}
				}
			}
			if (!mediumWeight.isEmpty()) {
				for (int i = 0; i < mediumWeight.size(); i++) {
					if (i != mediumWeight.size() - 1) {
						dieticianAdvice.append(mediumWeight.get(i)).append("、");
					} else {
						dieticianAdvice.append(mediumWeight.get(i)).append("含量适中, ");
					}
				}
			}
			if (!highWeight.isEmpty()) {
				if (!lowWeight.isEmpty() || !mediumWeight.isEmpty()) {
					dieticianAdvice.append("但");
				}
				for (int i = 0; i < highWeight.size(); i++) {
					if (i != highWeight.size() - 1) {
						dieticianAdvice.append(highWeight.get(i)).append("、");
					} else {
						dieticianAdvice.append(highWeight.get(i)).append("含量偏高, ");
					}
				}
			}

			//
			int maxWeight = getMaxWeight(recipeWeight, otherDiseasesList);

			if (maxWeight == 1) {
				dieticianAdvice.append("可经常食用。");
			} else if (maxWeight == 2) {
				dieticianAdvice.append("可适量食用。");
			} else {
				dieticianAdvice.append("不适宜您食用。 \n");
				String recommendRecipes = deduceRecipeForMultiDisease(otherDiseaseFoodWeightFields, material);
				if (recommendRecipes != null) {
					dieticianAdvice.append("以下食谱更适合您: ").append(recommendRecipes);
				}
			}

		} else {
			dieticianAdvice.append(String.format(Constants.DIETICIAN_ADVICE_WITHOUT_NEOPATHY_TEMPLATE, nephroticPeriod));
			if (proteinWeight == 1) {
				dieticianAdvice.append("该食谱蛋白含量低，可经常食用。");
			} else if (proteinWeight == 2) {
				dieticianAdvice.append("该食谱蛋白含量适中，可适量食用。");
			} else {
				dieticianAdvice.append("该食谱蛋白含量偏高，不适宜您食用。\n");
				String recommendFood = deduceRecommendRecipe(material);
				if (recommendFood != null) {
					dieticianAdvice.append("以下食谱更适合您: ").append(recommendFood);
				}
			}
		}
		return dieticianAdvice.toString();


	}

	/**
	 *  根据并发症的不同,从菜品中获得该并发症对应的敏感食物 存入对应的低/中/高集合中
	 * @param lowWeight 低成分集合
	 * @param mediumWeight 中成分集合
	 * @param highWeight 高成分集合
	 * @param recipeWeight RecipeWeight中包含营养成分的 低中高值
	 * @param otherDiseaseList 肾病并发症集合
	 */
	private void deduceWeight(List<String> lowWeight, List<String> mediumWeight,
							  List<String> highWeight, RecipeWeight recipeWeight, List<String> otherDiseaseList) {

		int proteinWeight = recipeWeight.getProteinWeight();
		//根据菜品中营养成分的不同,分别存入 营养成分低/中/高集合  line --- 299
		deduceWeight(lowWeight, mediumWeight, highWeight, proteinWeight, "蛋白质");
		if (otherDiseaseList.contains("hyperuricacidemia")) {
			int purineWeight = recipeWeight.getPurineWeight();
			deduceWeight(lowWeight, mediumWeight, highWeight, purineWeight, "嘌呤");
		}
		if (otherDiseaseList.contains("cholesterol")) {
			int cholesterolWeight = recipeWeight.getCholesterolWeight();
			deduceWeight(lowWeight, mediumWeight, highWeight, cholesterolWeight, "胆固醇");
		}
		if (otherDiseaseList.contains("hypertension")) {
			int naWeight = recipeWeight.getNaWeight();
			deduceWeight(lowWeight, mediumWeight, highWeight, naWeight, "钠");
		}
		if (otherDiseaseList.contains("triglyceride")) {
			int fatWeight = recipeWeight.getFatWeight();
			deduceWeight(lowWeight, mediumWeight, highWeight, fatWeight, "脂肪");
		}
		if (otherDiseaseList.contains("hyperglycemia")) {
			int choWeight = recipeWeight.getChoWeight();
			deduceWeight(lowWeight, mediumWeight, highWeight, choWeight, "碳水化合物");
		}
	}

	/**
	 * 根据菜品中营养成分的不同,分别存入 营养成分低/中/高集合
	 * @param lowWeight
	 * @param mediumWeight
	 * @param highWeight
	 * @param foodWeight
	 * @param element
	 */
	private void deduceWeight(List<String> lowWeight, List<String> mediumWeight,
							  List<String> highWeight, int foodWeight, String element) {
		if (foodWeight == 1) {
			lowWeight.add(element);
		} else if (foodWeight == 2) {
			mediumWeight.add(element);
		} else {
			highWeight.add(element);
		}
	}

	//List<Integer> weights -- 用于存放并发症敏感的食物成分(对应菜品中), 最终返回菜品中含量最高的成分值 1低/2中/3高
	private int getMaxWeight(RecipeWeight recipeWeight, List<String> otherDiseases) {
		List<Integer> weights = new ArrayList<>();
		weights.add(recipeWeight.getProteinWeight());
		if (otherDiseases.contains("hyperuricacidemia")) {
			weights.add(recipeWeight.getPurineWeight());
		}
		if (otherDiseases.contains("cholesterol")) {
			weights.add(recipeWeight.getCholesterolWeight());
		}
		if (otherDiseases.contains("hypertension")) {
			weights.add(recipeWeight.getNaWeight());
		}
		if (otherDiseases.contains("triglyceride")) {
			weights.add(recipeWeight.getFatWeight());
		}

		if (otherDiseases.contains("hyperglycemia")) {
			weights.add(recipeWeight.getChoWeight());
		}

		return Collections.max(weights);
	}

	private String deduceRecipeForMultiDisease(List<String> multiWeightFields, String material) {
		List<RecipeWeight> recipeWeights = recipeWeightDao.getRecipeWeightByMultiWeightFieldsAndMaterial(multiWeightFields, material);

		if (recipeWeights == null || recipeWeights.size() == 0) {
			return null;
		}

		StringBuffer recipes = new StringBuffer();

		for (int i = 0; i < recipeWeights.size(); i++) {
			String recipeId = recipeWeights.get(i).getRecipeId();
			Recipe recipe = recipeDao.getRecipeById(recipeId);
			if (i != recipeWeights.size() - 1) {
				recipes.append(recipe.getRecipeName()).append("、");
			} else {
				recipes.append(recipe.getRecipeName()).append("。");
			}
		}
		return recipes.toString();
	}

	private String deduceRecommendRecipe(String material) {
		List<RecipeWeight> recipeWeights = recipeWeightDao.getRecipeWeightByProteinWeightAndMaterial(3, material);
		if (recipeWeights == null && recipeWeights.size() == 0) {
			return null;
		}

		List<Recipe> recipes = new ArrayList<>();
		for (RecipeWeight recipeWeight : recipeWeights) {
			Recipe recipe = recipeDao.getRecipeById(recipeWeight.getRecipeId());
			recipes.add(recipe);
		}
		StringBuffer dieticianAdvice = new StringBuffer();
		for (int i = 0; i < recipes.size(); i++) {
			dieticianAdvice.append(recipes.get(i).getRecipeName());
			if (i != recipes.size() - 1) {
				dieticianAdvice.append("、");
			} else {
				dieticianAdvice.append("。");
			}
		}
		return dieticianAdvice.toString();
	}
}
