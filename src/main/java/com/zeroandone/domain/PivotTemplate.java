package com.zeroandone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PivotTemplate {
    @Id
    private String templateName;
    private String templateData;
}
