package com.sbz.ipfilter.infrastructure.persistence.repository;

import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends CrudRepository<RuleEntity, Long>, PagingAndSortingRepository<RuleEntity, Long> {
    List<RuleEntity> findByAllow(Boolean allow);
}
