package com.blackchicktech.healthdiet.repository;

import com.blackchicktech.healthdiet.domain.UserDataInfo;
import com.blackchicktech.healthdiet.domain.UserInfo;
import com.blackchicktech.healthdiet.entity.User;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);

	public void createUser(UserInfo userInfo, UserDataInfo userDataInfo) {
		Joiner joiner = Joiner.on(",");
		logger.info("Going to insert user data openId={}, unionId={} gender={}, birthday={}, height={}," +
						" weight={}, sportRate={}, nephroticPeriod={}, treat={}, disease={}, irritability={}, userInfo={}",
				userInfo.getOpenId(),
				userInfo.getUnionId(),
				userDataInfo.getGender(),
				userDataInfo.getBirthDay(),
				userDataInfo.getHeight(),
				userDataInfo.getWeight(),
				userDataInfo.getSportRate(),
				userDataInfo.getNephroticPeriod(),
				userDataInfo.getTreatmentMethod(),
				userDataInfo.getOtherDisease(),
				userDataInfo.getIrritability(),
				userInfo.getInfo());
		jdbcTemplate.update(
				"REPLACE INTO user_tbl VALUES (?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?)",
				userInfo.getOpenId(),
				userInfo.getUnionId(),
				userDataInfo.getGender(),
				userDataInfo.getBirthDay(),
				userDataInfo.getHeight(),
				userDataInfo.getWeight(),
				userDataInfo.getSportRate(),
				userDataInfo.getNephroticPeriod(),
				joiner.join(userDataInfo.getTreatmentMethod()),
				joiner.join(userDataInfo.getOtherDisease()),
				joiner.join(userDataInfo.getIrritability()),
				userInfo.getInfo()
		);
	}

	public User getUserByOpenId(String openId) {
		List<User> users = jdbcTemplate.query("SELECT * FROM user_tbl WHERE open_id = ?", rowMapper, openId);
		return users.stream().findFirst().orElse(null);
	}

	public User getUserByUnionId(String unionId) {
		List<User> users = jdbcTemplate.query("SELECT * FROM user_tbl WHERE union_id = ?", rowMapper, unionId);
		return users.stream().findFirst().orElse(null);
	}
}
