package com.blackchicktech.healthdiet.repository;

import com.blackchicktech.healthdiet.entity.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PreferenceDaoImpl extends AbstractDao<Preference> {

	@Override
	public Preference entitySupplier() {
		return new Preference();
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Preference> listByUserId(String openId, String type) {
		return jdbcTemplate.query("SELECT * FROM user_preference WHERE user_open_id = ? AND type=?", rowMapper, openId, type);
	}

	public Preference getPreference(String openId, String itemId, String type) {
		return jdbcTemplate.query("SELECT p.*,f.food_name, r.recipe_name FROM user_preference p " +
						"LEFT JOIN food_tbl f ON f.food_id=p.item_id " +
						"LEFT JOIN recipe_tbl r ON r.recipe_id=p.item_id " +
						"WHERE p.user_open_id = ? AND p.item_id = ? AND p.type=?",
				rowMapper, openId, itemId, type).stream().findFirst().orElse(null);
	}

	public void savePreference(Preference preference) {
		jdbcTemplate.update("INSERT INTO user_preference(user_open_id, item_id, frequency,type) VALUES (?,?,?,?) " +
						"ON DUPLICATE KEY UPDATE frequency = VALUES(frequency)",
				preference.getUserOpenId(), preference.getItemId(), preference.getFrequency(), preference.getType());
	}
}
