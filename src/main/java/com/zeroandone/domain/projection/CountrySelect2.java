package com.zeroandone.domain.projection;

import com.zeroandone.domain.Country;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Country.class,name = "select2")
public interface CountrySelect2 {

    @Value("#{target.getCountryId()}")
    String getId();

    @Value("#{target.getCountryName()}")
    String getText();
}
