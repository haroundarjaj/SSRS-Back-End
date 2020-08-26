package com.haroun.ssrs.repository;


import com.haroun.ssrs.model.ShareWith;
import com.haroun.ssrs.model.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShareWithRepository extends MongoRepository<ShareWith, Long> {
    List<ShareWith> findAllByEmailsContains(String email);
    ShareWith findByWorkspaceAndAndEmailsContains(Workspace workspace, String mail);
    ShareWith findByWorkspace(Workspace workspace);

}
