package me.cher1shrxd.whattodoserver.domain.sprint.repository;

import me.cher1shrxd.whattodoserver.domain.sprint.entity.SprintEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SprintRepository extends JpaRepository<SprintEntity, String> {
    Optional<SprintEntity> findById(String id);
}
