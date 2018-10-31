package com.blackchicktech.healthdiet.service;

import com.blackchicktech.healthdiet.domain.ReportRequest;
import com.blackchicktech.healthdiet.domain.SuggestNutrition;
import com.blackchicktech.healthdiet.domain.UserDataInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by Eric Cen on 2018/8/19.
 */
public class ReportServiceTest {

    @Mock
    private ReportService reportService;

    @Mock
    private ReportRequest reportRequest;

    @Mock
    private UserDataInfo userDataInfo;


    @Before
    public void setUp(){
        reportService = Mockito.mock(ReportService.class);
        reportRequest = Mockito.mock(ReportRequest.class);
        userDataInfo = Mockito.mock(UserDataInfo.class);
        reportRequest.setUserDataInfo(userDataInfo);
    }

    @Test
    public void testCalSuggestNutritionWithMilkIrritability(){
        when(reportService.calStandardWeight(reportRequest)).thenReturn(43f);
        when(reportRequest.getUserDataInfo()).thenReturn(userDataInfo);
        when(userDataInfo.getIrritability()).thenReturn(Arrays.asList(new String[]{"milk"}));
        when(userDataInfo.getNephroticPeriod()).thenReturn("2");
        when(reportService.calSuggestNutrition(reportRequest)).thenCallRealMethod();
        List<SuggestNutrition> suggestNutritionList = reportService.calSuggestNutrition(reportRequest);
        Assert.assertEquals(suggestNutritionList.size(), 6);
        Assert.assertEquals(suggestNutritionList.get(0).getValue(), "100克");
        Assert.assertEquals(suggestNutritionList.get(1).getValue(), "50克");
        Assert.assertEquals(suggestNutritionList.get(2).getValue(), "250克");
        Assert.assertEquals(suggestNutritionList.get(3).getValue(), "200克");
        Assert.assertEquals(suggestNutritionList.get(4).getValue(), "145克");
        Assert.assertEquals(suggestNutritionList.get(5).getValue(), "35克");
    }

    @Test
    public void testCalSuggestNutritionWithOutMilkIrritability(){
        when(reportService.calStandardWeight(reportRequest)).thenReturn(43f);
        when(reportRequest.getUserDataInfo()).thenReturn(userDataInfo);
        when(userDataInfo.getIrritability()).thenReturn(Arrays.asList(new String[]{"NoMilkIrritability"}));
        when(userDataInfo.getNephroticPeriod()).thenReturn("4");
        when(reportService.calSuggestNutrition(reportRequest)).thenCallRealMethod();
        List<SuggestNutrition> suggestNutritionList = reportService.calSuggestNutrition(reportRequest);
        Assert.assertEquals(suggestNutritionList.size(), 7);
        Assert.assertEquals(suggestNutritionList.get(0).getValue(), "75克");
        Assert.assertEquals(suggestNutritionList.get(1).getValue(), "150克");
        Assert.assertEquals(suggestNutritionList.get(2).getValue(), "250克");
        Assert.assertEquals(suggestNutritionList.get(3).getValue(), "200克");
        Assert.assertEquals(suggestNutritionList.get(4).getValue(), "230克");
        Assert.assertEquals(suggestNutritionList.get(5).getValue(), "55克");
        Assert.assertEquals(suggestNutritionList.get(6).getValue(), "40克");
    }
}
