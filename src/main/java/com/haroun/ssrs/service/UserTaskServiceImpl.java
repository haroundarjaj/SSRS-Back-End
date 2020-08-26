package com.haroun.ssrs.service;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.UserTask;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserTaskServiceImpl implements UserTaskService {

    @Autowired
    UserTaskRepository userTaskRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public List<UserTask> getAllTasks(String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        List<UserTask> tasks = new ArrayList<>(userTaskRepository.findAllByUser(user));
        Collections.reverse(tasks);
        return tasks;
    }

    @Override
    public Boolean addTask(UserTask task, String userEmail) {
        AppUser user = appUserRepository.findByEmail(userEmail);
        task.setTaskId(sequenceGenerator.generateSequence(task.SEQUENCE_NAME));;
        task.setUser(user);
        userTaskRepository.save(task);
        return true;
    }

    @Override
    public Boolean updateTask(UserTask task) {
        userTaskRepository.save(task);
        return true;
    }

    @Override
    public Boolean deleteTask(long id) {
        return userTaskRepository.findById(id).map(al ->{
            userTaskRepository.delete(al);
            return true;
        }).orElseThrow(()-> new ExceptionMessage("Impossible to delete task"));
    }
}
