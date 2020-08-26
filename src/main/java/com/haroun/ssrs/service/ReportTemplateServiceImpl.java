package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.ReportTemplate;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.repository.ReportTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ReportTemplateServiceImpl implements ReportTemplateService {

    @Autowired
    ReportTemplateRepository reportTemplateRepository;

    @Autowired
    AppUserRepository  appUserRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public List<ReportTemplate> getAllTemplates(String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        List<ReportTemplate> reportTemplates = new ArrayList<>(reportTemplateRepository.findAllByUser(user));
        Collections.reverse(reportTemplates);
        return reportTemplates;
    }

    @Override
    public String insertTemplate(ReportTemplate template, String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        template.setUser(user);
        template.setReportTemplateId(sequenceGenerator.generateSequence(template.SEQUENCE_NAME));
        reportTemplateRepository.save(template);
        return "done";
    }

    @Override
    public Boolean deleteTemplate(long id) {
        return reportTemplateRepository.findById(id).map(al ->{
            reportTemplateRepository.delete(al);
            return true;
        }).orElseThrow(()-> new ExceptionMessage("Impossible to delete template"));
    }
}
