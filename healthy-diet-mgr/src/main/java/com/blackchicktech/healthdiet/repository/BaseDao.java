package com.blackchicktech.healthdiet.repository;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
	List<T> queryList(Map<String, Object> param);
}
