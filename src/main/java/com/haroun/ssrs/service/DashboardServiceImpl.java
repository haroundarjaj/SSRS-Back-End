package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.Chart;
import com.haroun.ssrs.model.Dashboard;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.repository.ChartRepository;
import com.haroun.ssrs.repository.DashboardRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    DashboardRepository dashboardRepository;

    @Autowired
    ChartRepository chartRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;


    @Override
    public boolean updateDashboard(Dashboard dashboard, List<Chart> charts, String userEmail) {
        AppUser user= appUserRepository.findByEmail(userEmail);
        Optional<Dashboard> dashb = dashboardRepository.findByUser(user);
        if(dashb.isPresent()){
            Dashboard dashboard1 = dashb.get();
            dashboard1.setLastEditTime(dashboard.getLastEditTime());
            dashboard1.getCharts().forEach(chart -> chartRepository.delete(chart));
            charts.forEach(chart -> {
                chart.setChartId(sequenceGenerator.generateSequence(chart.SEQUENCE_NAME));
            });
            dashboard1.setCharts(charts);
            dashboardRepository.save(dashboard1);
            chartRepository.saveAll(charts);
        } else {
            dashboard.setDashboardId(sequenceGenerator.generateSequence(dashboard.SEQUENCE_NAME));
            charts.forEach(chart -> {
                chart.setChartId(sequenceGenerator.generateSequence(chart.SEQUENCE_NAME));
            });
            dashboard.setCharts(charts);
            dashboard.setUser(user);
            dashboardRepository.save(dashboard);
            chartRepository.saveAll(charts);
        }

        return true;
    }

    @Override
    public List<Chart> getDashboardCharts(String userEmail) {
        AppUser user= appUserRepository.findByEmail(userEmail);
        System.out.println(user);
        Optional<Dashboard> dashboard = dashboardRepository.findByUser(user);
        if(dashboard.isPresent()){
            List<Chart> charts = dashboard.get().getCharts();
            return charts;
        }
        else {
            return null;
        }
    }

    @Override
    public List<Object> getTableDataByRows(String tableName, int rowsNumber) {
        List<Object> tableData = new ArrayList<>();
        try {
            String URI = "mongodb://localhost/";
            MongoClient mongoClient = MongoClients.create(URI);
            MongoDatabase database = mongoClient.getDatabase("DataWareHouse");
            MongoCollection<Document> collection = database.getCollection(tableName);
            collection.find().skip((int) (collection.countDocuments() - rowsNumber)).forEach((Consumer<Document>) obj -> {
                obj.remove("_class");
                obj.remove("_id");
                tableData.add(obj);
            });
            mongoClient.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tableData;
    }
}
