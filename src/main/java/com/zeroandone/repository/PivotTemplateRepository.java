package com.zeroandone.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.PivotTemplate;
import com.zeroandone.domain.QPivotTemplate;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PivotTemplateRepository extends PagingAndSortingRepository<PivotTemplate,String>,QueryDslPredicateExecutor<PivotTemplate>,QuerydslBinderCustomizer<QPivotTemplate> {

    default void customize(QuerydslBindings bindings, QPivotTemplate qPivotTemplate)
    {
        bindings.bind(qPivotTemplate.templateName).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
