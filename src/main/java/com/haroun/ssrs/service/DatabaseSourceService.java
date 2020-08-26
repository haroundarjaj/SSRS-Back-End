package com.haroun.ssrs.service;

import com.haroun.ssrs.model.DatabaseSource;

import java.util.List;

public interface DatabaseSourceService {

    Boolean testConnection(DatabaseSource dbs);

    List<String> getTables(DatabaseSource dbs);

    List<Object> getTableData(DatabaseSource dbs, String table);

    List<DatabaseSource> getAllDatabaseSources(String userEmail);

    void saveDatabaseSource(DatabaseSource dbs, String userEmail);

    Boolean deleteDatabaseSource(long id);
}
