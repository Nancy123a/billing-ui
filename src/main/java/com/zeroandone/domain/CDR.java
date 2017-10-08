package com.zeroandone.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
public class CDR {

    @Id
    private long cdrId;

    @Column(columnDefinition = "TINYINT")
    private int monthDay;

    @Column(name = "`day_hour`", columnDefinition = "TINYINT")
    private int dayHour;

    private LocalDateTime sigConnected;

    private LocalDateTime sigEnd;

    private int sigEndReason;

    private double sellCode;

    private double sellCharge;

    private double buyQuantizedDuration;

    private double buyCode;

    private double buyCharge;

    private String buyTo;

    @Setter(AccessLevel.NONE)
    private LocalDateTime asigInvite;

    private LocalDateTime asigPostDial;

    private String asigFrom;

    private String asigFromHost;

    private String asigTo;

    
    private String asigToHost;

    private String asigCarrierGroup;

    
    private String asigInviteStatus;

    
    private String asigRemoteIp;

    
    private String asigCallId;

    
    private String artpCodec;

    
    private String bsigFrom;

    
    private String bsigFromHost;

    
    private String bsigTo;

    
    private String bsigToHost;

    
    private String bsigCarrierGroup;

    
    private String bsigInviteStatus;

    
    private String bsigRemoteIp;

    
    private String bsigCallId;

    
    private String brtpCodec;

    
    private String buyPerMinChg;

    
    private String sellPerMinChg;

    private int ingressCountryId;
    private int ingressOperatorId;
    private int egressCountryId;
    private int egressOperatorId;

    public void setAsigInvite(LocalDateTime asigInvite) {
        this.asigInvite = asigInvite;
        this.monthDay = asigInvite.getDayOfMonth();
        this.dayHour = asigInvite.getHour();
    }
}
