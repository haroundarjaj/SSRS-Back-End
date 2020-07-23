package com.haroun.ssrs.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroun.ssrs.model.Chart;
import com.haroun.ssrs.model.Dashboard;
import com.haroun.ssrs.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3001" })
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{userEmail}/charts")
    public List<Chart> getDashboardCharts (@PathVariable("userEmail") String userEmail) {
        return dashboardService.getDashboardCharts(userEmail);
    }

    @PostMapping("/save&{userEmail}")
    public boolean updateDashboard (@RequestBody List<Object> objects, @PathVariable("userEmail") String userEmail) {
        ObjectMapper mapper = new ObjectMapper();
        Dashboard dashboard = mapper.convertValue(objects.get(0), Dashboard.class);
        List<Chart> charts = mapper.convertValue(objects.get(1), new TypeReference<List<Chart>>(){});
        return dashboardService.updateDashboard(dashboard, charts, userEmail);
    }

    @GetMapping("/data/getbyrows/{tableName}&{rowsNumber}")
    public List<Object> getTableDataByRows (@PathVariable("tableName") String tableName, @PathVariable("rowsNumber") int rowsNumber ) {
        return dashboardService.getTableDataByRows(tableName, rowsNumber);
    }

}
