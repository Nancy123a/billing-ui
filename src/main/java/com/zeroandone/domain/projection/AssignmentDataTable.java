package com.zeroandone.domain.projection;

import com.zeroandone.domain.Assignment;
import com.zeroandone.domain.Carrier;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

@Projection(name = "dataTable", types = Assignment.class)
public interface AssignmentDataTable {
    int getAssignmentId();
    LocalDate getAssignmentDate();
    String getRequester();
    String getCarrierId();
    Carrier getCarrier();
    String getPrefix();
    int getCount();
    String getAssignmentType();
}
