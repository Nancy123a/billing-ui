package com.zeroandone.repository;

import com.zeroandone.domain.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository("countryRepository")
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @RestResource(path = "findByCountryName", rel = "findByCountryName")
    Page<Country> findByCountryName(@Param("countryName") String countryName, Pageable pageable);

}
