use ota_users_db;


INSERT INTO users (user_id, email, password_hash, is_deleted) VALUES (1, 'one@email.com', 'password', false);
INSERT INTO users (user_id, email, password_hash, is_deleted) VALUES (2, 'two@email.com', 'password', false);
INSERT INTO users (user_id, email, password_hash, is_deleted) VALUES (3, 'three@email.com', 'password', false);
INSERT INTO users (user_id, email, password_hash, is_deleted) VALUES (4, 'four@email.com', 'password', false);
INSERT INTO users (user_id, email, password_hash, is_deleted) VALUES (5, 'five@email.com', 'password', false);
INSERT INTO users (user_id, email, password_hash, is_deleted) VALUES (6, 'six@email.com', 'password', false);
INSERT INTO users (user_id, email, password_hash, is_deleted) VALUES (7, 'seven@email.com', 'password', false);

INSERT INTO profiles (profile_id, user_id, username, bio, is_deleted) VALUES (1, 1, 'one', 'bio', false);
INSERT INTO profiles (profile_id, user_id, username, bio, is_deleted) VALUES (2, 2, 'two', 'bio', false);
INSERT INTO profiles (profile_id, user_id, username, bio, is_deleted) VALUES (3, 3, 'three', 'bio', false);
INSERT INTO profiles (profile_id, user_id, username, bio, is_deleted) VALUES (4, 4, 'four', 'bio', false);
INSERT INTO profiles (profile_id, user_id, username, bio, is_deleted) VALUES (5, 5, 'five', 'bio', false);
INSERT INTO profiles (profile_id, user_id, username, bio, is_deleted) VALUES (6, 6, 'six', 'bio', false);
INSERT INTO profiles (profile_id, user_id, username, bio, is_deleted) VALUES (7, 7, 'seven', 'bio', false);

INSERT INTO follows (follow_id, follower_profile_id, followed_profile_id) VALUES (1, 1, 2);
INSERT INTO follows (follow_id, follower_profile_id, followed_profile_id) VALUES (2, 2, 1);
INSERT INTO follows (follow_id, follower_profile_id, followed_profile_id) VALUES (3, 5, 4);
INSERT INTO follows (follow_id, follower_profile_id, followed_profile_id) VALUES (4, 6, 5);
INSERT INTO follows (follow_id, follower_profile_id, followed_profile_id) VALUES (5, 7, 6);
INSERT INTO follows (follow_id, follower_profile_id, followed_profile_id) VALUES (6, 3, 2);
INSERT INTO follows (follow_id, follower_profile_id, followed_profile_id) VALUES (7, 4, 2);

INSERT INTO liked_categories (liked_category_id, profile_id, category_id) VALUES (1, 2, 1);
INSERT INTO liked_categories (liked_category_id, profile_id, category_id) VALUES (2, 1, 1);
INSERT INTO liked_categories (liked_category_id, profile_id, category_id) VALUES (3, 4, 1);
INSERT INTO liked_categories (liked_category_id, profile_id, category_id) VALUES (4, 5, 3);
INSERT INTO liked_categories (liked_category_id, profile_id, category_id) VALUES (5, 6, 4);
INSERT INTO liked_categories (liked_category_id, profile_id, category_id) VALUES (6, 2, 2);
INSERT INTO liked_categories (liked_category_id, profile_id, category_id) VALUES (7, 7, 2);