package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.DatabaseSource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DatabaseSourceRepository extends MongoRepository<DatabaseSource, Long> {

    List<DatabaseSource> findAllByUser (AppUser user);
}
