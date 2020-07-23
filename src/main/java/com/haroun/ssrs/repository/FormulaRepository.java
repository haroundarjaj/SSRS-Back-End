package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.Formula;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FormulaRepository extends MongoRepository<Formula, Long> {

    List<Formula> findAllByUser(AppUser user);
}
