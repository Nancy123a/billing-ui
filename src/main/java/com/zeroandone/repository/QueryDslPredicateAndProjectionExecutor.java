package com.zeroandone.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shahbour on 10/11/16.
 */
@NoRepositoryBean
public interface QueryDslPredicateAndProjectionExecutor<T, ID extends Serializable>
        extends JpaRepository<T, ID>, QueryDslPredicateExecutor<T> {

    <PROJ> Page<PROJ> customFindWithProjection(FactoryExpression<PROJ> factoryExpression, Predicate predicate, Pageable pageable);

    <PROJ> List<PROJ> customFindWithProjection(FactoryExpression<PROJ> factoryExpression, Predicate predicate, List<Expression<?>> groupBy,Predicate havingCondition);
}
