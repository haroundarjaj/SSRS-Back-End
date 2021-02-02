package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.WebServiceSource;
import com.haroun.ssrs.service.WebServiceSourceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3001" })
@RestController
@RequestMapping("/import/webservice")
public class WebServiceSourceController {

    private WebServiceSourceService webServiceSourceService;

    public WebServiceSourceController (WebServiceSourceService webServiceSourceService){
        this.webServiceSourceService = webServiceSourceService;
    }

    @PostMapping("/testConnection")
    public boolean testConnection (@RequestBody WebServiceSource WSs){
        return  webServiceSourceService.testConnection(WSs);
    }
}
