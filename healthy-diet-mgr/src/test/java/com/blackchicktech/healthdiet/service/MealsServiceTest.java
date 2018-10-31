package com.blackchicktech.healthdiet.service;

import com.blackchicktech.healthdiet.entity.FoodRecommended;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by Eric Cen on 2018/9/6.
 */
public class MealsServiceTest {

    @Mock
    private MealsService mealsService;

    @Before
    public void setUp(){
        mealsService = Mockito.mock(MealsService.class);
    }

    @Test
    public void testCandidateFoodElements(){
        FoodRecommended foodRecommended = new FoodRecommended();
        foodRecommended.setGrainsBR(20);
        foodRecommended.setEggsBR(30);
        foodRecommended.setFatBR(10);
        when(mealsService.candidateFoodElements(foodRecommended,"BR")).thenCallRealMethod();
        List<String> candidateFoodElements = mealsService.candidateFoodElements(foodRecommended, "BR");
        Assert.assertEquals(candidateFoodElements.size(), 3);
    }
}
