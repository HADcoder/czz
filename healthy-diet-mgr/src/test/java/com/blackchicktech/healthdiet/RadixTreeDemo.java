package com.blackchicktech.healthdiet;

import com.blackchicktech.healthdiet.entity.Food;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharSequenceNodeFactory;
import com.googlecode.concurrenttrees.radixinverted.ConcurrentInvertedRadixTree;
import com.googlecode.concurrenttrees.radixinverted.InvertedRadixTree;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.StreamSupport;

/**
 * detail usage could be seen here { @link https://github.com/npgall/concurrent-trees#tree-implementations}
 */
public class RadixTreeDemo {

    // InvertedRadixTree -> keyStartingWith
    // SuffixTree -> keyContaining
    private final static InvertedRadixTree<Food> cache = new ConcurrentInvertedRadixTree<>(new DefaultCharSequenceNodeFactory());

    @BeforeClass
    public static void init() {

        cache.put("01-1-205", new Food("01-1-205", "麸皮", "someurl",
                "常见食物", "1", "1", "千卡", "282"));
        cache.put("01-1-408", new Food("01-1-408", "油饼", "someurl",
                "常见食物", "1", "1", "千卡", "403"));
        cache.put("01-1-503", new Food("01-1-503", "面筋(肉馅)", "someurl",
                "常见食物", "1", "1", "千卡", "364"));

        cache.put("02-1-107", new Food("2-1-107", "马铃薯(煮)", "someurl",
                "常见食物", "2", "1", "千卡", "65"));
        cache.put("03-1-305", new Food("3-1-305", "豆腐脑", "someurl",
                "常见食物", "3", "1", "千卡", "15"));
        cache.put("04-8-002", new Food("4-8-002", "白花菜", "someurl",
                "常见食物", "4", "8", "千卡", "0"));
    }

    @Test
    public void testCacheStartingCategories() {
        Assert.assertEquals(Arrays.asList("01-1-205", "01-1-408", "01-1-503"), Lists.newArrayList(cache.getKeysStartingWith("01-1")));
        Assert.assertEquals(true, StreamSupport.stream(cache.getValuesForKeysStartingWith("01-1").spliterator(), false)
                .allMatch(item -> Arrays.asList("01-1-205", "01-1-408", "01-1-503").contains(item.getFood_code())));


    }
}
