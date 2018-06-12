package com.zeroandone.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.Assignment;
import com.zeroandone.domain.Country;
import com.zeroandone.domain.OperatorCode;
import com.zeroandone.domain.QAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer>,QueryDslPredicateExecutor<Assignment>,QuerydslBinderCustomizer<QAssignment> {

    default void customize(QuerydslBindings bindings, QAssignment qAssignment)
    {
        bindings.bind(qAssignment.prefix).first((path, value) -> path.stringValue().startsWith(value.toString()));
    }
}
