package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppUserRepository extends MongoRepository<AppUser, Long> {

    // @Query(value="{ 'email' : ?0 }")
    AppUser findByEmail (String email);

    List<AppUser> findAppUsersByActivatedFalse ();
}
