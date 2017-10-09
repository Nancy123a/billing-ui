package com.zeroandone.repository;

import com.zeroandone.domain.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;


public interface OperatorRepository extends JpaRepository<Operator,Integer> {

    @RestResource(path = "findByCountryName", rel ="findByCountryName")
    Page<Operator>  findByCountryCountryNameContainingIgnoreCase(@Param("countryName") String countryName, Pageable pageable);
}
