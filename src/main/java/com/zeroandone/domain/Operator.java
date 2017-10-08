package com.zeroandone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer operatorId;

    private String operatorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="countryId")
    private Country country;

    private int obsolete;

    //@JsonFormat(pattern="dd-MM-yyyy")
    //@Type(type="org.hibernate.type.LocalDateType")
    private LocalDate activationDate;

    //@JsonFormat(pattern="dd-MM-yyyy")
    //@Type(type="org.hibernate.type.LocalDateType")
    private LocalDate deactivationDate;

    private short typeId;

}
