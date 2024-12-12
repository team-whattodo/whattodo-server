package me.cher1shrxd.watodoserver.domain.task.repository;

import me.cher1shrxd.watodoserver.domain.task.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TaskRepository extends JpaRepository<TaskEntity, String> {
    Optional<TaskEntity> findByBranch(String branchName);
}
