package com.zeroandone.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class CDRStatistic implements Serializable {

	private int attempts;
	private int connected;
	@NumberFormat(style= Style.NUMBER,pattern="#,###.##")
	@Getter(AccessLevel.NONE)
	private int duration;
	private LocalDate sigDate;
	private int sigHour;
	private String customer;
	private String vendor;
	private String ingressCountryName;
	private String ingressOperatorName;
	private String egressCountryName;
	private String egressOperatorName;
	private int buyCode;
	private String buyPerMinChg;
	private int sellCode;
	private String sellPerMinChg;
	private double buyCharge;
	private double sellCharge;

	@Transient
	@JsonIgnore
	private double acd;
	@Transient
	@JsonIgnore
	private double asr;
	@Transient
	@JsonIgnore
	private double pdd;

	@JsonIgnore
	public void setSigDate(LocalDate sigDate) {
		this.sigDate = sigDate;
	}
	
	@JsonProperty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	public LocalDate getSigDate() {
		return sigDate;
	}


	@JsonProperty
	public double getCeiledDuration() {
		return (double)duration / 60;
	}

	@JsonProperty
	public double getAcd() {
		if(getCeiledDuration() > 0)
			return getCeiledDuration()/getConnected();
		else
			return 0;
	}
	@JsonProperty
	public double getAsr() {
		return ((double)getConnected() / getAttempts());
	}

	public double getGrossProfit() {
		return sellCharge - buyCharge;
	}


}
