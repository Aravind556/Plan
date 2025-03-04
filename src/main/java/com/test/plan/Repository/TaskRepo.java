package com.test.plan.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.plan.Entity.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findByUserId(int userId);
    Optional<Task> findByUserIdAndId(int userId, int taskId);
    List<Task> findByUserIdAndStatus(int userId, Boolean status);
    List<Task> findByUserIdOrderByCreatedAtDesc(int userId);
}
