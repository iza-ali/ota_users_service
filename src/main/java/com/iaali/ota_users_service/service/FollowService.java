package com.iaali.ota_users_service.service;

import com.iaali.ota_users_service.dto.FollowRequestDTO;
import com.iaali.ota_users_service.dto.FollowResponseDTO;

import java.util.List;

public interface FollowService {

    FollowResponseDTO getById(Long id);

    List<FollowResponseDTO> getAll();

    List<FollowResponseDTO> getListOfFollowing(Long id);

    List<FollowResponseDTO> getListOfFollowers(Long id);

    Long getFollowingCountById(Long id);

    Long getFollowerCountById(Long id);

    FollowResponseDTO save(FollowRequestDTO dto);

    void delete(Long id);
}
