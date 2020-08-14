package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.UserTask;
import com.haroun.ssrs.service.UserTaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3001" })
@RestController
@RequestMapping("/task")
public class UserTaskController {

    UserTaskService userTaskService;

    public UserTaskController(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }

    @GetMapping("/getAll")
    public List<UserTask> getAllWorkspaces (Authentication authentication) {
        return userTaskService.getAllTasks(authentication.getName());
    }

    @PostMapping("/add")
    public boolean addTask (@RequestBody UserTask task, Authentication authentication) {
        return userTaskService.addTask(task, authentication.getName());
    }

    @PostMapping("/update")
    public boolean updateTask (@RequestBody UserTask task) {
        return userTaskService.updateTask(task);
    }

    @PostMapping("/delete")
    public boolean deleteTask (@RequestBody UserTask task) {
        return userTaskService.deleteTask(task);
    }
}
