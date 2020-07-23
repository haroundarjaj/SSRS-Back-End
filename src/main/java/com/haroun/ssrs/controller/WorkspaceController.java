package com.haroun.ssrs.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroun.ssrs.model.Chart;
import com.haroun.ssrs.model.Workspace;
import com.haroun.ssrs.service.WorkspaceService;
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

    @GetMapping("/getAll&{userEmail}")
    public List<Workspace> getAllWorkspaces (@PathVariable("userEmail") String userEmail) {
        return workspaceService.getAllWorkspaces(userEmail);
    }

    @PostMapping("/save&{userEmail}")
    public boolean saveWorkspace (@RequestBody List<Object> objects, @PathVariable("userEmail") String userEmail) {
        ObjectMapper mapper = new ObjectMapper();
        Workspace workspace = mapper.convertValue(objects.get(0), Workspace.class);
        List<Chart> charts = mapper.convertValue(objects.get(1), new TypeReference<List<Chart>>(){});
        return workspaceService.saveWorkspace(workspace, charts, userEmail);
    }

    @GetMapping("/{id}/charts")
    public List<Chart> getTemplateCharts (@PathVariable("id") long id) {
        return workspaceService.getTemplateCharts(id);
    }

    @GetMapping("/checkExistence/{title}&{userEmail}")
    public boolean checkExistWorkspace (@PathVariable("title") String title, @PathVariable("userEmail") String userEmail) {
        return workspaceService.checkExistWorkspace(title, userEmail);
    }

    @PostMapping("/update")
    public boolean updateWorkspace (@RequestBody List<Object> objects) {
        ObjectMapper mapper = new ObjectMapper();
        Workspace workspace = mapper.convertValue(objects.get(0), Workspace.class);
        List<Chart> charts = mapper.convertValue(objects.get(1), new TypeReference<List<Chart>>(){});
        return workspaceService.updateWorkspace(workspace, charts);
    }

    @PostMapping("/delete")
    public boolean deleteWorkspace (@RequestBody Workspace workspace) {
        return workspaceService.deleteWorkspace(workspace);
    }
}
