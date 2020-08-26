package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.ImportedTable;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.repository.ImportedTableRepository;
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

@Service
@Transactional
public class ImportedTableServiceImpl implements ImportedTableService {

    @Autowired
    ImportedTableRepository importedTableRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public Boolean saveImportedTable(ImportedTable table, String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        table.setTableId(sequenceGenerator.generateSequence(table.SEQUENCE_NAME));;
        table.setUser(user);
        importedTableRepository.save(table);
        return true;
    }

    @Override
    public List<ImportedTable> getUserTables(String email) {
        List<ImportedTable> list = new ArrayList<>();
        AppUser user = appUserRepository.findByEmail(email);
        list = importedTableRepository.findAllByUser(user);
        return list;
    }

    @Override
    public List<ImportedTable> getAll() {
        List<ImportedTable> list = importedTableRepository.findAll();
        return list;
    }

    @Override
    public Boolean deleteTable(long id) {
        return importedTableRepository.findById(id).map(table ->{
            String URI = "mongodb://localhost/";
            MongoClient mongoClient = MongoClients.create(URI);
            MongoDatabase database = mongoClient.getDatabase("DataWareHouse");
            MongoCollection<Document> collection = database.getCollection(table.getName());
            collection.drop();
            importedTableRepository.delete(table);
            return true;
        }).orElseThrow(()-> new ExceptionMessage("Impossible to delete task"));
    }
}
