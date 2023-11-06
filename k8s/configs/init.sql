CREATE SCHEMA IF NOT EXISTS `krampoline` DEFAULT CHARACTER SET utf8mb4;

GRANT ALL ON *.* TO 'root'@'localhost' IDENTIFIED BY 'root' WITH GRANT OPTION;
GRANT ALL ON krampoline.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

USE `krampoline`;

SET foreign_key_checks = 0;    # 외래키 체크 설정 해제
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS interests;
DROP TABLE IF EXISTS mentor_posts;
DROP TABLE IF EXISTS connected_users;
DROP TABLE IF EXISTS not_connected_register_users;
DROP TABLE IF EXISTS user_interests;
DROP TABLE IF EXISTS videos;
DROP TABLE IF EXISTS subtitles;
DROP TABLE IF EXISTS video_interests;
DROP TABLE IF EXISTS refresh_tokens;
DROP TABLE IF EXISTS video_histories;
SET foreign_key_checks = 1;    # 외래키 체크 설정

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                       age INT NOT NULL,
                       country VARCHAR(255) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       first_name VARCHAR(255) NOT NULL,
                       introduction VARCHAR(255),
                       last_name VARCHAR(255) NOT NULL,
                       PASSWORD VARCHAR(256) NOT NULL,
                       phone VARCHAR(255) NOT NULL,
                       profile_image VARCHAR(255),
                       role VARCHAR(255) NOT NULL
);

CREATE TABLE interests (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                           category VARCHAR(255)
);

CREATE TABLE videos (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                        video_end_time VARCHAR(255),
                        video_info VARCHAR(255),
                        video_start_time VARCHAR(255),
                        video_url VARCHAR(255)
);

CREATE TABLE mentor_posts (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                              content VARCHAR(300),
                              state VARCHAR(255) NOT NULL,
                              title VARCHAR(255) NOT NULL,
                              writer_id INT,
                              FOREIGN KEY (writer_id) REFERENCES users(id)
);

CREATE TABLE connected_users (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                 mentee_user_id INT,
                                 mentor_post_id INT,
                                 FOREIGN KEY (mentee_user_id) REFERENCES users(id),
                                 FOREIGN KEY (mentor_post_id) REFERENCES mentor_posts(id)
);

CREATE TABLE not_connected_register_users (
                                              id INT AUTO_INCREMENT PRIMARY KEY,
                                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                              state VARCHAR(255) NOT NULL,
                                              mentee_user_id INT,
                                              mentor_post_id INT,
                                              FOREIGN KEY (mentee_user_id) REFERENCES users(id),
                                              FOREIGN KEY (mentor_post_id) REFERENCES mentor_posts(id)
);

CREATE TABLE user_interests (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                interest_id INT,
                                user_id INT,
                                FOREIGN KEY (interest_id) REFERENCES interests(id),
                                FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE video_interests (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                 interest_id INT,
                                 video_id INT,
                                 FOREIGN KEY (interest_id) REFERENCES interests(id),
                                 FOREIGN KEY (video_id) REFERENCES videos(id)
);

CREATE TABLE subtitles (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                           eng_end_time VARCHAR(255),
                           eng_start_time VARCHAR(255),
                           eng_subtitle_content VARCHAR(255),
                           kor_end_time VARCHAR(255),
                           kor_start_time VARCHAR(255),
                           kor_subtitle_content VARCHAR(255),
                           video_id INT,
                           FOREIGN KEY (video_id) REFERENCES videos(id)
);

CREATE TABLE refresh_tokens (
    id INT NOT NULL AUTO_INCREMENT,
    refresh_token VARCHAR(500),
    user_id INT,
    PRIMARY KEY (id)
);

CREATE TABLE video_histories (
    id INT NOT NULL AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    user_id INT,
    video_id INT,
    PRIMARY KEY (id)
);


INSERT INTO `interests` (created_at, category) VALUES
                                                   (NOW(), 'IDOL'),
                                                   (NOW(), 'Game'),
                                                   (NOW(), 'K-POP'),
                                                   (NOW(), 'Sports'),
                                                   (NOW(), 'Movie'),
                                                   (NOW(), 'Drama');

