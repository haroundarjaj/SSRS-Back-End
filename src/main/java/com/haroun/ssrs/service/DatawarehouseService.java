package com.haroun.ssrs.service;

import java.util.List;

public interface DatawarehouseService {

    boolean LoadData(List<Object> tableData, String tableName);

    List<String> getTables();

    List<Object> getTableData(String tableName);

    List<Object> getTableDataByRows(String tableName, int rowsNumber);

    List<Object> getTableDataByRange(String tableName, int firstLimit, int lastLimit);

}
