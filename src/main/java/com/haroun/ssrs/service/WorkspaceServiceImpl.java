package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.Chart;
import com.haroun.ssrs.model.Workspace;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.repository.ChartRepository;
import com.haroun.ssrs.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    ChartRepository chartRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public List<Workspace> getAllWorkspaces(String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        List<Workspace> workspaces = new ArrayList<>(workspaceRepository.findAllByUser(user));
        Collections.reverse(workspaces);
        return workspaces;
    }

    @Override
    public List<Chart> getTemplateCharts(long id) {
        System.out.println(id);
        Workspace workspace = workspaceRepository.findById(id).get();
        System.out.println(workspace);
        List<Chart> charts = workspace.getCharts();
        System.out.println(charts);
        return charts;
    }

    @Override
    public boolean checkExistWorkspace(String title, String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        List<Workspace> workspaces = workspaceRepository.findAllByUser(user);
        System.out.println(workspaces);
        for (Workspace workspace : workspaces) {
            if (workspace.getTitle().equals(title)) {
                System.out.println(workspace.getTitle());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteWorkspace(Workspace workspace) {
        workspace.getCharts().forEach(chart -> chartRepository.delete(chart));
        workspaceRepository.delete(workspace);
        return true;
    }

    @Override
    public boolean saveWorkspace(Workspace workspace, List<Chart> charts, String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        workspace.setWorkspaceId(sequenceGenerator.generateSequence(workspace.SEQUENCE_NAME));
        charts.forEach(chart -> {
            chart.setChartId(sequenceGenerator.generateSequence(chart.SEQUENCE_NAME));
        });
        workspace.setCharts(charts);
        workspace.setUser(user);
        workspaceRepository.save(workspace);
        chartRepository.saveAll(charts);
        return true;
    }

    @Override
    public boolean updateWorkspace(Workspace workspace, List<Chart> charts) {
        Workspace workspace1 = workspaceRepository.findByTitle(workspace.getTitle());
        workspace1.getCharts().forEach(chart -> chartRepository.delete(chart));
        charts.forEach(chart -> {
            chart.setChartId(sequenceGenerator.generateSequence(chart.SEQUENCE_NAME));
        });
        workspace1.setCharts(charts);
        workspaceRepository.save(workspace1);
        chartRepository.saveAll(charts);
        return true;
    }
}
