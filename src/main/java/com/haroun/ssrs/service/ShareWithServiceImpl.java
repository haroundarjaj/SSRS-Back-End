package com.haroun.ssrs.service;


import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.ShareWith;
import com.haroun.ssrs.model.Workspace;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.repository.ShareWithRepository;
import com.haroun.ssrs.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShareWithServiceImpl implements ShareWithService {
    private AppUserRepository appUserRepository;
    private WorkspaceRepository workspaceRepository;
    private ShareWithRepository shareWithRepository;

    ShareWithServiceImpl(WorkspaceRepository workspaceRepository, ShareWithRepository shareWithRepository, AppUserRepository appUserRepository){
        this.workspaceRepository = workspaceRepository;
        this.shareWithRepository = shareWithRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public ShareWith shareWith(long workspaceId, ShareWith shareWith) {
        return this.workspaceRepository.findById(workspaceId).map(workspace -> {
            ShareWith shareWith2 = shareWithRepository.findByWorkspace(workspace);
            if (shareWith2 != null) {
                shareWith2.setEmails(shareWith2.getEmails().concat("-" + shareWith.getEmails()));
                shareWith2.setLastUpdate(new Date());
                return this.shareWithRepository.save(shareWith2);
            } else {
                ShareWith shareWith1 = new ShareWith();
                shareWith1.setWorkspace(workspace);
                shareWith1.setSharedAt(new Date());
                shareWith1.setLastUpdate(new Date());
                shareWith1.setEmails(shareWith.getEmails());
                return this.shareWithRepository.save(shareWith1);
            }
        }).orElseThrow(() -> new ExceptionMessage("Cannot share the workspace"));
    }

    @Override
    public List<Workspace> getSharedWorkspace(String email) {
        List<ShareWith> shareWiths = shareWithRepository.findAllByEmailsContains(email);
        List<Workspace> workspaces = new ArrayList<>();
        shareWiths.forEach(shareWith -> {
            this.workspaceRepository.findById(shareWith.getWorkspace().getWorkspaceId()).map(workspaces::add).orElseThrow(() -> new ExceptionMessage("cannot find workSpace"));
        });
        return workspaces;
    }

    @Override
    public void deleteSharedWith(long shareWithId) {
        this.shareWithRepository.findById(shareWithId).map(shareWith -> {
            this.shareWithRepository.delete(shareWith);
            return "";
        }).orElseThrow(() -> new ExceptionMessage("cannot delete the share"));
    }

    @Override
    public void deleteUserInShareWith(long workspaceId, String email) {
        this.workspaceRepository.findById(workspaceId).map(workspace -> {
            ShareWith shareWith = this.shareWithRepository.findByWorkspaceAndAndEmailsContains(workspace, email);
            String emails = shareWith.getEmails();
            List<String> list = new ArrayList<>(Arrays.asList(emails.split("-")));
            list.remove(email);
            emails = String.join("-", list.toArray(new String[0]));
            shareWith.setEmails(emails);
            this.shareWithRepository.save(shareWith);
            return "";
        }).orElseThrow(() -> new ExceptionMessage("cannot delete User"));

    }

    @Override
    public List<AppUser> getShareWithUsers(long workspaceId) {
        List<AppUser> appUsers = new ArrayList<>();
        return workspaceRepository.findById(workspaceId).map(workspace -> {
            ShareWith shareWith = shareWithRepository.findByWorkspace(workspace);
            String emails = shareWith.getEmails();
            System.out.println(emails);
            String[] emailsArray = emails.split("-");
            for (String s : emailsArray) {
                AppUser user = appUserRepository.findByEmail(s);
                if (user!= null) {
                    appUsers.add(user);
                }
            }
            return appUsers ;
        }).orElseThrow(() -> new ExceptionMessage("cannot get Workspace users"));
    }

    @Override
    public List<AppUser> getUsersToShare(long workspaceId, String owner) {
        List<AppUser> appUsers;
        AppUser user = appUserRepository.findByEmail(owner);
        appUsers = workspaceRepository.findById(workspaceId).map(workspace -> {
            ShareWith shareWith = shareWithRepository.findByWorkspace(workspace);
            if (shareWith!= null){
                String emails = shareWith.getEmails();
                List<AppUser> appUsers2 = appUserRepository.findAll();
                String[] emailsArray = emails.split("-");
                for (String s : emailsArray) {
                    AppUser user1 = appUserRepository.findByEmail(s);
                    appUsers2.remove(user1);
                }
                appUsers2.remove(user);
                return appUsers2;
            }
            else {
                return appUserRepository.findAll().stream().filter(appUser -> !appUser.getEmail().equals(user.getEmail())).collect(Collectors.toList());
            }
        }).orElseThrow(() -> new ExceptionMessage("cannot get Workspace users"));
        return appUsers;
    }
}
