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
    @Column(name = "sigDate")
    private LocalDate sigDate;
    @Id
    @Column(name = "sigHour")
    private int sigHour;
    @Id
    @Column(name = "customer")
    private String customer;
    @Id
    @Column(name = "vendor")
    private String vendor;
    @Id
    @Column(name = "ingresscountryid")
    private int ingressCountryId;
    @Id
    @Column(name = "egresscountryid")
    private int egressCountryId;
    @Id
    @Column(name = "ingressoperatorid")
    private int ingressOperatorId;
    @Id
    @Column(name = "egressoperatorid")
    private int egressOperatorId;

}
