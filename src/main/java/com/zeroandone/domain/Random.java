package com.zeroandone.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.font.NumericShaper;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Random {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int randomId;


    private int rangeId;


    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rangeId", insertable = false,updatable = false)
    private Ranges ranges;

    private int number;

    private String carrierId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="carrierId", insertable = false,updatable = false)
    private Carrier carrier;

    private int assignmentId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="assignmentId", insertable = false,updatable = false)
    private Assignment assignment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate lastUsed;
}
