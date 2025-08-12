package com.iaali.ota_users_service.repository;

import com.iaali.ota_users_service.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<ProfileEntity> findByUserId(Long id);
}
