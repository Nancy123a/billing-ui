package com.zeroandone.repository;

import com.zeroandone.domain.CDR;
import com.zeroandone.domain.CdrStatisticBaseId;
import com.zeroandone.domain.CdrStatisticDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDRRepository extends JpaRepository<CDR,Long>,QueryDslPredicateAndProjectionExecutor<CDR,Long> {
}
