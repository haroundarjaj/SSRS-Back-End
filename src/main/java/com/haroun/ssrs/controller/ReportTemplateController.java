package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.ReportTemplate;
import com.haroun.ssrs.service.ReportTemplateService;
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

    @GetMapping("/getAll&{userEmail}")
    public List<ReportTemplate> getAllTemplates (@PathVariable("userEmail") String userEmail) {
        return reportTemplateService.getAllTemplates(userEmail);
    }

    @PostMapping("/save&{userEmail}")
    public void saveReportTemplate (@RequestBody ReportTemplate reportTemplate, @PathVariable("userEmail") String userEmail) {
        reportTemplateService.insertTemplate(reportTemplate, userEmail);
    }

    @PostMapping("/delete")
    public void deleteReportTemplate (@RequestBody ReportTemplate reportTemplate) {
        reportTemplateService.deleteTemplate(reportTemplate);
    }

}
