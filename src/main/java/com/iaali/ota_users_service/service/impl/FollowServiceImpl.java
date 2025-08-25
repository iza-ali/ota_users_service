package com.iaali.ota_users_service.service.impl;

import com.iaali.ota_users_service.dto.FollowRequestDTO;
import com.iaali.ota_users_service.dto.FollowResponseDTO;
import com.iaali.ota_users_service.entity.ProfileEntity;
import com.iaali.ota_users_service.exception.ErrorEnum;
import com.iaali.ota_users_service.exception.GlobalException;
import com.iaali.ota_users_service.mapper.FollowMapper;
import com.iaali.ota_users_service.repository.FollowRepository;
import com.iaali.ota_users_service.service.FollowService;
import com.iaali.ota_users_service.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

    private FollowRepository repository;
    private FollowMapper mapper;

    private ProfileService profileService;

    @Override
    public FollowResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new GlobalException(id, ErrorEnum.NOT_FOUND_ID));
    }

    @Override
    public List<FollowResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<FollowResponseDTO> getListOfFollowing(Long id) {
        return repository.findAllByFollowerId(id).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<FollowResponseDTO> getListOfFollowers(Long id) {
        return repository.findAllByFollowedId(id).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public Long getFollowingCountById(Long id) {
        return repository.countByFollowerId(id);
    }

    @Override
    public Long getFollowerCountById(Long id) {
        return repository.countByFollowedId(id);
    }


    @Override
    public FollowResponseDTO save(FollowRequestDTO dto) {
        Long followerId = dto.getFollowerId();
        Long followedId = dto.getFollowedId();

        if (followerId.equals(followedId)) {
            throw new GlobalException(ErrorEnum.BAD_REQUEST_FOLLOW_IDS_MATCH);
        }

        if (repository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            throw new GlobalException(ErrorEnum.CONFLICT_FOLLOW_ALREADY_EXISTS);
        }

        ProfileEntity follower = profileService.getEntityById(followerId);
        ProfileEntity followed = profileService.getEntityById(followedId);

        return mapper.toDTO(repository.save(mapper.toEntity(follower, followed)));
    }

    @Override
    public void delete(Long id) {
        // Only hard delete available for follow table
        if (!repository.existsById(id)) {
            throw new GlobalException(id, ErrorEnum.NOT_FOUND_ID);
        }

        repository.deleteById(id);
    }
}
