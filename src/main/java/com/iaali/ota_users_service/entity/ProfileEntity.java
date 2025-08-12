package com.iaali.ota_users_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profiles")
public class ProfileEntity {

    @Id
    @Column(name = "profile_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "bio", nullable = false, columnDefinition = "TINYTEXT")
    private String bio;

    @Column(nullable = false)
    private String avatarUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;
}