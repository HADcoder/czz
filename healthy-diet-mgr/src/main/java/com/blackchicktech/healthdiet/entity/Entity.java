package com.blackchicktech.healthdiet.entity;

import java.io.Serializable;
import java.util.Map;

public interface Entity extends Serializable {
	Map<String, Object[]> getFieldInfo();
}
