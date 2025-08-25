package com.iaali.ota_users_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowRequestDTO {

    @NotNull(message = "Follower ID is required")
    private Long followerId;

    @NotNull(message = "Followed ID is required")
    private Long followedId;
}
