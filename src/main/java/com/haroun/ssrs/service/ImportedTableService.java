package com.haroun.ssrs.service;

import com.haroun.ssrs.model.ImportedTable;

import java.util.List;

public interface ImportedTableService {

    Boolean saveImportedTable (ImportedTable table, String userEmail);

    List<ImportedTable> getUserTables(String email);

    List<ImportedTable> getAll();

    Boolean deleteTable(long id);

}
