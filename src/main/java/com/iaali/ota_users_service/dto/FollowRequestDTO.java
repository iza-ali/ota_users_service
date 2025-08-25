package com.iaali.ota_users_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowRequestDTO {

    @NotBlank(message = "Follower ID is required")
    private Long followerId;

    @NotBlank(message = "Followed ID is required")
    private Long followedId;
}
