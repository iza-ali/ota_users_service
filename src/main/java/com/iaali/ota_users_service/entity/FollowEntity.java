package com.iaali.ota_users_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "follows", uniqueConstraints = @UniqueConstraint(columnNames = { "personNumber", "isActive" }))
public class FollowEntity {

    @Id
    @Column(name = "follow_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_profile_id")
    private ProfileEntity follower;

    @ManyToOne
    @JoinColumn(name = "followed_profile_id")
    private ProfileEntity followed;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}