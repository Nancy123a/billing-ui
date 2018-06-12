package com.zeroandone.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private int operatorId;

    private String operatorName;

    private int countryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="countryId" , insertable = false,updatable = false)
    private Country country;


    private int obsolete;

    private LocalDate activationDate;

    private LocalDate deactivationDate;

    private short typeId;

}
