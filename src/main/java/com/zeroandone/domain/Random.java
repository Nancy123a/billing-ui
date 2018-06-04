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
    private int random_id;


    private int range_id;


    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="range_id", insertable = false,updatable = false)
    private Ranges ranges;

    private int number;

    private String carrierId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="carrierId", insertable = false,updatable = false)
    private Carrier carrier;

    private int assignment_id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="assignment_id", insertable = false,updatable = false)
    private Assignment assignment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate lastUsed;
}
