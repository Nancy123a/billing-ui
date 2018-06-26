package com.zeroandone.domain.projection;

import com.zeroandone.domain.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Range.class,name = "select2")
public interface RangeSelect2 {
    @Value("#{target.getRangeId()}")
    String getId();

    @Value("#{target.get_From()}")
    String getText();
}
