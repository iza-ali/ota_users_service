package com.iaali.ota_users_service.mapper;

import com.iaali.ota_users_service.dto.FollowResponseDTO;
import com.iaali.ota_users_service.entity.FollowEntity;
import com.iaali.ota_users_service.entity.ProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FollowMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "follower.id", target = "followerId")
    @Mapping(source = "followed.id", target = "followedId")
    @Mapping(source = "createdAt", target = "createdAt")
    FollowResponseDTO toDTO(FollowEntity entity);

    @Mapping(source = "follower", target = "follower")
    @Mapping(source = "followed", target = "followed")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    FollowEntity toEntity(ProfileEntity follower, ProfileEntity followed);
}
