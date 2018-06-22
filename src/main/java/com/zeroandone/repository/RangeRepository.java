package com.zeroandone.repository;


import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.QRange;
import com.zeroandone.domain.Range;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RangeRepository extends JpaRepository<Range, Integer>,QueryDslPredicateExecutor<Range>,QuerydslBinderCustomizer<QRange> {

    Page<Range> findBy_FromContainingIgnoreCase(@Param("_From") String searchTerm, Pageable pageRequest);



    default void customize(QuerydslBindings bindings, QRange qmRange)
    {
        bindings.bind(qmRange._From).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
