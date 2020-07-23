package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.DatabaseSource;
import com.haroun.ssrs.service.DatabaseSourceService;
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

    @GetMapping("/getsources&{userEmail}")
    public List<DatabaseSource> getSources (@PathVariable("userEmail") String userEmail){
        return  dbSourceService.getAllDatabaseSources(userEmail);
    }

    @PostMapping("/savesource&{userEmail}")
    public void saveSource (@RequestBody DatabaseSource dbs, @PathVariable("userEmail") String userEmail) {
        dbSourceService.saveDatabaseSource(dbs, userEmail);
    }

    @PostMapping("/deletesource")
    public void deleteSource (DatabaseSource dbs) { dbSourceService.deleteDatabaseSource(dbs); }
}
