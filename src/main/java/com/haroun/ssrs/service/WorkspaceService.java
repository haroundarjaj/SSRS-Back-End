package com.haroun.ssrs.service;

import com.haroun.ssrs.model.Chart;
import com.haroun.ssrs.model.Workspace;

import java.util.List;

public interface WorkspaceService {

    List<Workspace> getAllWorkspaces (String userEmail);

    Boolean saveWorkspace (Workspace workspace, List<Chart> charts, String userEmail);

    Boolean updateWorkspace (Workspace workspace, List<Chart> charts, String own);

    List<Chart> getTemplateCharts(long id);

    Boolean checkExistWorkspace(String title);

    Boolean deleteWorkspace (long id);
}
