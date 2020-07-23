package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends MongoRepository<Workspace, Long> {

    // @Query(value="{ '_id' : ?0 }", fields="{ 'workspace' : 0}")
    Optional<Workspace> findById(long id);

    // @Query(value="{ 'title' : ?0 }")
    Workspace findByTitle(String title);

    List<Workspace> findAllByUser (AppUser user);
}
