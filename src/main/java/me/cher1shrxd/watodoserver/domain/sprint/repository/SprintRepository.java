package me.cher1shrxd.watodoserver.domain.sprint.repository;

import me.cher1shrxd.watodoserver.domain.sprint.entity.SprintEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<SprintEntity, String> {
//    Optional<SprintEntity> findById(UUID id);
}
