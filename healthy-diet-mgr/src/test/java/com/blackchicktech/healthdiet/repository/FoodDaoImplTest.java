package com.blackchicktech.healthdiet.repository;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class FoodDaoImplTest {

    @Test
    public void listFoodByAlias() {
        String alias = "肉包子";
        Assert.assertEquals("肉%包%子",
                Arrays.stream(alias.split("")).collect(Collectors.joining("%")));

    }
}