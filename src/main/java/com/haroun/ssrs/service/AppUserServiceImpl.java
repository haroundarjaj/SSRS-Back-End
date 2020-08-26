package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppRole;
import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.ShareWith;
import com.haroun.ssrs.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    ChartRepository chartRepository;

    @Autowired
    ShareWithRepository shareWithRepository;

    @Autowired
    FormulaRepository formulaRepository;

    @Autowired
    DashboardRepository dashboardRepository;

    @Autowired
    DatabaseSourceRepository databaseSourceRepository;

    @Autowired
    AlgorithmRepository algorithmRepository;

    @Autowired
    ImportedTableRepository importedTableRepository;

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
            if (appUserObj.getUsername().equals(appUser.getUsername())) {
                return "username exist";
            } else if (appUserObj.getEmail().equals(appUser.getEmail())) {
                return "email exist";
            }
        }
        return null;
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
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
    public AppUser getUserData(Long id) {
        return appUserRepository.findById(id).get();
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
        return appUsers;
    }

    @Override
    public List<AppUser> getAllDeactivatedUsers() {
        List<AppUser> appUsers = new ArrayList<>(appUserRepository.findAppUsersByActivatedFalse());
        System.out.println(appUsers);
        Collections.reverse(appUsers);
        return appUsers;
    }

    @Override
    public List<AppUser> getUsersByRange(int firstLimit, int numberOfElements) {
        Page usersPage = appUserRepository.findAll(PageRequest.of(firstLimit, numberOfElements, Sort.Direction.DESC, "creationTime"));
        List<AppUser> users = usersPage.getContent();
        System.out.println("firstLimit " + firstLimit);
        System.out.println("numberOfElements  " + numberOfElements);
        System.out.println("users");
        System.out.println(users);
        return users;
    }

    @Override
    public void updateUser(AppUser user) {
        appUserRepository.save(user);
    }

    @Override
    public void updateUserProfile(AppUser user) {
        System.out.println("user id: ");
        System.out.println(user.getUserId());
        AppUser appUser = appUserRepository.findById(user.getUserId()).get();
        appUser.setEmail(user.getEmail());
        appUser.setUsername(user.getUsername());
        appUser.setIdCardNumber(user.getIdCardNumber());
        appUser.setAddress(user.getAddress());
        appUser.setBirthday(user.getBirthday());
        System.out.println(appUser);
        appUserRepository.save(appUser);
    }

    @Override
    public void updateUserPicture(AppUser user) {
        AppUser appUser = appUserRepository.findById(user.getUserId()).get();
        appUser.setImage(user.getImage());
        appUserRepository.save(appUser);
    }

    @Override
    public String getProfileImage(Long id) {
        AppUser appUser = appUserRepository.findById(id).get();
        return appUser.getImage();
    }

    @Override
    public Boolean deleteUser(long id) {
        return appUserRepository.findById(id).map(user ->{
            workspaceRepository.findAllByUser(user).forEach(workspace -> {
                workspace.getCharts().forEach(chart -> chartRepository.delete(chart));
                ShareWith shareWith = shareWithRepository.findByWorkspace(workspace);
                if(shareWith != null) {
                    shareWithRepository.delete(shareWith);
                }
                workspaceRepository.delete(workspace);
            });
            dashboardRepository.findByUser(user).map(dshb ->{
                chartRepository.deleteAll(dshb.getCharts());
                dashboardRepository.delete(dshb);
                return true;
            }).orElseThrow(()-> new ExceptionMessage("Impossible to delete dashboard"));
            databaseSourceRepository.findAllByUser(user).forEach(databaseSource -> {
                databaseSourceRepository.delete(databaseSource);
            });
            formulaRepository.findAllByUser(user).forEach(formula -> {
                formulaRepository.delete(formula);
            });
            algorithmRepository.findAllByUser(user).forEach(algo -> {
                algorithmRepository.delete(algo);
            });
            importedTableRepository.findAllByUser(user).forEach(table -> {
                importedTableRepository.delete(table);
            });
            appUserRepository.delete(user);
            return true;
        }).orElseThrow(()-> new ExceptionMessage("Impossible to delete user"));
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
