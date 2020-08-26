package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.ImportedTable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImportedTableRepository extends MongoRepository<ImportedTable, Long> {

    List<ImportedTable> findAllByUser (AppUser user);
}
