package com.zeroandone.domain.projection;

import com.zeroandone.domain.Zone;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "dataTable", types = Zone.class)
public interface ZoneDataTable {
    int getZoneId();
    String getZoneName();
    int getCode();
    double getRate();
}
