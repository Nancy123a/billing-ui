package com.zeroandone.domain.projection;

import com.zeroandone.domain.Carrier;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "dataTable", types = Carrier.class)
public interface CarrierDataTable {
    String getCarrierId();
    String getCarrierName();
}
