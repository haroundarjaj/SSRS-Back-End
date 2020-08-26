package com.haroun.ssrs.repository;


import com.haroun.ssrs.model.Algorithms;
import com.haroun.ssrs.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlgorithmRepository extends MongoRepository<Algorithms, Long> {
    List<Algorithms> findAllByUser(AppUser appUser);
}
