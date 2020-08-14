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

    @PostMapping("/testconnection")
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

    @GetMapping("/getsources")
    public List<DatabaseSource> getSources (Authentication authentication){
        return  dbSourceService.getAllDatabaseSources(authentication.getName());
    }

    @PostMapping("/savesource")
    public void saveSource (@RequestBody DatabaseSource dbs, Authentication authentication) {
        dbSourceService.saveDatabaseSource(dbs, authentication.getName());
    }

    @PostMapping("/deletesource")
    public void deleteSource (@RequestBody DatabaseSource dbs) { dbSourceService.deleteDatabaseSource(dbs); }
}
