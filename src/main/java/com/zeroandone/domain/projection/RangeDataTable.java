package com.zeroandone.domain.projection;

import com.zeroandone.domain.Assignment;
import com.zeroandone.domain.Carrier;
import com.zeroandone.domain.Range;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "dataTable", types = Range.class)
public interface RangeDataTable {

    int getRangeId();
    String getCarrierId();
    Carrier getCarrier();
    String getRangeType();
    String get_To();
    String get_From();
    int getAssignmentId();
    Assignment getAssignment();
}
