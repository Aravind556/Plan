package com.test.plan.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.test.plan.Entity.Task;
import com.test.plan.Entity.Users;
import com.test.plan.Repository.TaskRepo;
import com.test.plan.Repository.UserRepo;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UserRepo userRepo;

    //INSTEAD OF USING THE LOGGED USER IN CONTROLLER, WE CAN USE IT IN SERVICE TO SIMPLIFY THE PROCESSING
    
    private Users getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            return userRepo.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("No user logged in");
    }

    public Task addTaskForCurrentUser(Task task) {
        Users currentUser = getLoggedInUser();
        task.setUser(currentUser);
        task.setStatus(false);
        return taskRepo.save(task);
    }

    public List<Task> getCurrentUserTasks() {
        Users currentUser = getLoggedInUser();
        return taskRepo.findByUserIdOrderByCreatedAtDesc(currentUser.getId());
    }

    public List<Task> getCurrentUserTasksByStatus(Boolean status) {
        Users currentUser = getLoggedInUser();
        return taskRepo.findByUserIdAndStatus(currentUser.getId(), status);
    }

    public List<Task> gettaskbyuser(int userid){
        return taskRepo.findByUserId(userid);
    }

    public Task saveTask(Task task, int userId) {
        Users currentUser = getLoggedInUser();
        task.setUser(currentUser);
        task.setStatus(false); // Set initial status as not completed
        return taskRepo.save(task);
    }

    public Task gettaskbyid(int taskid) {
        Users currentUser = getLoggedInUser();
        return taskRepo.findByUserIdAndId(currentUser.getId(), taskid)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updatetask(Task task, int taskid) {
        Task existingTask = gettaskbyid(taskid);
        if(task.getDescription() != null) {
            existingTask.setDescription(task.getDescription());
        }
        if(task.getStatus() != null) {
            existingTask.setStatus(task.getStatus());
        }
        return taskRepo.save(existingTask);
    }

    public void delete(int taskId) {
        Task task = gettaskbyid(taskId);
        taskRepo.delete(task);
    }
}
