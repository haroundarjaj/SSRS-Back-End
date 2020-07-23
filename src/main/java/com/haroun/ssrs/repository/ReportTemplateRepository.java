package com.haroun.ssrs.repository;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.ReportTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportTemplateRepository extends MongoRepository<ReportTemplate, Long> {

    List<ReportTemplate> findAllByUser (AppUser user);
}
