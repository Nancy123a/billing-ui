package com.zeroandone.repository;

import com.zeroandone.domain.OperatorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface OperatorCodeRepository extends CrudRepository<OperatorCode,Integer> {
    @RestResource(path = "findByCountryName", rel = "findByCountryName")
    Page<OperatorCode> findByOperatorCountryCountryNameContainingIgnoreCase(@Param("countryName") String countryName, Pageable pageable);
}
