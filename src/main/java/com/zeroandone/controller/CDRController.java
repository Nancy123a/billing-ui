package com.zeroandone.controller;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.zeroandone.domain.CDRStatistic;
import com.zeroandone.domain.QCDR;
import com.zeroandone.domain.ScvdReport;
import com.zeroandone.domain.UserOperator;
import com.zeroandone.repository.CDRRepository;
import com.zeroandone.repository.CdrStatisticDailyRepository;
import com.zeroandone.repository.CdrStatisticHourlyRepository;
import com.zeroandone.repository.UserOperatorRepository;
import com.zeroandone.utility.CDRStatisticCriteria;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CDRController {

    private final CdrStatisticDailyRepository cdrStatisticDailyRepository;
    private final CdrStatisticHourlyRepository cdrStatisticHourlyRepository;
    private final CDRRepository cdrRepository;
    private final UserOperatorRepository userOperatorRepository;

    public CDRController(CdrStatisticDailyRepository cdrStatisticDailyRepository, CdrStatisticHourlyRepository cdrStatisticHourlyRepository, CDRRepository cdrRepository, UserOperatorRepository userOperatorRepository) {
        this.cdrStatisticDailyRepository = cdrStatisticDailyRepository;
        this.cdrStatisticHourlyRepository = cdrStatisticHourlyRepository;
        this.cdrRepository = cdrRepository;
        this.userOperatorRepository = userOperatorRepository;
    }


    @RequestMapping(value="/api/group",method= RequestMethod.POST)
    public List<CDRStatistic> getQueryResult(@RequestBody CDRStatisticCriteria cdrGroupCriteria) {
        if(!cdrGroupCriteria.isHourly())
            return cdrStatisticDailyRepository.customFindWithProjection(
                    Projections.bean(CDRStatistic.class,cdrGroupCriteria.getSelect()),
                    cdrGroupCriteria.getWhere().getValue(),
                    cdrGroupCriteria.getGroupBy(),cdrGroupCriteria.getHavingCondition());
        else
            return cdrStatisticHourlyRepository.customFindWithProjection(
                    Projections.bean(CDRStatistic.class,cdrGroupCriteria.getSelect()),
                    cdrGroupCriteria.getWhere().getValue(),
                    cdrGroupCriteria.getGroupBy(),cdrGroupCriteria.getHavingCondition());
    }

    @RequestMapping(value="/api/partner/group",method= RequestMethod.POST)
    public List<CDRStatistic> getPartnerQueryResult(@RequestBody CDRStatisticCriteria cdrGroupCriteria) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Integer> operators = userOperatorRepository.findAllByUsername(auth.getName()).stream().map(UserOperator::getOperatorId).collect(Collectors.toList());


        FactoryExpression<CDRStatistic> factoryExpression = Projections.bean(CDRStatistic.class,cdrGroupCriteria.getSelect());
        List<Expression<?>> expressionList = cdrGroupCriteria.getGroupBy();
        Predicate havingCondition = cdrGroupCriteria.getHavingCondition();
        // Change where to include only the operators
        cdrGroupCriteria.setEgressOperatorId(operators);
        Predicate whereCondition = cdrGroupCriteria.getWhere().getValue();


        if(!cdrGroupCriteria.isHourly())
            return cdrStatisticDailyRepository.customFindWithProjection(
                    factoryExpression,
                    whereCondition,
                    expressionList,havingCondition);
        else
            return cdrStatisticHourlyRepository.customFindWithProjection(
                    factoryExpression,
                    whereCondition,
                    expressionList,havingCondition);
    }


    @PostMapping(value = "/api/scvd/detail")
    public List<ScvdReport> getScvdDetail(@RequestBody CDRStatisticCriteria cdrGroupCriteria) {
        Map<String,Expression<?>> select = new HashMap<>();

        QCDR qcdr = QCDR.cDR;
        select.put("sigDate",qcdr.sigDate);
        select.put("callingNumber",qcdr.asigFrom);
        select.put("calledNumber",qcdr.asigTo);
        select.put("attempts",qcdr.count());

        BooleanBuilder where = new BooleanBuilder();
        List<Expression<?>> groupBy = new ArrayList<>();

        where.and(qcdr.sigDate.eq(cdrGroupCriteria.getFromDate().toLocalDate()));
        groupBy.add(qcdr.sigDate);
        groupBy.add(qcdr.asigFrom);
        groupBy.add(qcdr.asigTo);

        if(cdrGroupCriteria.isHourly()) {
            select.put("sigHour",qcdr.sigHour);
            where.and(qcdr.sigHour.eq(cdrGroupCriteria.getSigHour()));
            groupBy.add(qcdr.sigHour);
        }

        if(!cdrGroupCriteria.getIngressCountryId().isEmpty())
            where.and(qcdr.ingressCountryId.in(cdrGroupCriteria.getIngressCountryId()));

        if(!cdrGroupCriteria.getIngressOperatorId().isEmpty())
            where.and(qcdr.ingressOperatorId.in(cdrGroupCriteria.getIngressOperatorId()));

        if(!cdrGroupCriteria.getEgressOperatorId().isEmpty())
            where.and(qcdr.egressOperatorId.in(cdrGroupCriteria.getEgressOperatorId()));

        if(!cdrGroupCriteria.getCustomer().isEmpty()) {
            select.put("customer",qcdr.asigCarrierGroup);
            where.and(qcdr.asigCarrierGroup.in(cdrGroupCriteria.getCustomer()));
            groupBy.add(qcdr.asigCarrierGroup);
        }

        if(!cdrGroupCriteria.getVendor().isEmpty()) {
            select.put("vendor",qcdr.bsigCarrierGroup);
            where.and(qcdr.bsigCarrierGroup.in(cdrGroupCriteria.getVendor()));
            groupBy.add(qcdr.bsigCarrierGroup);
        }

        // Only query connected called
        //where.and(qcdr.asigInviteStatus.eq("200"));

        return cdrRepository.customFindWithProjection(
                Projections.bean(ScvdReport.class,select),
                where.getValue(),
                groupBy,null
        );



    }
}
