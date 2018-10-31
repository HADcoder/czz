package com.diet.core.base.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diet.caches.BaseCacheService;
import com.diet.core.base.BaseDao;
import com.diet.core.base.BaseService;
import com.diet.core.persistence.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

/**
 * @author LiuYu
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BaseDao<T, Object> baseDao;

    @Autowired
    protected BaseCacheService cacheService;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public T findById(Object id) {
        Optional<T> entity = baseDao.findById(id);
        return entity.get();
    }

    @Override
    public T findOne(Criteria<T> criteria) {
        Optional<T> entity = baseDao.findOne(criteria);
        return entity.isPresent() ? entity.get() : null;
    }

    @Override
    public <S> S findBySql(String sql, Class<S> clazz) {
        Query query = entityManager.createNativeQuery(sql, clazz);
        List list = query.getResultList();
        if (list != null && !list.isEmpty()) {
            Object object = list.get(0);
            return JSON.parseObject(JSON.toJSONString(object), clazz);
        }
        return null;
    }

    @Override
    public List findAllByHql(String hql, JSONObject params) {
        Query query = entityManager.createQuery(hql);
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query.getResultList();
    }

    @Override
    public Page findAllByHql(String hql, JSONObject params, Pageable pageable) {
        Query query = entityManager.createQuery(hql);
        if (params != null && !params.isEmpty()) {
            setQueryParameters(query, params);
        }
        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        logger.info(hql);

        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> count(hql, params));
    }

    @Override
    public long count(String hql, JSONObject params) {
        String t = QueryUtils.createCountQueryFor(hql);
        TypedQuery<Long> query = entityManager.createQuery(t, Long.class);
        if (params != null && !params.isEmpty()) {
            this.setQueryParameters(query, params);
        }
        Long total = query.getSingleResult();
        return total;
    }

    private Query setQueryParameters(Query query, JSONObject params) {
        // 根据query的参数集设置各个参数值
        Set<Parameter<?>> set = query.getParameters();
        Iterator<Parameter<?>> i = set.iterator();
        while (i.hasNext()) {
            String param = i.next().getName();
            query.setParameter(param, params.get(param));
        }
        return query;
    }

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    public List<T> findAllByIds(Iterable<Object> ids) {
        return baseDao.findAllById(ids);
    }

    @Override
    public List<T> findAll(Example<T> example) {
        return baseDao.findAll(example);
    }

    @Override
    public List<T> findAll(Criteria<T> criteria) {
        return baseDao.findAll(criteria);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseDao.findAll(pageable);
    }

    @Override
    public Page<T> findAll(Example<T> example, Pageable pageable) {
        return baseDao.findAll(example, pageable);
    }

    @Override
    public Page<T> findAll(Criteria<T> criteria, Pageable pageable) {
        return baseDao.findAll(criteria, pageable);
    }

    @Override
    public <S> List<S> findAllBySql(String sql, Class<S> clazz) {
        Query query = entityManager.createNativeQuery(sql, clazz);
        List list = query.getResultList();
        if (list != null && !list.isEmpty()) {
            return JSONArray.parseArray(JSON.toJSONString(list), clazz);
        }
        return new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void executeSql(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public T save(T entity) {
        return baseDao.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<T> saveAll(Iterable<T> entities) {
        return baseDao.saveAll(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Object id) {
        baseDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(T entity) {
        baseDao.delete(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll(Iterable<T> entities) {
        baseDao.deleteAll(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll() {
        baseDao.deleteAll();
    }

    @Override
    public boolean existsById(Object id) {
        return baseDao.existsById(id);
    }

    @Override
    public long count() {
        return baseDao.count();
    }

    @Override
    public long count(Criteria<T> criteria) {
        return baseDao.count(criteria);
    }
}
