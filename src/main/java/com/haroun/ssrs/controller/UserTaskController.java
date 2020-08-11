package com.haroun.ssrs.controller;

import com.haroun.ssrs.model.UserTask;
import com.haroun.ssrs.service.UserTaskService;
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

    @GetMapping("/getAll&{userEmail}")
    public List<UserTask> getAllWorkspaces (@PathVariable("userEmail") String userEmail) {
        return userTaskService.getAllTasks(userEmail);
    }

    @PostMapping("/add&{userEmail}")
    public boolean addTask (@RequestBody UserTask task, @PathVariable("userEmail") String userEmail) {
        return userTaskService.addTask(task, userEmail);
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
