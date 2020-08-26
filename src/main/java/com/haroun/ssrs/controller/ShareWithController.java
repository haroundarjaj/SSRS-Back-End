package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.ShareWith;
import com.haroun.ssrs.model.Workspace;
import com.haroun.ssrs.service.ShareWithService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin("*")
public class ShareWithController {
    private ShareWithService shareWithService;
    public ShareWithController(ShareWithService shareWithService){
        this.shareWithService = shareWithService;
    }
    @RequestMapping(path = "sharewith/{workspaceId}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShareWith SaveShareWith(@PathVariable(value = "workspaceId") long workspaceId ,@RequestBody ShareWith shareWith){
        return this.shareWithService.shareWith(workspaceId, shareWith);
    }

    @RequestMapping(path = "sharewith/{workspaceId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppUser> getShareWithUsers(@PathVariable(value = "workspaceId") long workspaceId){
        return this.shareWithService.getShareWithUsers(workspaceId);
    }
    @RequestMapping(path = "sharewith/workspace",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Workspace> getShareWithWorkspace(Authentication authentication){
        return shareWithService.getSharedWorkspace(authentication.getName());
    }

    @RequestMapping(path = "sharewith/workspace/users/{workspaceId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppUser> getShareWithWorkspaceUsers(@PathVariable(value = "workspaceId") long workspaceId,Authentication authentication){
        return shareWithService.getUsersToShare(workspaceId,authentication.getName());
    }

    @RequestMapping(path = "sharewith/delete/{shareWithId}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteShareWith(@PathVariable(value = "shareWithId") long shareWithId ){
        shareWithService.deleteSharedWith(shareWithId);
        return "done";
    }

    @RequestMapping(path = "sharewith/delete/user/{workspaceId}/{email}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteShareWithUser(@PathVariable(value = "workspaceId") long workspaceId, @PathVariable(value = "email") String email ){
        shareWithService.deleteUserInShareWith(workspaceId, email);
        return "done";
    }
}
