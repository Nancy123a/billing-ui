package com.zeroandone.domain.projection;

import com.zeroandone.domain.Country;
import com.zeroandone.domain.CountryCode;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "dataTable", types = CountryCode.class)
public interface CountrycodeDataTable {
    int getCountryCodeId();

    int getCode();

    int getCountryId();

    Country getCountry();

}
