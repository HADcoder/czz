package com.blackchicktech.healthdiet.repository;

import com.blackchicktech.healthdiet.entity.Entity;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDao<T extends Entity> {

	protected RowMapper<T> rowMapper = (rs, rowNum) -> {
		T entity = entitySupplier();
		Map<String, Object[]> map = entity.getFieldInfo();
		Map<String, Boolean> columnMap = buildColumnMap(rs.getMetaData(), map.keySet());
		for (String key : map.keySet()) {
			Object o[] = map.get(key);
			try {
				if (!columnMap.get(key)) continue;
				Method m = entity.getClass().getMethod((String) o[1], (Class) o[2]);
				if (m != null) {
					if (o[2] == int.class || o[2] == Integer.class) {
						m.invoke(entity, rs.getInt(key));
					} else if (o[2] == long.class || o[2] == Long.class) {
						m.invoke(entity, rs.getLong(key));
					} else if (o[2] == String.class) {
						m.invoke(entity, rs.getString(key));
					} else if (o[2] == Date.class) {
						m.invoke(entity, rs.getTimestamp(key));
					} else if (o[2] == boolean.class || o[2] == Boolean.class) {
						m.invoke(entity, rs.getBoolean(key));
					} else if (o[2] == BigDecimal.class) {
						m.invoke(entity, rs.getBigDecimal(key));
					}
				}
			} catch (Exception ignored) {

			}
		}
		return entity;
	};

	private Map<String, Boolean> buildColumnMap(ResultSetMetaData metaData, Set<String> keys) throws SQLException {
		Map<String, Boolean> map = keys.stream().collect(Collectors.toMap(s -> s, s -> false));
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			String columnName = metaData.getColumnName(i);
			map.put(columnName, true);
		}
		return map;
	}

	protected abstract T entitySupplier();
}
