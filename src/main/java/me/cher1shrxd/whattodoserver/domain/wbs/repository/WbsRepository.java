package me.cher1shrxd.whattodoserver.domain.wbs.repository;

import me.cher1shrxd.whattodoserver.domain.wbs.entity.WbsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WbsRepository extends JpaRepository<WbsEntity, String> {
}
