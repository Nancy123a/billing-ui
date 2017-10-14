package com.zeroandone.domain.projection;

import com.zeroandone.domain.Country;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "dataTable", types = Country.class)
public interface CountryDataTable {
    int getCountryId();

    String getCountryName();


}
