package me.cher1shrxd.watodoserver.domain.wbs.repository;

import me.cher1shrxd.watodoserver.domain.wbs.entity.WbsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WbsRepository extends JpaRepository<WbsEntity, String> {
}
