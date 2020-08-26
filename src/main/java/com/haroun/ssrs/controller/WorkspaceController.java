package com.haroun.ssrs.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroun.ssrs.model.Chart;
import com.haroun.ssrs.model.Workspace;
import com.haroun.ssrs.service.WorkspaceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3001" })
@RestController
@RequestMapping("/workspace")
public class WorkspaceController {

    WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping("/getAll")
    public List<Workspace> getAllWorkspaces (Authentication authentication) {
        return workspaceService.getAllWorkspaces(authentication.getName());
    }

    @PostMapping("/save")
    public Boolean saveWorkspace (@RequestBody List<Object> objects, Authentication authentication) {
        ObjectMapper mapper = new ObjectMapper();
        Workspace workspace = mapper.convertValue(objects.get(0), Workspace.class);
        List<Chart> charts = mapper.convertValue(objects.get(1), new TypeReference<List<Chart>>(){});
        System.out.println("email");
        System.out.println(authentication.getName());
        return workspaceService.saveWorkspace(workspace, charts, authentication.getName());
    }

    @GetMapping("/{id}/charts")
    public List<Chart> getTemplateCharts (@PathVariable("id") long id) {
        return workspaceService.getTemplateCharts(id);
    }

    @GetMapping("/checkExistence/{title}")
    public Boolean checkExistWorkspace (@PathVariable("title") String title, Authentication authentication) {
        return workspaceService.checkExistWorkspace(title);
    }

    @PostMapping("/update")
    public Boolean updateWorkspace (@RequestBody List<Object> objects, Authentication authentication) {
        ObjectMapper mapper = new ObjectMapper();
        Workspace workspace = mapper.convertValue(objects.get(0), Workspace.class);
        List<Chart> charts = mapper.convertValue(objects.get(1), new TypeReference<List<Chart>>(){});
        return workspaceService.updateWorkspace(workspace, charts, authentication.getName());
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteWorkspace (@PathVariable("id") long id) {
        return workspaceService.deleteWorkspace(id);
    }
}
