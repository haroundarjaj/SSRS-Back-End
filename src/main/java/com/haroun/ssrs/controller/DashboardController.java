package com.haroun.ssrs.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroun.ssrs.model.Chart;
import com.haroun.ssrs.model.Dashboard;
import com.haroun.ssrs.service.DashboardService;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/charts")
    public List<Chart> getDashboardCharts (Authentication authentication) {
        return dashboardService.getDashboardCharts(authentication.getName());
    }

    @PostMapping("/save")
    public boolean updateDashboard (@RequestBody List<Object> objects, Authentication authentication) {
        ObjectMapper mapper = new ObjectMapper();
        Dashboard dashboard = mapper.convertValue(objects.get(0), Dashboard.class);
        List<Chart> charts = mapper.convertValue(objects.get(1), new TypeReference<List<Chart>>(){});
        return dashboardService.updateDashboard(dashboard, charts, authentication.getName());
    }

    @GetMapping("/data/getbyrows/{tableName}&{rowsNumber}")
    public List<Object> getTableDataByRows (@PathVariable("tableName") String tableName, @PathVariable("rowsNumber") int rowsNumber ) {
        return dashboardService.getTableDataByRows(tableName, rowsNumber);
    }

}
