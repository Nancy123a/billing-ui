package com.zeroandone.domain;

import com.zeroandone.repository.CountryRepository;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CountryCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryCodeId;

    private int code;

    private int countryId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="countryId", insertable = false,updatable = false)
    private Country country;

}
