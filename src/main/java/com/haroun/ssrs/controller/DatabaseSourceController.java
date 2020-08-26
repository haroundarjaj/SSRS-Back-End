package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.DatabaseSource;
import com.haroun.ssrs.service.DatabaseSourceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3001" })
@RestController
@RequestMapping("/import/database")
public class DatabaseSourceController {

    private DatabaseSourceService dbSourceService;

    public DatabaseSourceController (DatabaseSourceService dbSourceService){
        this.dbSourceService = dbSourceService;
    }

    @PostMapping("/testConnection")
    public boolean testConnection (@RequestBody DatabaseSource dbs){
        return  dbSourceService.testConnection(dbs);
    }

    @PostMapping("/tables")
    public List<String> getTables (@RequestBody DatabaseSource dbs) {
        return  dbSourceService.getTables(dbs);
    }

    @PostMapping("/tables/{table}")
    public List<Object> getTableData (@RequestBody DatabaseSource dbs, @PathVariable("table") String tableName) {
        return  dbSourceService.getTableData(dbs, tableName);
    }

    @GetMapping("/getSources")
    public List<DatabaseSource> getSources (Authentication authentication){
        return  dbSourceService.getAllDatabaseSources(authentication.getName());
    }

    @PostMapping("/saveSource")
    public void saveSource (@RequestBody DatabaseSource dbs, Authentication authentication) {
        dbSourceService.saveDatabaseSource(dbs, authentication.getName());
    }

    @DeleteMapping("/deleteSource/{id}")
    public Boolean deleteSource (@PathVariable("id") long id) { return dbSourceService.deleteDatabaseSource(id); }
}
