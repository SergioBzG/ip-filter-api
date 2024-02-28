package com.sbz.ipfilter.infrastructure.persistence.repository;

import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Long> {
}
