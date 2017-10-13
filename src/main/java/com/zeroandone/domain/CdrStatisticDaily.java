package com.zeroandone.domain;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Created by shahbour on 9/30/16.
 */
@IdClass(CdrStatisticBaseId.class)
@Entity
public class CdrStatisticDaily extends CdrStatisticBase {

}
