package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.Chart;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ChartRepository extends MongoRepository<Chart, Long> {
}
