package com.zeroandone.domain;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Random {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int randomId;

    private int rangeId;

    private String  number;

    private String carrierId;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="carrierId", insertable = false,updatable = false)
    private Carrier carrier;

    private int assignmentId;

    private LocalDate lastUsed;

    public Random() {
    }

}
