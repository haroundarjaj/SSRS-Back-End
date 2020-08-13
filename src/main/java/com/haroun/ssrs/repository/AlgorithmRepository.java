package com.haroun.ssrs.repository;


import com.haroun.ssrs.model.Algorithms;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlgorithmRepository extends MongoRepository<Algorithms, String> {
}
