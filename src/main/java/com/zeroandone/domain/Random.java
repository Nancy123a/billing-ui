package com.zeroandone.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Random {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int randomId;

    private int rangeId;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="rangeId", insertable = false,updatable = false)
    private MRange mRange;

    private int number;

    private String carrierId;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="carrierId", insertable = false,updatable = false)
    private Carrier carrier;

    private int assignmentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignmentId", insertable = false, updatable = false)
    private Assignment assignment;

    private LocalDate lastUsed;


}
