package me.cher1shrxd.watodoserver.domain.user.repository;

import me.cher1shrxd.watodoserver.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPat(String pat);

    Optional<UserEntity> findByEmail(String email);
}
