package com.zeroandone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class OperatorCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int operatorCodeId;

    private int code;

    private String operatorName;

    private int operatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="operatorId",insertable = false,updatable = false)
    private Operator operator;

    private boolean enabled;

    //@JsonFormat(pattern="dd-MM-yyyy")
    //@Type(type="org.hibernate.type.LocalDateType")
    private LocalDate activationDate;

    //@JsonFormat(pattern="dd-MM-yyyy")
    //@Type(type="org.hibernate.type.LocalDateType")
    private LocalDate deactivationDate;

    private int countryId;

}
