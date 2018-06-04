package com.zeroandone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter

public class Ranges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int range_id;

    private String from;

    private String to;

    private String carrierId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="carrierId", insertable = false,updatable = false)
    private Carrier carrier;

    private String rangeType;

    private int assignment_id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="assignment_id", insertable = false,updatable = false)
    private Assignment assignment;
}
