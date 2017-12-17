package com.zeroandone.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScvdReport {

    private LocalDate sigDate;
    private int sigHour;
    private String customer;
    private String vendor;
    private String callingNumber;
    private String calledNumber;
    private long attempts;

}
