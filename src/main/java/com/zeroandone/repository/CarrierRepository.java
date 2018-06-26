package com.zeroandone.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.Carrier;
import com.zeroandone.domain.QCarrier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface CarrierRepository extends JpaRepository<Carrier, String>,PagingAndSortingRepository <Carrier,String>,QueryDslPredicateExecutor<Carrier>,QuerydslBinderCustomizer<QCarrier> {

    @RestResource(path = "findByName", rel ="findByName")
    Page<Carrier> findByCarrierNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    default void customize(QuerydslBindings bindings, QCarrier qCarrier)
    {
        bindings.bind(qCarrier.carrierName).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
