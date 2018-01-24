package com.zeroandone.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserOperator {

    @Id
    private int id;
    private String username;
    private int operatorId;
}
