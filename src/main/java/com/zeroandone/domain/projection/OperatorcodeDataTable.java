package com.zeroandone.domain.projection;

import com.zeroandone.domain.Operator;
import com.zeroandone.domain.OperatorCode;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

@Projection(name = "dataTable", types = OperatorCode.class)
public interface OperatorcodeDataTable {
    int getOperatorCodeId();
    String getOperatorName();
    int getCode();
    int getOperatorId();
    Operator getOperator();
    boolean getEnabled();
    LocalDate getActivationDate();
    LocalDate getDeactivationDate();
    int getCountryId();
}
