package com.zeroandone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Getter
@Setter

public class Range {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rangeId;

    private String carrierId;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="carrierId", insertable = false,updatable = false)
    private Carrier carrier;

    private String rangeType;

    private String _To;

    private String _From;

    private int assignmentId;

    private int count;

    public Range() {
    }

    public Range(String carrierId, String rangeType, String _To, String _From, int assignmentId, int count) {
        this.carrierId = carrierId;
        this.rangeType = rangeType;
        this._To = _To;
        this._From = _From;
        this.assignmentId = assignmentId;
        this.count = count;
    }
}