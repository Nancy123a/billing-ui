package com.zeroandone.domain.projection;

import com.zeroandone.domain.Country;
import com.zeroandone.domain.Operator;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

@Projection(name = "dataTable",types = Operator.class)
public interface OperatorDataTable {

    int getOperatorId();
    String getOperatorName();
    Country getCountry();
    LocalDate getActivationDate();
    LocalDate getDeactivationDate();
    int getCountryId();
    int getObsolete();
    short getTypeId();

}
