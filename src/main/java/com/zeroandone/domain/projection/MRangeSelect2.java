package com.zeroandone.domain.projection;

import com.zeroandone.domain.MRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = MRange.class,name = "select2")
public interface MRangeSelect2 {
    @Value("#{target.getRangeId()}")
    String getId();

    @Value("#{target.get_From()}")
    String getText();
}
