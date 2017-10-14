package com.zeroandone.utility;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.zeroandone.domain.QCdrStatisticBase;
import com.zeroandone.domain.QCdrStatisticDaily;
import com.zeroandone.domain.QCdrStatisticHourly;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
@Setter
public class CDRStatisticCriteria {

	@DateTimeFormat(iso= ISO.DATE_TIME)
	private LocalDateTime fromDate;
	@DateTimeFormat(iso= ISO.DATE_TIME)
	private LocalDateTime toDate;
	private int sigHour = -1;
	private List<String> customer = new ArrayList<>();
	private List<String> vendor = new ArrayList<>();
	private List<Integer> ingressCountryId = new ArrayList<>();
	private List<Integer> ingressOperatorId = new ArrayList<>();
	private List<Integer> egressCountryId = new ArrayList<>();
	private List<Integer> egressOperatorId = new ArrayList<>();

	private boolean cache = false;
	private boolean hourly = false;
	private boolean compareDates = false;

	public Map getSelect(){
		Map<String,Expression<?>> select = new HashMap<>();

        QCdrStatisticBase qCdrStatisticBase;

		if(isHourly())
			qCdrStatisticBase = QCdrStatisticHourly.cdrStatisticHourly._super;
		else
			qCdrStatisticBase = QCdrStatisticDaily.cdrStatisticDaily._super;

		Expression<Integer> sigHour = qCdrStatisticBase.sigHour;
        Expression<Integer> attempts = qCdrStatisticBase.attempts.sum();
        Expression<Integer> connected = qCdrStatisticBase.connected.sum();
        Expression<Integer> duration = qCdrStatisticBase.duration.sum();
        Expression<String> customer = qCdrStatisticBase.customer;
        Expression<String> vendor = qCdrStatisticBase.vendor;
		Expression<LocalDate> sigDate = qCdrStatisticBase.sigDate;
        Expression<String> ingressCountryName = qCdrStatisticBase.ingressCountry.countryName;
        Expression<String> egressCountryName = qCdrStatisticBase.egressCountry.countryName;
        Expression<String> ingressOperatorName = qCdrStatisticBase.ingressOperator.operatorName;
        Expression<String> egressOperatorName = qCdrStatisticBase.egressOperator.operatorName;



        select.put("attempts",attempts);
        select.put("sigDate",sigDate);
        select.put("connected",connected);
        select.put("duration",duration);

		if(isHourly())
			select.put("sigHour",sigHour);

        if(getCustomer().size() > 0) {
            select.put("customer",customer);
        }
        if(getVendor().size() > 0) {
            select.put("vendor",vendor);
        }
        if(getIngressCountryId().size() > 0) {
            select.put("ingressCountryName",ingressCountryName);
        }
        if(getEgressCountryId().size() > 0) {
            select.put("egressCountryName",egressCountryName);
        }
        if(getIngressOperatorId().size() > 0) {
            select.put("ingressOperatorName",ingressOperatorName);
        }
        if(getEgressOperatorId().size() > 0) {
            select.put("egressOperatorName",egressOperatorName);
        }
		return select;
	}

    public BooleanBuilder getWhere(){
        BooleanBuilder where = new BooleanBuilder();
        List<LocalDate> dateList = new ArrayList<>();
        LocalDate from = getFromDate().toLocalDate();
        LocalDate to = getToDate().toLocalDate();
		QCdrStatisticBase qCdrStatisticBase;
		if(isHourly())
			qCdrStatisticBase = QCdrStatisticHourly.cdrStatisticHourly._super;
		else
			qCdrStatisticBase = QCdrStatisticDaily.cdrStatisticDaily._super;

		if(compareDates) {
			dateList.add(from);
			dateList.add(to);
		}
		else {
			do {
				dateList.add(from);
				from = from.plus(1, ChronoUnit.DAYS);
			} while (!from.isAfter(to));
		}

        where.and(qCdrStatisticBase.sigDate.in(dateList));

        if(isHourly()) {
            int startHour = fromDate.getHour();
            int endHour = toDate.getHour();
            if(startHour != 0 || endHour != 23) {
                where.and(qCdrStatisticBase.sigHour.goe(startHour));
                where.and(qCdrStatisticBase.sigHour.loe(endHour));
            }
        }


        if(getCustomer().size() > 0 && !getCustomer().get(0).equalsIgnoreCase("all")) {
            where.and(qCdrStatisticBase.customer.in(getCustomer()));
        }

        if(getVendor().size() > 0 && !getVendor().get(0).equalsIgnoreCase("all")) {
            where.and(qCdrStatisticBase.vendor.in(getVendor()));
        }

        if(getIngressCountryId().size() > 0 && getIngressCountryId().get(0) > 0) {
            where.and(qCdrStatisticBase.ingressCountryId.in(getIngressCountryId()));
        }

        if(getEgressCountryId().size() > 0 && getEgressCountryId().get(0) > 0) {
            where.and(qCdrStatisticBase.egressCountryId.in(getEgressCountryId()));
        }

        if(getIngressOperatorId().size() > 0 && getIngressOperatorId().get(0) > 0) {
            where.and(qCdrStatisticBase.ingressOperatorId.in(getIngressOperatorId()));
        }

        if(getEgressOperatorId().size() > 0 && getEgressOperatorId().get(0) > 0) {
            where.and(qCdrStatisticBase.egressOperatorId.in(getEgressOperatorId()));
        }

        return where;
    }

    public List<Expression<?>> getGroupBy(){
		QCdrStatisticBase qCdrStatisticBase;

		if(isHourly())
			qCdrStatisticBase = QCdrStatisticHourly.cdrStatisticHourly._super;
		else
			qCdrStatisticBase = QCdrStatisticDaily.cdrStatisticDaily._super;

        List<Expression<?>> groupBy = new ArrayList<>();
        groupBy.add(qCdrStatisticBase.sigDate);

		if(isHourly())
			groupBy.add(qCdrStatisticBase.sigHour);

        if(getCustomer().size() > 0)
            groupBy.add(qCdrStatisticBase.customer);

        if(getVendor().size() > 0)
            groupBy.add(qCdrStatisticBase.vendor);

        if(getIngressCountryId().size() > 0)
            groupBy.add(qCdrStatisticBase.ingressCountryId);

        if(getEgressCountryId().size() > 0)
            groupBy.add(qCdrStatisticBase.egressCountryId);

        if(getIngressOperatorId().size() > 0)
            groupBy.add(qCdrStatisticBase.ingressOperatorId);

        if(getEgressOperatorId().size() > 0)
            groupBy.add(qCdrStatisticBase.egressOperatorId);

        return groupBy;
    }
}
