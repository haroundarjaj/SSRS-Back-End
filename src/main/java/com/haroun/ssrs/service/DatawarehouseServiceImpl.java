package com.haroun.ssrs.service;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional
public class DatawarehouseServiceImpl implements DatawarehouseService {
    @Override
    public boolean LoadData(List<Object> tableData, String tableName) {
        String URI = "mongodb://localhost/";
        MongoClient mongoClient = MongoClients.create(URI);
        MongoDatabase database = mongoClient.getDatabase("DataWareHouse");
        MongoCollection<Document> collection = database.getCollection(tableName);
        List<Document> list = new ArrayList<>();
        tableData.forEach((obj) -> {
            Document doc = Document.parse(new Gson().toJson(obj));
            list.add(doc);
        });
        collection.insertMany(list);
        mongoClient.close();
        return true;
    }

    @Override
    public List<String> getTables() {
        List<String> listTables = new ArrayList<>();
        try {
            String URI = "mongodb://localhost/";
            MongoClient mongoClient = MongoClients.create(URI);
            MongoDatabase database = mongoClient.getDatabase("DataWareHouse");
            for (String name : database.listCollectionNames()) {
                listTables.add(name);
            }
            mongoClient.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listTables;
    }

    @Override
    public List<Object> getTableData(String tableName) {
        List<Object> tableData = new ArrayList<>();
        try {
            String URI = "mongodb://localhost/";
            MongoClient mongoClient = MongoClients.create(URI);
            MongoDatabase database = mongoClient.getDatabase("DataWareHouse");
            MongoCollection<Document> collection = database.getCollection(tableName);
            collection.find().forEach((Consumer<Document>) obj -> {
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

    @Override
    public List<Object> getTableDataByRows(String tableName, int rowsNumber) {
        List<Object> tableData = new ArrayList<>();
        try {
            String URI = "mongodb://localhost/";
            MongoClient mongoClient = MongoClients.create(URI);
            MongoDatabase database = mongoClient.getDatabase("DataWareHouse");
            MongoCollection<Document> collection = database.getCollection(tableName);
            collection.find().limit(rowsNumber).forEach((Consumer<Document>) obj -> {
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

    @Override
    public List<Object> getTableDataByRange(String tableName, int firstLimit, int lastLimit) {
        List<Object> tableData = new ArrayList<>();
        try {
            String URI = "mongodb://localhost/";
            MongoClient mongoClient = MongoClients.create(URI);
            MongoDatabase database = mongoClient.getDatabase("DataWareHouse");
            MongoCollection<Document> collection = database.getCollection(tableName);
            collection.find().skip(firstLimit-1).limit(lastLimit-firstLimit+1).forEach((Consumer<Document>) obj -> {
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
