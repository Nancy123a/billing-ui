package com.zeroandone.repository;

import com.zeroandone.domain.CountryCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface CountryCodeRepository extends CrudRepository<CountryCode,Integer> {
    @RestResource(path = "findByCountryName", rel = "findByCountryName")
    Page<CountryCode> findByCountryCountryNameContainingIgnoreCase(@Param("countryName") String countryName, Pageable pageable);
}
