package com.haroun.ssrs.service;

import com.haroun.ssrs.model.UserTask;

import java.util.List;

public interface UserTaskService {

    List<UserTask> getAllTasks (String userEmail);

    boolean addTask (UserTask task, String userEmail);

    boolean updateTask (UserTask task);

    boolean deleteTask(UserTask task);
}
