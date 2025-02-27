package com.test.plan.Service;

import com.test.plan.Entity.Task;
import com.test.plan.Entity.Users;
import com.test.plan.Repository.TaskRepo;
import com.test.plan.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UserRepo userRepo;

    public List<Task> gettaskbyuser(int userid){
        return taskRepo.findByUserId(userid);
    }

    public Task saveTask(Task task, int userId) {
        Optional<Users> user = userRepo.findById(userId);
        if (user.isPresent()) {
            task.setUser(user.get());
            return taskRepo.save(task);
        }
        throw new RuntimeException("User not found");
    }

    public Task gettaskbyid(int userid,int taskid){
        return taskRepo.findByUserIdAndId(userid,taskid)
                .orElseThrow(()-> new RuntimeException("Task not found"));
    }

    public Task updatetask(Task task,int userId,int taskid){
        Task task1 = gettaskbyid(userId,taskid);
        if(task.getDescription() !=null){
            task1.setDescription(task.getDescription());
        }
        task1.setStatus(task1.getStatus());
        return taskRepo.save(task1);
    }

    public void delete(int userId,int taskId){
        Task task = gettaskbyid(userId,taskId);
        taskRepo.delete(task);
    }
}
