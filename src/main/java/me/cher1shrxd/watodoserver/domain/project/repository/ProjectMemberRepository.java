package me.cher1shrxd.watodoserver.domain.project.repository;

import me.cher1shrxd.watodoserver.domain.project.entity.ProjectMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMemberEntity, Long> {
}
