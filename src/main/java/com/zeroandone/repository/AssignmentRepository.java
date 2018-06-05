package com.zeroandone.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.Assignment;
import com.zeroandone.domain.QAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer>,QueryDslPredicateExecutor<Assignment>,QuerydslBinderCustomizer<QAssignment> {
    default void customize(QuerydslBindings bindings, QAssignment qAssignment)
    {
        bindings.bind(qAssignment.assignment_Type).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
