package com.zeroandone.domain.projection;

import com.zeroandone.domain.Carrier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Carrier.class,name = "select2")
public interface CarrierSelect2 {

    @Value("#{target.getCarrierId()}")
    String getId();

    @Value("#{target.getCarrierName()}")
    String getText();
}
