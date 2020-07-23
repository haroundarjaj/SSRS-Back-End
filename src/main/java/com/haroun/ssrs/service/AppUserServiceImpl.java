package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppRole;
import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    FormulaRepository formulaRepository;

    @Autowired
    DatabaseSourceRepository databaseSourceRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public String checkExistence(AppUser appUser) {
        List<AppUser> appUsers = appUserRepository.findAll();
        System.out.println(appUsers);
        System.out.println(appUser.getUsername());
        for (AppUser appUserObj : appUsers) {
            System.out.println(appUserObj);
            if (appUserObj.getUsername().equals(appUser.getUsername())){
                return "username exist";
            }
            else if (appUserObj.getEmail().equals(appUser.getEmail())) {
                return "email exist";
            }
        }
        return null;
    }

    @Override
    public AppUser saveUser (AppUser appUser) {
        appUser.setUserId(sequenceGenerator.generateSequence(appUser.SEQUENCE_NAME));
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        AppRole appRole = appRoleRepository.findByRole("USER");
        appUser.setRole(appRole);
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public void setRoleToUser(String email, String roleName) {
        AppUser appUser = appUserRepository.findByEmail(email);
        AppRole appRole = appRoleRepository.findByRole(roleName);
        appUser.setRole(appRole);
        appUserRepository.save(appUser);
    }

    @Override
    public void updateUserRole(AppUser user, String role) {
        AppRole appRole = appRoleRepository.findByRole(role);
        user.setRole(appRole);
        appUserRepository.save(user);
    }

    @Override
    public List<AppUser> getAllUsers() {
        List<AppUser> appUsers = new ArrayList<>(appUserRepository.findAll());
        Collections.reverse(appUsers);
        return appUsers ;
    }

    @Override
    public void updateUser(AppUser user) {
        appUserRepository.save(user);
    }

    @Override
    public void deleteUser(AppUser user) {
        workspaceRepository.findAllByUser(user).forEach(workspace -> {
            workspaceRepository.delete(workspace);
        });
        databaseSourceRepository.findAllByUser(user).forEach(databaseSource -> {
            databaseSourceRepository.delete(databaseSource);
        });
        formulaRepository.findAllByUser(user).forEach(formula -> {
            formulaRepository.delete(formula);
        });
        appUserRepository.delete(user);
    }

    /* @Override
    public AppUser login(AppUser appUser) {
        List<AppUser> appUsers = appUserRepository.findAll();
        for (AppUser appUserObj : appUsers) {
            System.out.println(bCryptPasswordEncoder.matches(appUser.getPassword(), appUserObj.getPassword()));
            if (appUserObj.getEmail().equals(appUser.getEmail()) && bCryptPasswordEncoder.matches(appUser.getPassword(), appUserObj.getPassword())){
                System.out.println(appUserObj);
                return appUserObj;
            }
        }
        return null;
    } */
}
