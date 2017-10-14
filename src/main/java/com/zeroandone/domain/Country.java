package com.zeroandone.domain;

import com.zeroandone.repository.CountryRepository;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Country {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;


    private String countryName;


}