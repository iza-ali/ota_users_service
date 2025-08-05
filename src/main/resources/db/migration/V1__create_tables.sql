use ota_users_db;

DROP TABLE IF EXISTS liked_categories;
DROP TABLE IF EXISTS follows;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    email VARCHAR(254) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('User', 'Admin') NOT NULL DEFAULT 'User',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (user_id),
    UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE profiles (
    profile_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id BIGINT UNSIGNED NOT NULL,
    username VARCHAR(20) NOT NULL,
    bio TINYTEXT NULL,
    avatar_url VARCHAR(2048) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (profile_id),
    UNIQUE (username),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id)
        REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE follows (
    follow_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    follower_profile_id BIGINT UNSIGNED NOT NULL,
    followed_profile_id BIGINT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (follow_id),
    UNIQUE (follower_profile_id, followed_profile_id),
    CONSTRAINT FK_follower_profile_id FOREIGN KEY (follower_profile_id)
        REFERENCES profiles(profile_id),
    CONSTRAINT FK_followed_profile_id FOREIGN KEY (followed_profile_id)
        REFERENCES profiles(profile_id)
) ENGINE=InnoDB;

CREATE TABLE liked_categories (
    liked_category_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    profile_id BIGINT UNSIGNED NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (liked_category_id),
    UNIQUE (profile_id, category_id),
    CONSTRAINT FK_profile_id FOREIGN KEY (profile_id)
        REFERENCES profiles(profile_id)
) ENGINE=InnoDB;