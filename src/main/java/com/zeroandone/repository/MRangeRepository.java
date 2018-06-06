package com.zeroandone.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.MRange;
import com.zeroandone.domain.QMRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface MRangeRepository extends JpaRepository<MRange, Integer>,QueryDslPredicateExecutor<MRange>,QuerydslBinderCustomizer<QMRange> {

    default void customize(QuerydslBindings bindings, QMRange qmRange)
    {
        bindings.bind(qmRange._From).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
