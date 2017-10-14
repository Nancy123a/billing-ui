package com.zeroandone.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.Operator;
import com.zeroandone.domain.QCountry;
import com.zeroandone.domain.QOperator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;


public interface OperatorRepository extends JpaRepository<Operator,Integer>,QueryDslPredicateExecutor<Operator>,QuerydslBinderCustomizer<QOperator> {

    @RestResource(path = "findByCountryName", rel ="findByCountryName")
    Page<Operator>  findByCountryCountryNameContainingIgnoreCase(@Param("countryName") String countryName, Pageable pageable);

    default void customize(QuerydslBindings bindings, QOperator qOperator)
    {
        bindings.bind(qOperator.operatorName).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}