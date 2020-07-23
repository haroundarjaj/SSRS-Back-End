package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.Dashboard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DashboardRepository extends MongoRepository<Dashboard, Long> {

    Optional<Dashboard> findByUser(AppUser user);
}
