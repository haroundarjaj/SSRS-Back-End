package com.haroun.ssrs.controller;

import com.haroun.ssrs.service.DatawarehouseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/datawarehouse")
public class DatawarehouseController {

    private DatawarehouseService datawarehouseService;

    public DatawarehouseController (DatawarehouseService datawarehouseService) {
        this.datawarehouseService = datawarehouseService;
    }

    @PostMapping("/load/{tableName}")
    public boolean LoadData (@RequestBody List<Object> data, @PathVariable("tableName") String name) {
        return datawarehouseService.LoadData(data, name);
    }

    @GetMapping("/tables")
    public List<String> getTables () {
        return datawarehouseService.getTables();
    }

    @GetMapping("/data/{tableName}")
    public List<Object> getTableData (@PathVariable("tableName") String tableName) {
        return datawarehouseService.getTableData(tableName);
    }

    @GetMapping("/data/getbyrows/{tableName}&{rowsNumber}")
    public List<Object> getTableDataByRows (@PathVariable("tableName") String tableName, @PathVariable("rowsNumber") int rowsNumber ) {
        return datawarehouseService.getTableDataByRows(tableName, rowsNumber);
    }

    @GetMapping("/data/getbyrange/{tableName}&{firstLimit}&{lastLimit}")
    public List<Object> getTableDataByRange (@PathVariable("tableName") String tableName, @PathVariable("firstLimit") int firstLimit, @PathVariable("lastLimit") int lastLimit ) {
        return datawarehouseService.getTableDataByRange(tableName, firstLimit, lastLimit);
    }

}
