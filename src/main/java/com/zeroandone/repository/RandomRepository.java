package com.zeroandone.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.QRandom;
import com.zeroandone.domain.Random;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface RandomRepository  extends JpaRepository<Random, Integer>,QueryDslPredicateExecutor<Random>,QuerydslBinderCustomizer<QRandom> {

    default void customize(QuerydslBindings bindings, QRandom qRandom)
    {
        bindings.bind(qRandom.carrierId).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
