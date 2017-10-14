package com.zeroandone.domain.projection;

import com.zeroandone.domain.Operator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "select2",types = Operator.class)
public interface OperatorSelect2 {

    @Value("#{target.getOperatorId()}")
    String getId();

    @Value("#{target.getOperatorName()}")
    String getText();
}
