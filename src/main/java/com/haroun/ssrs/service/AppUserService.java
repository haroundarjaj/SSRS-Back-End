package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppUser;

import java.util.List;

public interface AppUserService {

    String checkExistence (AppUser appUser);

    AppUser saveUser (AppUser appUser);

    AppUser findUserByEmail (String email);

    void setRoleToUser (String email, String role);

    List<AppUser> getAllUsers ();

    List<AppUser> getAllDeactivatedUsers ();

    void updateUser (AppUser user);

    void updateUserRole (AppUser user, String role);

    void  deleteUser (AppUser user);

    // AppUser login(AppUser appUser);
}
