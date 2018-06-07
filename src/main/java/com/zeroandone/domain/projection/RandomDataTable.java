package com.zeroandone.domain.projection;

import com.zeroandone.domain.Assignment;
import com.zeroandone.domain.Carrier;
import com.zeroandone.domain.MRange;
import com.zeroandone.domain.Random;
import org.springframework.data.rest.core.config.Projection;
import java.time.LocalDate;

@Projection(name = "dataTable", types = Random.class)
public interface RandomDataTable {
    int getRandomId();
    int getRangeId();
    MRange getMRange();
    int getNumber();
    String getCarrierId();
    Carrier getCarrier();
    int getAssignmentId();
    Assignment getAssignment();
    LocalDate getLastUsed();
}
