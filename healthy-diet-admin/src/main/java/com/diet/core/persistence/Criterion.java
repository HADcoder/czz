package com.diet.core.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author LiuYu
 */
public interface Criterion {
    enum Operator {
        EQ, NE, LIKE, LIKE_LEFT, LIKE_RIGHT, GT, LT, GTE, LTE, AND, OR, IS_MEMBER, IS_NOT_MEMBER
    }

    /**
     * 查询条件组装
     * @param root
     * @param query
     * @param builder
     * @return
     */
    Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}
