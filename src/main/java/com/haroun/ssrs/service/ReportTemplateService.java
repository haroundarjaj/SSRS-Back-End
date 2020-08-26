package com.haroun.ssrs.service;

import com.haroun.ssrs.model.ReportTemplate;

import java.util.List;

public interface ReportTemplateService {

    List<ReportTemplate> getAllTemplates (String userEmail);

    String insertTemplate (ReportTemplate template, String userEmail);

    Boolean deleteTemplate (long id);

}
