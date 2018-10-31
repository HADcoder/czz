package com.blackchicktech.healthdiet.service;

import com.blackchicktech.healthdiet.domain.UserDataInfo;
import com.blackchicktech.healthdiet.domain.UserInfo;
import com.blackchicktech.healthdiet.domain.UserMetadata;
import com.blackchicktech.healthdiet.entity.User;
import com.blackchicktech.healthdiet.repository.MockUserDao;
import com.blackchicktech.healthdiet.repository.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    @Autowired
    private UserDaoImpl userDao;

    public void createUser(UserInfo userInfo, UserDataInfo userDataInfo) {
        userDao.createUser(userInfo, userDataInfo);
    }

    public User getUserByOpenId(String openId){
        return userDao.getUserByOpenId(openId);
    }

    public User getUserByUnionId(String unionId) {
        return userDao.getUserByUnionId(unionId);
    }
}
