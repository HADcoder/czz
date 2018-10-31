package com.diet.core.base;

import com.alibaba.fastjson.JSONObject;
import com.diet.core.persistence.Criteria;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 通用接口
 *
 * @author LiuYu
 */
public interface BaseService<T> {

    /**
     * 根据主键查询，返回单个对象
     * @param id
     * @return
     */
    T findById(Object id);

    /**
     * 根据条件查询，返回单个对象
     * @param criteria
     * @return
     */
    T findOne(Criteria<T> criteria);

    /**
     * 根据Sql查询，返回单个对象
     * @param sql
     * @param clazz
     * @param <S>
     * @return
     */
    <S> S findBySql(String sql, Class<S> clazz);

    List findAllByHql(String hql, JSONObject params);

    Page findAllByHql(String hql, JSONObject params, Pageable pageable);

    long count(String hql, JSONObject params);

    /**
     * 查询所有记录
     * @return
     */
    List<T> findAll();

    /**
     * 根据主键集合获取记录
     * @param ids
     * @return
     */
    List<T> findAllByIds(Iterable<Object> ids);

    /**
     * 根据查询条件获取记录
     * @param example
     * @return
     */
    List<T> findAll(Example<T> example);

    /**
     * 根据查询条件获取记录
     * @param criteria
     * @return
     */
    List<T> findAll(Criteria<T> criteria);

    /**
     * 分页查询所有记录
     * @param pageable
     * @return
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 根据条件分页获取记录
     * @param example
     * @param pageable
     * @return
     */
    Page<T> findAll(Example<T> example, Pageable pageable);

    /**
     * 根据条件分页获取记录
     * @param criteria
     * @param pageable
     * @return
     */
    Page<T> findAll(Criteria<T> criteria, Pageable pageable);

    /**
     * 根据Sql获取记录
     * @param sql
     * @param clazz
     * @param <S>
     * @return
     */
    <S> List<S> findAllBySql(String sql, Class<S> clazz);

    /**
     * 执行sql
     * @param sql
     */
    void executeSql(String sql);

    /**
     * 保存
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 批量保存
     * @param entities
     * @return
     */
    List<T> saveAll(Iterable<T> entities);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(Object id);

    /**
     * 根据实体删除
     * @param entity
     */
    void delete(T entity);

    /**
     * 根据实体集合删除
     * @param entities
     */
    void deleteAll(Iterable<T> entities);

    /**
     * 删除所有记录
     */
    void deleteAll();

    /**
     * 判断ID对应记录是否存在
     * @param id
     * @return
     */
    boolean existsById(Object id);

    /**
     * 统计所有记录
     * @return
     */
    long count();

    /**
     * 根据条件统计记录
     * @param criteria
     * @return
     */
    long count(Criteria<T> criteria);
}
