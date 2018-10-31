package com.blackchicktech.healthdiet.repository;

import com.blackchicktech.healthdiet.entity.RecipeWeight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeWeightDaoImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeWeightDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<RecipeWeight> rowMapper = new BeanPropertyRowMapper<>(RecipeWeight.class);

	/**
	 *
	 * @param recipeId 菜品id
	 * @return RecipeWeight 菜品对应的成分含量如protein_weight,fat_weight......(1低/2中/3高)
	 */
	public RecipeWeight getRecipeWeightByRecipeId(String recipeId) {
		List<RecipeWeight> recipeWeightList = jdbcTemplate.query("SELECT * FROM recipe_weight_tbl WHERE recipe_id = ?", rowMapper, recipeId);
		return recipeWeightList.stream().findFirst().orElse(null);
	}

	public List<RecipeWeight> getRecipeWeightByMultiWeightFieldsAndMaterial(List<String> multiWeightFields, String material) {
		LOGGER.debug("Getting RecipeWeight by multi weight fields:" + multiWeightFields);
		StringBuilder sqlSegment = new StringBuilder();
		for (String multiWeightField : multiWeightFields) {
			sqlSegment.append(multiWeightField).append(" < 3 and ");
		}
		List<RecipeWeight> result;
		String sqlWithMaterial = "SELECT * from recipe_weight_tbl where protein_weight < 3 and " +
				sqlSegment + " material like '%" + material + "%' order by rand() limit 3";
		LOGGER.debug("SQLWithFoodCodeAndSubCode: " + sqlWithMaterial);
		result = jdbcTemplate.query(sqlWithMaterial, rowMapper);
		return result;
	}

	public List<RecipeWeight> getRecipeWeightByProteinWeightAndMaterial(int proteinWeight, String material) {
		List<RecipeWeight> result;
		result = jdbcTemplate.query("SELECT * FROM recipe_weight_tbl WHERE " +
						" protein_weight < ? AND material LIKE ? ORDER BY rand() LIMIT 3",
				rowMapper, proteinWeight, "%" + material + "%");

		return result;
	}
}
