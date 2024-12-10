package me.cher1shrxd.whattodoserver.domain.schedule.repository;

import me.cher1shrxd.whattodoserver.domain.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {}
