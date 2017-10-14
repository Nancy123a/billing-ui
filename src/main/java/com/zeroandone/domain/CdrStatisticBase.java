package com.zeroandone.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by shahbour on 9/30/16.
 */
@Data
@Getter
@MappedSuperclass
public class CdrStatisticBase {
  @Id
  private LocalDate sigDate;

  @Id
  private int sigHour;

  private int attempts;

  private int connected;

  private int duration;

  @Id
  private String customer;

  @Id
  private String vendor;

  @Id
  private int ingressCountryId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="ingresscountryid",insertable = false,updatable = false)
  private Country ingressCountry;

  @Id
  private int egressCountryId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="egresscountryid",insertable = false,updatable = false)
  private Country egressCountry;

  @Id
  private int ingressOperatorId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="ingressoperatorid",insertable = false,updatable = false)
  private Operator ingressOperator;

  @Id
  private int egressOperatorId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="egressoperatorid",insertable = false,updatable = false)
  private Operator egressOperator;

}
