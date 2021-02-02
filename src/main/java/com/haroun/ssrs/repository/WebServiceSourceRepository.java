package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.WebServiceSource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WebServiceSourceRepository extends MongoRepository<WebServiceSource, Long> {

    List<WebServiceSource> findAllByUser (AppUser user);
}