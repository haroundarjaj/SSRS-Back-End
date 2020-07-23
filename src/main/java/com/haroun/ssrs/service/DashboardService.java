package com.haroun.ssrs.service;

import com.haroun.ssrs.model.Chart;
import com.haroun.ssrs.model.Dashboard;

import java.util.List;

public interface DashboardService {

    boolean updateDashboard (Dashboard dashboard, List<Chart> charts, String userEmail);

    List<Chart> getDashboardCharts(String userEmail);

    List<Object> getTableDataByRows(String tableName, int rowsNumber);

}
