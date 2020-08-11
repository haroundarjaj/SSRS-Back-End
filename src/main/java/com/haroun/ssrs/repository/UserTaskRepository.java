package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.UserTask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserTaskRepository extends MongoRepository<UserTask, Long> {

    List<UserTask> findAllByUser(AppUser user);
}
