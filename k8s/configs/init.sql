CREATE SCHEMA IF NOT EXISTS `borrowme_db_test` DEFAULT CHARACTER SET utf8mb4;

CREATE USER 'root'@'%' identified by 'root';
GRANT ALL PRIVILEGES ON borrowme_db_test.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

USE `borrowme_db_test`;
