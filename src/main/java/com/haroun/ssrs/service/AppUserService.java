package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppUser;

import java.util.List;

public interface AppUserService {

    String checkExistence (AppUser appUser);

    AppUser saveUser (AppUser appUser);

    AppUser findUserByEmail (String email);

    AppUser getUserData (Long id);

    void setRoleToUser (String email, String role);

    List<AppUser> getAllUsers ();

    List<AppUser> getAllDeactivatedUsers ();

    List<AppUser> getUsersByRange(int firstLimit, int numberOfElements);

    void updateUser (AppUser user);

    void updateUserProfile(AppUser user);

    void updateUserPicture(AppUser user);

    String getProfileImage(Long id);

    void updateUserRole (AppUser user, String role);

    Boolean  deleteUser (long id);

    // AppUser login(AppUser appUser);
}
