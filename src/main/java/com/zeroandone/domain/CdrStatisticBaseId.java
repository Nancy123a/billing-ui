package com.zeroandone.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by shahbour on 10/11/16.
 */
@Data
public class CdrStatisticBaseId implements Serializable {
    @Id
    private LocalDate sigDate;
    @Id
    private int sigHour;
    @Id
    private String customer;
    @Id
    private String vendor;
    @Id
    private int ingressCountryId;
    @Id
    private int egressCountryId;
    @Id
    private int ingressOperatorId;
    @Id
    private int egressOperatorId;
    @Id
    private int buyCode;
    @Id
    private String buyPerMinChg;
    @Id
    private int sellCode;
    @Id
    private String sellPerMinChg;

}
