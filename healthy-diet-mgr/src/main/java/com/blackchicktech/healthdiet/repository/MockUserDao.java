package com.blackchicktech.healthdiet.repository;

import com.blackchicktech.healthdiet.domain.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//开发用
@Repository
public class MockUserDao {

    private Map<String, UserInfo> cache = new ConcurrentHashMap<>();

    public UserInfo getUserInfo(String openId) {
        return cache.get(openId);
    }

    public void addUserInfo(String openId, UserInfo userInfo) {
        cache.put(openId, userInfo);
    }
}
