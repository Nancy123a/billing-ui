package com.zeroandone.domain.projection;

import com.zeroandone.domain.Assignment;
import com.zeroandone.domain.Carrier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Assignment.class,name = "select2")
public interface AssignmentSelect2 {
    @Value("#{target.getAssignmentId()}")
    String getId();

    @Value("#{target.getPrefix()}")
    String getText();
}
