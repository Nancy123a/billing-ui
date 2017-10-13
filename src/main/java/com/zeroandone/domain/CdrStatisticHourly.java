package com.zeroandone.domain;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;


@IdClass(CdrStatisticBaseId.class)
@Entity
public class CdrStatisticHourly extends CdrStatisticBase {

}
