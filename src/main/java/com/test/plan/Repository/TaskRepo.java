package com.test.plan.Repository;

import com.test.plan.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task,Integer> {
    List<Task> findByUserId(int userId);
    Optional<Task> findByUserIdAndId(int userId, int taskId);
}
