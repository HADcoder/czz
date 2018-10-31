package com.blackchicktech.healthdiet.repository;

import com.blackchicktech.healthdiet.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class RecipeDaoImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Recipe> entityRowMapper = new BeanPropertyRowMapper<>(Recipe.class);

	private RowMapper<com.blackchicktech.healthdiet.domain.Recipe> domainRowMapper = new BeanPropertyRowMapper<>(com.blackchicktech.healthdiet.domain.Recipe.class);

	/**
	 *
	 * @param recipeId 菜品ID
	 * @return 菜品ID对应菜品
	 */
	public Recipe getRecipeById(String recipeId) {
		List<Recipe> foodList = jdbcTemplate.query("SELECT * FROM recipe_tbl WHERE recipe_id = ?",
				entityRowMapper, recipeId);
		return foodList.stream().findFirst().orElse(null);
	}

	public List<String> getAllMealTime() {
		return Arrays.asList("早餐", "午餐", "晚餐", "加餐");
	}

	public List<String> getAllCategory() {
		return jdbcTemplate.queryForList("SELECT DISTINCT category FROM recipe_tbl", String.class);
	}

	/**
	 *
	 * @param recipeName 菜名关键字
	 * @return List<Recipe> 菜品集合
	 */
	public List<Recipe> getRecipeByName(String recipeName) {
		return jdbcTemplate.query("SELECT * FROM recipe_tbl WHERE recipe_name LIKE ?",
				entityRowMapper, "%" + recipeName + "%");
	}

	/**
	 *
	 * @param mealTime 用餐时间
	 * @return List<Recipe> 菜品集合
	 */
	public List<Recipe> getRecipeByMealTime(String mealTime) {
		return jdbcTemplate.query("SELECT * FROM recipe_tbl WHERE meal_time LIKE ?",
				entityRowMapper, "%" + mealTime + "%");
	}

	/**
	 *
	 * @param category 菜品分类
	 * @return List<Recipe> 菜品集合
	 */
	public List<Recipe> getRecipeByCategory(String category) {
		return jdbcTemplate.query("SELECT * FROM recipe_tbl WHERE category = ?",
				entityRowMapper, category);
	}

	public List<Recipe> getRecommendRecipe(String foodName) {
		return jdbcTemplate.query("SELECT * FROM recipe_tbl WHERE material LIKE ? ORDER BY RAND() LIMIT 3;",
				entityRowMapper, "%|" + foodName + "|%");
	}

	public List<Recipe> getRecipeByCookMethod(String cookMethod) {
		return jdbcTemplate.query("SELECT * FROM recipe_tbl WHERE cook_method = ?",
				entityRowMapper, cookMethod);
	}

	public com.blackchicktech.healthdiet.domain.Recipe getRecipeByCkdCategory(String ckdCategory, List<String> recipeWeights, List<String> recipeCookingMethods, boolean isBreakfast) {
		StringBuffer recipeWeightSqlSegment = new StringBuffer();
		for(String recipeWeight : recipeWeights){
			recipeWeightSqlSegment.append("recipe_weight.").append(recipeWeight).append(" < 3 and ");
		}
		StringBuffer filteredCookingMethod = new StringBuffer();
		if(recipeCookingMethods != null){
			for(int i = 0 ; i < recipeCookingMethods.size(); i++){
				if(i == recipeCookingMethods.size() - 1){
					filteredCookingMethod.append("'").append(recipeCookingMethods.get(i)).append("'");
				} else {
					filteredCookingMethod.append("'").append(recipeCookingMethods.get(i)).append("'").append(",");
				}

			}

			String querySql;
			if(isBreakfast){
				 querySql = "SELECT recipe.material, recipe.recipe_id, recipe.recipe_name, recipe.meal_time " +
						 "from recipe_tbl recipe," +
						" recipe_weight_tbl recipe_weight where recipe.ckd_category = ? " +
						 "and recipe.meal_time like '%早餐%' and recipe.recipe_id = recipe_weight.recipe_id " +
						 "and " + recipeWeightSqlSegment +
						" recipe.cook_method not in (" + filteredCookingMethod + ") order by rand() limit 1";
			} else {
				 querySql = "SELECT recipe.material, recipe.recipe_id, recipe.recipe_name, recipe.meal_time" +
						 " from recipe_tbl recipe," +
						" recipe_weight_tbl recipe_weight where recipe.ckd_category = ? and recipe.meal_time != '早餐'" +
						 "and recipe.recipe_id = recipe_weight.recipe_id and "
						 + recipeWeightSqlSegment +
						" recipe.cook_method not in (" + filteredCookingMethod + ") order by rand() limit 1";
			}

			List<com.blackchicktech.healthdiet.domain.Recipe> recipes = jdbcTemplate.query(querySql,
					domainRowMapper, ckdCategory);
			return recipes.isEmpty()?null:recipes.get(0);

		} else {
			String querySql;
			if(isBreakfast){
				querySql = "SELECT recipe.material, recipe.recipe_id, recipe.recipe_name, recipe.meal_time FROM " +
						"recipe_tbl recipe, recipe_weight_tbl recipe_weight WHERE recipe.ckd_category = ? and " +
						"recipe.meal_time like '%早餐%' and " +
						recipeWeightSqlSegment +"recipe.recipe_id = recipe_weight.recipe_id " +
						" order by rand() limit 1";
			} else {
				querySql = "SELECT recipe.material, recipe.recipe_id, recipe.recipe_name, recipe.meal_time FROM " +
						"recipe_tbl recipe, recipe_weight_tbl recipe_weight WHERE recipe.ckd_category = ? and recipe.meal_time != '早餐'and " +
						recipeWeightSqlSegment +"recipe.recipe_id = recipe_weight.recipe_id " +
						" order by rand() limit 1";
			}
			List<com.blackchicktech.healthdiet.domain.Recipe> recipes = jdbcTemplate.query(querySql,
					domainRowMapper, ckdCategory);
			return recipes.isEmpty()?null:recipes.get(0);
		}

	}

	public com.blackchicktech.healthdiet.domain.Recipe getMeatRecipeByCkdCategory(String ckdCategory,List<String> recipeWeights, List<String> recipeCookingMethods){
		StringBuffer recipeWeightSqlSegment = new StringBuffer();
		for(String recipeWeight : recipeWeights){
			recipeWeightSqlSegment.append("recipe_weight.").append(recipeWeight).append(" < 3 and ");
		}
		StringBuffer filteredCookingMethod = new StringBuffer();
		if(recipeCookingMethods != null){
			for(int i = 0 ; i < recipeCookingMethods.size(); i++){
				if(i == recipeCookingMethods.size() - 1){
					filteredCookingMethod.append("'").append(recipeCookingMethods.get(i)).append("'");
				} else {
					filteredCookingMethod.append("'").append(recipeCookingMethods.get(i)).append("'").append(",");
				}

			}
			String querySql = "SELECT recipe.material, recipe.recipe_id, recipe.recipe_name, recipe.meal_time " +
					"from recipe_tbl recipe," +
					" recipe_weight_tbl recipe_weight where recipe.ckd_category like ? and recipe.meal_time != '早餐' " +
					"and recipe.recipe_id = recipe_weight.recipe_id and " + recipeWeightSqlSegment +
					" recipe.cook_method not in (" + filteredCookingMethod + ") order by rand() limit 1";
			List<com.blackchicktech.healthdiet.domain.Recipe> recipes = jdbcTemplate.query(querySql,
					domainRowMapper, "%"+ckdCategory + "%");
			return recipes.isEmpty()?null:recipes.get(0);

		} else {
			List<com.blackchicktech.healthdiet.domain.Recipe> recipes = jdbcTemplate.query("SELECT recipe.material," +
							" recipe.recipe_id, " +
							"recipe.recipe_name, recipe.meal_time FROM recipe_tbl recipe, " +
							"recipe_weight_tbl recipe_weight WHERE ckd_category like ? and recipe.meal_time != '早餐'and " +
							recipeWeightSqlSegment +"recipe.recipe_id = recipe_weight.recipe_id " +
							" order by rand() limit 1",
					domainRowMapper, "%"+ckdCategory + "%");
			return recipes.isEmpty()?null:recipes.get(0);
		}

	}

}
