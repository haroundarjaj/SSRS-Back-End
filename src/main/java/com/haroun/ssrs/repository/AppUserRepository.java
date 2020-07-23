package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserRepository extends MongoRepository<AppUser, Long> {

    // @Query(value="{ 'email' : ?0 }")
    AppUser findByEmail (String email);
}
