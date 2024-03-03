package com.sbz.ipfilter.infrastructure.persistence.repository;

import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Long>, PagingAndSortingRepository<Rule, Long> {
    List<Rule> findByAllow(Boolean allow);
}
