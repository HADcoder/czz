package com.blackchicktech.healthdiet.util;

import com.blackchicktech.healthdiet.domain.ReportRequest;
import com.blackchicktech.healthdiet.domain.UserDataInfo;
import com.blackchicktech.healthdiet.domain.UserInfo;
import com.blackchicktech.healthdiet.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserUtil {

    public static ReportRequest createReportRequest(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setInfo(user.getUserInfo());
        userInfo.setOpenId(user.getOpenId());
        userInfo.setUnionId(user.getUnionId());

        UserDataInfo userDataInfo = new UserDataInfo();
        userDataInfo.setBirthDay(user.getBirthDay());
        userDataInfo.setGender(user.getGender());
        userDataInfo.setHeight(user.getHeight());
        userDataInfo.setIrritability(toStringList(user.getIrritability()));
        userDataInfo.setNephroticPeriod(user.getNephroticPeriod());
        userDataInfo.setWeight(user.getWeight());
        userDataInfo.setSportRate(user.getSportRate());
        userDataInfo.setOtherDisease(toStringList(user.getOtherDiseases()));
        userDataInfo.setTreatmentMethod(toStringList(user.getTreatmentMethod()));

        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setUserInfo(userInfo);
        reportRequest.setUserDataInfo(userDataInfo);
        return reportRequest;
    }

    private static List<String> toStringList(String raw) {
        if (raw == null || raw.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(Arrays.asList(raw.split(",")));
    }
}
