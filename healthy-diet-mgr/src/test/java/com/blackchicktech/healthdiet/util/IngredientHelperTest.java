package com.blackchicktech.healthdiet.util;

import com.blackchicktech.healthdiet.domain.MainIngredient;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class IngredientHelperTest {

    @Test
    public void testParse1() throws Exception {
        String raw = "{[{\"食材\":\"鸡蛋\",\"重量\":\"180克\"},{\"食材\":\"苦瓜\",\"重量\":\"100克\"}]}";
        List<MainIngredient> list = IngredientHelper.parse(raw);

        Assert.assertEquals(2, list.size());
        Assert.assertEquals("鸡蛋", list.get(0).getFoodName());
        Assert.assertEquals("180克", list.get(0).getWeight());

        Assert.assertEquals("苦瓜", list.get(1).getFoodName());
        Assert.assertEquals("100克", list.get(1).getWeight());
    }

    @Test
    public void testParse2() throws Exception {
        String raw = "{[{\"食材\":\"酸奶\",\"重量\":\"100克\"}]}";
        List<MainIngredient> list = IngredientHelper.parse(raw);

        Assert.assertEquals(1, list.size());
        Assert.assertEquals("酸奶", list.get(0).getFoodName());
        Assert.assertEquals("100克", list.get(0).getWeight());
    }
}
