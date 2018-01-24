package com.zeroandone.repository;

import com.zeroandone.domain.UserOperator;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserOperatorRepository extends CrudRepository<UserOperator,Integer> {
    List<UserOperator> findAllByUsername(String username);
}
