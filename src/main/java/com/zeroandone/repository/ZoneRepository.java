package com.zeroandone.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.Country;
import com.zeroandone.domain.QCountry;
import com.zeroandone.domain.QZone;
import com.zeroandone.domain.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ZoneRepository extends JpaRepository<Zone, Integer>,QueryDslPredicateExecutor<Zone>,QuerydslBinderCustomizer<QZone> {
    @RestResource(path = "/api/zones/findByZoneName", rel = "findByZoneName")
    Page<Country> findByZoneNameContainingIgnoreCase(@Param("zoneName") String zoneName, Pageable pageable);

    default void customize(QuerydslBindings bindings, QZone qZone)
    {
        bindings.bind(qZone.zoneName).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
