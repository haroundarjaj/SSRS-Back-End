package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppRole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppRoleRepository extends MongoRepository<AppRole, Long> {

    //@Query(value="{ 'role' : ?0 }")
    AppRole findByRole(String role);
}
