package com.zeroandone.repository;


import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.zeroandone.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.EntityManager;
import java.util.List;
// When adding spring data rest , then you can talk directly to the repository without having to do a controller
public interface ZoneRepository extends JpaRepository<Zone, Integer>,QueryDslPredicateExecutor<Zone>,QuerydslBinderCustomizer<QZone> {

    default void customize(QuerydslBindings bindings, QZone qZone)
    {
        bindings.bind(qZone.zoneName).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

}
