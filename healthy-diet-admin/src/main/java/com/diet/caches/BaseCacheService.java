package com.diet.caches;

import java.util.List;

/**
 * @author LiuYu
 */
public interface BaseCacheService {
    String DEFAULT_CACHE = "defaultCache";

    /**
     * 获取缓存信息
     * @param key
     * @return
     */
    Object get(Object key);

    /**
     * 获取缓存信息，返回指定类型对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(Object key, Class<T> clazz);

    /**
     *  设置缓存信息
     * @param key
     * @param value
     */
    void put(Object key, Object value);

    /**
     *  设置缓存信息，超时
     * @param key
     * @param value
     * @param timeToLiveSeconds 存活时间，单位：秒
     */
    void put(Object key, Object value, int timeToLiveSeconds);

    /**
     * 移除缓存信息
     * @param key
     * @return
     */
    boolean remove(Object key);

    /**
     * 模糊查询
     * @param key
     * @return
     */
    <T> List<T> getListByKeyPattern(String key, Class<T> clazz);
}
