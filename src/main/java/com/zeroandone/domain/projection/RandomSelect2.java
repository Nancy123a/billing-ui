package com.zeroandone.domain.projection;

import com.zeroandone.domain.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Random.class,name = "select2")
public interface RandomSelect2 {
    @Value("#{target.getRandomId()}")
    String getId();

    @Value("#{target.getNumber()}")
    String getText();
}
