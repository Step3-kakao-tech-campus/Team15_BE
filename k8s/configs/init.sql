CREATE SCHEMA IF NOT EXISTS `borrowme_db_test` DEFAULT CHARACTER SET utf8mb4;

CREATE USER root@'%' identified by 'Kakao@123';
GRANT ALL ON borrowme_db_test.* TO 'root'@'%';
FLUSH PRIVILEGES;

USE `borrowme_db_test`;
