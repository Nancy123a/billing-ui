package com.zeroandone.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.QRanges;
import com.zeroandone.domain.Ranges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface RangesRepository extends JpaRepository<Ranges, Integer>,QueryDslPredicateExecutor<Ranges>,QuerydslBinderCustomizer<QRanges> {
    default void customize(QuerydslBindings bindings, QRanges qRanges)
    {
        bindings.bind(qRanges.rangeType).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
