package me.cher1shrxd.watodoserver.domain.project.repository;

import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {
    Optional<ProjectEntity> findById(String id);
}
