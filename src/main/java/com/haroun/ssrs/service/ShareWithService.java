package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.ShareWith;
import com.haroun.ssrs.model.Workspace;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

public interface ShareWithService {
    ShareWith shareWith(long workspaceId, ShareWith shareWith);
    List<Workspace> getSharedWorkspace(String email);
    void deleteSharedWith(String shareWithId);
    void deleteUserInShareWith(long workspaceId, String email);
    List<AppUser> getShareWithUsers(long workspaceId);

}