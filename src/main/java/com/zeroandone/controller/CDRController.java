package com.zeroandone.controller;

import com.querydsl.core.types.Projections;
import com.zeroandone.domain.CDRStatistic;
import com.zeroandone.repository.CdrStatisticDailyRepository;
import com.zeroandone.repository.CdrStatisticHourlyRepository;
import com.zeroandone.utility.CDRStatisticCriteria;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CDRController {

    private final CdrStatisticDailyRepository cdrStatisticDailyRepository;
    private final CdrStatisticHourlyRepository cdrStatisticHourlyRepository;

    public CDRController(CdrStatisticDailyRepository cdrStatisticDailyRepository, CdrStatisticHourlyRepository cdrStatisticHourlyRepository) {
        this.cdrStatisticDailyRepository = cdrStatisticDailyRepository;
        this.cdrStatisticHourlyRepository = cdrStatisticHourlyRepository;
    }


    @RequestMapping(value="/api/group",method= RequestMethod.POST)
    public List<CDRStatistic> getQueryResult(@RequestBody CDRStatisticCriteria cdrGroupCriteria) {
        if(!cdrGroupCriteria.isHourly())
            return cdrStatisticDailyRepository.customFindWithProjection(
                    Projections.bean(CDRStatistic.class,cdrGroupCriteria.getSelect()),
                    cdrGroupCriteria.getWhere().getValue(),
                    cdrGroupCriteria.getGroupBy());
        else
            return cdrStatisticHourlyRepository.customFindWithProjection(
                    Projections.bean(CDRStatistic.class,cdrGroupCriteria.getSelect()),
                    cdrGroupCriteria.getWhere().getValue(),
                    cdrGroupCriteria.getGroupBy());
    }
}
