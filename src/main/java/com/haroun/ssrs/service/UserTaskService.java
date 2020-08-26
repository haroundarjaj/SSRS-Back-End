package com.haroun.ssrs.service;

import com.haroun.ssrs.model.UserTask;

import java.util.List;

public interface UserTaskService {

    List<UserTask> getAllTasks (String userEmail);

    Boolean addTask (UserTask task, String userEmail);

    Boolean updateTask (UserTask task);

    Boolean deleteTask(long id);
}
