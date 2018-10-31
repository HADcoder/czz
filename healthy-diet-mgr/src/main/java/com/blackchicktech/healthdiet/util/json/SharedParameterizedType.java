package com.blackchicktech.healthdiet.util.json;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class SharedParameterizedType {
	public final static Type STR_LIST_TYPE = JsonUtil.makeParameterizedType(List.class, String.class);
	public final static Type STR_MAP_TYPE = JsonUtil.makeParameterizedType(Map.class, String.class, String.class);
	public final static Type STR_OBJ_MAP_TYPE = JsonUtil.makeParameterizedType(Map.class, String.class, Object.class);
	public final static Type STR_OBJECT_MAP_TYPE = JsonUtil.makeParameterizedType(Map.class, String.class, Object.class);
}
