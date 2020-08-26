package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.DatabaseSource;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.repository.DatabaseSourceRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional
public class DatabaseSourceServiceImpl implements DatabaseSourceService {

    @Autowired
    DatabaseSourceRepository dbsourceRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public Boolean testConnection(DatabaseSource dbs) {
        String url = "";
        String driverClass = "";
        boolean isConnected = false;
        List<String> listTables = new ArrayList<>();
        DataSource dsbuilder = null;
        System.out.print("dd => " + dbs);
        if (dbs.getType().equals("SQLServer")) {
            url = "jdbc:sqlserver://" + dbs.getHost() + ":"+ dbs.getPort() +";databaseName=" + dbs.getDatabaseName() + ";";
            driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            dsbuilder = DataSourceBuilder.create()
                    .username(dbs.getUsername())
                    .password(dbs.getPassword())
                    .url(url)
                    .driverClassName(driverClass)
                    .build();

            try {
                isConnected = true;
                dsbuilder.getConnection();
            } catch (SQLException e) {
                isConnected = false;
            }
        }
        if (dbs.getType().equals("MongoDB")) {
            try {
                String URI =
                        "mongodb://" +
                                dbs.getUsername() +
                                ":" + dbs.getPassword() +
                                "@" + dbs.getHost() + ":"
                                + dbs.getPort() + "/";
                MongoClient m = MongoClients.create(URI);
                isConnected = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                isConnected = false;
            }
        }
        return isConnected;
    }

    @Override
    public List<String> getTables(DatabaseSource dbs) {
        String url = "";
        String driverClass = "";
        List<String> listTables = new ArrayList<>();
        DataSource dsbuilder = null;
        if (dbs.getType().equals("SQLServer")) {
            url = "jdbc:sqlserver://" + dbs.getHost() + ":"+ dbs.getPort() +";databaseName=" + dbs.getDatabaseName() + ";";
            driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            dsbuilder = DataSourceBuilder.create()
                    .username(dbs.getUsername())
                    .password(dbs.getPassword())
                    .url(url)
                    .driverClassName(driverClass)
                    .build();

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dsbuilder);
            jdbcTemplate.query(
                    "select TABLE_NAME from INFORMATION_SCHEMA.TABLES",
                    (rs, rowNum) -> listTables.add(rs.getString("TABLE_NAME"))
            );
        }
        if (dbs.getType().equals("MongoDB")){
            try {
                String URI =
                        "mongodb://" + dbs.getHost() + ":"
                                + dbs.getPort() + "/";
                MongoClient mongoClient = MongoClients.create(URI);
                MongoDatabase database = mongoClient.getDatabase(dbs.getDatabaseName());
                for (String name : database.listCollectionNames()) {
                    listTables.add(name);
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        Collections.reverse(listTables);
        return  listTables;
    }

    @Override
    public List<Object> getTableData(DatabaseSource dbs, String table) {
        List<Object> tableData = new ArrayList<>();
        String url = "";
        String driverClass = "";
        DataSource dsbuilder = null;
        if (dbs.getType().equals("SQLServer")) {
            url = "jdbc:sqlserver://" + dbs.getHost() + ":"+ dbs.getPort() +";databaseName=" + dbs.getDatabaseName() + ";";
            driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            dsbuilder = DataSourceBuilder.create()
                    .username(dbs.getUsername())
                    .password(dbs.getPassword())
                    .url(url)
                    .driverClassName(driverClass)
                    .build();

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dsbuilder);
            String sqlQuery = "select * from " + table;
            tableData.addAll(jdbcTemplate.queryForList(sqlQuery));
        }
        if (dbs.getType().equals("MongoDB")){
            try {
                String URI =
                        "mongodb://" + dbs.getHost() + ":"
                                + dbs.getPort()+ "/";
                MongoClient mongoClient = MongoClients.create(URI);
                MongoDatabase database = mongoClient.getDatabase(dbs.getDatabaseName());
                MongoCollection<Document> h = database.getCollection(table);
                h.find().limit(1000).forEach((Consumer<Document>) d -> {
                    d.remove("_class");
                    tableData.add(d);
                });

            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return  tableData;
    }

    @Override
    public List<DatabaseSource> getAllDatabaseSources(String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        List<DatabaseSource> databaseSources = new ArrayList<>(dbsourceRepository.findAllByUser(user));
        Collections.reverse(databaseSources);
        return databaseSources;
    }

    @Override
    public void saveDatabaseSource(DatabaseSource dbs, String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        dbs.setUser(user);
        dbs.setDatabaseSourceId(sequenceGenerator.generateSequence(dbs.SEQUENCE_NAME));
        dbsourceRepository.save(dbs);
    }

    @Override
    public Boolean deleteDatabaseSource(long id) {
        return dbsourceRepository.findById(id).map(al ->{
            dbsourceRepository.delete(al);
            return true;
        }).orElseThrow(()-> new ExceptionMessage("Impossible to delete database source"));
    }
}
