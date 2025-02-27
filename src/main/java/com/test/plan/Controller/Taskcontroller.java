package com.test.plan.Controller;

import com.test.plan.Entity.Task;
import com.test.plan.Entity.Users;
import com.test.plan.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class Taskcontroller {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{userid}")
    public List<Task> showtasks(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("User not logged in");
        }
        return taskService.gettaskbyuser(user.getId());
    }
    @PostMapping("add/{userid}/{taskid}")
    public Task addTask(@RequestBody Task task, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("User not logged in");
        }
        return taskService.saveTask(task, user.getId());
    }
    @PutMapping("update/{userid}/{taskid}")
    public Optional<Task> updateTask(@PathVariable int taskId, @RequestBody Task task, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("User not logged in");
        }
        return Optional.ofNullable(taskService.updatetask(task, user.getId(), taskId));
    }

    @DeleteMapping("delete/{userid}/{taskid}")
    public void deleteTask(@PathVariable int taskId, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("User not logged in");
        }
        taskService.delete(user.getId(),taskId );
    }
}
