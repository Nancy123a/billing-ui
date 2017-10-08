package com.zeroandone.repository;

import com.zeroandone.domain.CDR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDRRepository extends JpaRepository<CDR,Long> {
}
