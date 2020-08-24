package com.haroun.ssrs.service;

import com.haroun.ssrs.model.Chart;
import com.haroun.ssrs.model.Workspace;

import java.util.List;

public interface WorkspaceService {

    List<Workspace> getAllWorkspaces (String userEmail);

    boolean saveWorkspace (Workspace workspace, List<Chart> charts, String userEmail);

    boolean updateWorkspace (Workspace workspace, List<Chart> charts, String own);

    List<Chart> getTemplateCharts(long id);

    boolean checkExistWorkspace(String title);

    boolean deleteWorkspace (Workspace workspace);
}
