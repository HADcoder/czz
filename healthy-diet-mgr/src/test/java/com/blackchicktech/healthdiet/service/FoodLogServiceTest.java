package com.blackchicktech.healthdiet.service;

import com.blackchicktech.healthdiet.entity.FoodLog;
import com.blackchicktech.healthdiet.repository.FoodDaoImpl;
import com.blackchicktech.healthdiet.repository.FoodLogDao;
import com.blackchicktech.healthdiet.repository.UserDaoImpl;
import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * Created by Eric Cen on 2018/8/26.
 */
public class FoodLogServiceTest {

    @Mock
    private FoodLogService foodLogService;

    @Mock
    private FoodDaoImpl foodDao;

    @Mock
    private UserDaoImpl userDao;

    @Mock
    private FoodLogDao foodLogDao;

    private List<FoodLog> foodLogList = ImmutableList.<FoodLog>builder()
            .add(new FoodLog("test", new Date(2018, 8, 28),
                    true, 2500, 3000, 0.6, 30.5, 0.5, 56.2, 0.5, 4.5,
                    5.5, 3.6, 4.6))
            .add(new FoodLog("test", new Date(2018, 8, 27),
                    true, 2500, 3000, 0.6, 30.5, 0.5, 56.2, 0.5, 4.5,
                    5.5, 3.6, 4.6))
            .add(new FoodLog("test", new Date(2018, 8, 26),
                    true, 2500, 3000, 0.6, 30.5, 0.5, 56.2, 0.5, 4.5,
                    5.5, 3.6, 4.6)).build();


    @Before
    public void setUp(){
        foodLogService = Mockito.mock(FoodLogService.class);
        foodDao = Mockito.mock(FoodDaoImpl.class);
        userDao = Mockito.mock(UserDaoImpl.class);
        foodLogDao = Mockito.mock(FoodLogDao.class);
    }

    @Test
    public void testDeduceElementEvgs(){
        when(foodLogService.deduceElementEvgs(foodLogList)).thenCallRealMethod();
        Map<String, Double> result = foodLogService.deduceElementEvgs(foodLogList);
        Assert.assertEquals(result.get("ca"), 4.6, 0.0);
        Assert.assertEquals(result.get("cho"), 56.2, 0.0);
        Assert.assertEquals(result.get("ceRatio"), 0.5, 0.0);
    }

    @Test
    public void testIsStandardLogType(){
        when(foodLogService.isStandardLogType(foodLogList)).thenCallRealMethod();
        boolean isStandardLogType = foodLogService.isStandardLogType(foodLogList);
        Assert.assertTrue(isStandardLogType);
    }


}
