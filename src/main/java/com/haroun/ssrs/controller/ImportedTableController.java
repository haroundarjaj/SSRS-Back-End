package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.ImportedTable;
import com.haroun.ssrs.service.ImportedTableService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3001" })
@RestController
@RequestMapping("/importedTable")
public class ImportedTableController {

    ImportedTableService importedTableService;

    public ImportedTableController(ImportedTableService importedTableService) {
        this.importedTableService = importedTableService;
    }

    @GetMapping("/getUserTables")
    public List<ImportedTable> getUserTables (Authentication authentication) {
        return importedTableService.getUserTables(authentication.getName());
    }

    @GetMapping("/getAll")
    public List<ImportedTable> getAll () {
        return importedTableService.getAll();
    }

    @PostMapping("/save")
    public Boolean saveImportedTable (@RequestBody ImportedTable importedTable, Authentication authentication) {
        return importedTableService.saveImportedTable(importedTable, authentication.getName());
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteTask (@PathVariable("id") long id) {
        return importedTableService.deleteTable(id);
    }
}
