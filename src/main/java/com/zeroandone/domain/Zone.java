package com.zeroandone.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zoneId")
    private int zoneId;

    @Column(name = "zoneName")
    private String zoneName;

    @Column(name = "code")
    private int code;

    @Column(name = "rate")
    private double rate;

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "{" +
                "zoneId=" + zoneId +
                ", zoneName='" + zoneName + '\'' +
                ", code=" + code +
                ", rate=" + rate +
                '}';
    }
}
