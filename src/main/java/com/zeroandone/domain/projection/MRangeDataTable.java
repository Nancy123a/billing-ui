package com.zeroandone.domain.projection;

import com.zeroandone.domain.Assignment;
import com.zeroandone.domain.Carrier;
import com.zeroandone.domain.MRange;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "dataTable", types = MRange.class)
public interface MRangeDataTable {
    int getRangeId();
    String getCarrierId();
    Carrier getCarrier();
    String getRangeType();
    String get_To();
    String get_From();
    int getAssignmentId();
    Assignment getAssignment();
}
