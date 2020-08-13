package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.service.AppUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3001" })
@RestController
public class AppUserController {

    AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/user/checkRegister")
    public String checkExistence(@RequestBody AppUser appUser) {
        return appUserService.checkExistence(appUser);
    }

    @GetMapping("/getuserdata/{id}")
    public AppUser getUserData (@PathVariable("id") Long id) {return appUserService.getUserData(id); }

    @PostMapping("/user/register")
    public AppUser register(@RequestBody AppUser appUser) { return appUserService.saveUser(appUser); }

    @GetMapping("/administration/getAllUser")
    public List<AppUser> getAllUsers() { return appUserService.getAllUsers(); }

    @GetMapping("/administration/getAllDeactivatedUsers")
    public List<AppUser> getAllDeactivatedUsers() { return appUserService.getAllDeactivatedUsers(); }

    @GetMapping("/getUsersByRange/{firstLimit}&{numberOfItems}")
    public List<AppUser> getUsersByRange (@PathVariable("firstLimit") int firstLimit, @PathVariable("numberOfItems") int numberOfItems ) {
        return appUserService.getUsersByRange(firstLimit, numberOfItems);
    }

    @PostMapping("/updateUser")
    public void updateUser(@RequestBody AppUser user) { appUserService.updateUser(user); }

    @PostMapping("/updateUserProfile")
    public void updateUserProfile(@RequestBody AppUser user) { appUserService.updateUserProfile(user); }

    @PostMapping("/updateUserPicture")
    public void updateUserPicture(@RequestBody AppUser user) { appUserService.updateUserPicture(user); }

    @GetMapping("/user/getProfileImage/{id}")
    public String getProfileImage(@PathVariable("id") Long id) { return  appUserService.getProfileImage(id); }

    @PostMapping("/administration/updateUserRole/{role}")
    public void updateUserRole(@RequestBody AppUser user, @PathVariable("role") String role) {
        appUserService.updateUserRole(user, role);
    }

    @PostMapping("/deleteUser")
    public void deleteUser(@RequestBody AppUser user) { appUserService.deleteUser(user); }

    /* @PostMapping("/login")
    public AppUser login(@RequestBody AppUser appUser) {
        return appUserService.login(appUser);
    } */
}
