package com.zeroandone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Carrier {
    @Id
    private String carrierId;
    private String carrierName;
}
