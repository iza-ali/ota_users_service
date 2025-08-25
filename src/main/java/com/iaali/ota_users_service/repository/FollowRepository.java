package com.iaali.ota_users_service.repository;

import com.iaali.ota_users_service.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    List<FollowEntity> findAllByFollowerId(Long id);

    List<FollowEntity> findAllByFollowedId(Long id);

    Long countByFollowerId(Long id);

    Long countByFollowedId(Long id);

    boolean existsByFollowerIdAndFollowedId(Long follower, Long followed);
}
