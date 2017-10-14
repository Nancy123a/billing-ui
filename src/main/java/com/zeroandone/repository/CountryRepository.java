package com.zeroandone.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.zeroandone.domain.Country;
import com.zeroandone.domain.QCountry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;


public interface CountryRepository extends JpaRepository<Country, Integer>,QueryDslPredicateExecutor<Country>,QuerydslBinderCustomizer<QCountry> {
    @RestResource(path = "findByCountryName", rel = "findByCountryName")
    Page<Country> findByCountryName(@Param("countryName") String countryName, Pageable pageable);

    default void customize(QuerydslBindings bindings, QCountry qCountry)
    {
        bindings.bind(qCountry.countryName).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
