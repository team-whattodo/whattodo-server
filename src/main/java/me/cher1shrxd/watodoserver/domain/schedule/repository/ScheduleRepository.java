package me.cher1shrxd.watodoserver.domain.schedule.repository;

import me.cher1shrxd.watodoserver.domain.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {
    Optional<ScheduleEntity> findByBranch(String branchName);
}
