package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.ShareWith;
import com.haroun.ssrs.model.Workspace;

import java.util.List;

public interface ShareWithService {
    ShareWith shareWith(long workspaceId, ShareWith shareWith);
    List<Workspace> getSharedWorkspace(String email);
    void deleteSharedWith(long shareWithId);
    void deleteUserInShareWith(long workspaceId, String email);
    List<AppUser> getShareWithUsers(long workspaceId);
    List<AppUser> getUsersToShare(long workspaceId, String owner);
}
