package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.ReportTemplate;
import com.haroun.ssrs.service.ReportTemplateService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3001" })
@RestController
@RequestMapping("/report")
public class ReportTemplateController {

    ReportTemplateService reportTemplateService;

    public ReportTemplateController(ReportTemplateService reportTemplateService) {
        this.reportTemplateService = reportTemplateService;
    }

    @GetMapping("/getAll")
    public List<ReportTemplate> getAllTemplates (Authentication authentication) {
        return reportTemplateService.getAllTemplates(authentication.getName());
    }

    @PostMapping("/save")
    public void saveReportTemplate (@RequestBody ReportTemplate reportTemplate, Authentication authentication) {
        reportTemplateService.insertTemplate(reportTemplate, authentication.getName());
    }

    @PostMapping("/delete")
    public void deleteReportTemplate (@RequestBody ReportTemplate reportTemplate) {
        reportTemplateService.deleteTemplate(reportTemplate);
    }

}
