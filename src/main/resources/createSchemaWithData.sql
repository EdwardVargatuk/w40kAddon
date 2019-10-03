-- MySQL Workbench Forward Engineering


SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Schema warhammer_addon_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `warhammer_addon_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `warhammer_addon_db`;

-- -----------------------------------------------------
-- Table `warhammer_addon_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warhammer_addon_db`.`user`
(
  `id`        INT         NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(30) NOT NULL,
  `password`  VARCHAR(30) NOT NULL,
  `email`     VARCHAR(255),
  `balance`   DOUBLE      NOT NULL,

  PRIMARY KEY (id)

)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci;

-- -----------------------------------------------------
-- Table `warhammer_addon_db`.`armor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warhammer_addon_db`.`armor`
(
  `id`    INT          NOT NULL AUTO_INCREMENT,
  `name`  VARCHAR(255) NOT NULL,
  `power` DOUBLE       NOT NULL,

  PRIMARY KEY (id)

)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci;

-- -----------------------------------------------------
-- Table `warhammer_addon_db`.`armor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warhammer_addon_db`.`weapon`
(
  `id`    INT          NOT NULL AUTO_INCREMENT,
  `name`  VARCHAR(255) NOT NULL,
  `power` DOUBLE       NOT NULL,

  PRIMARY KEY (id)

)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `warhammer_addon_db`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `warhammer_addon_db`;
INSERT INTO `warhammer_addon_db`.`user` (`id`, `user_name`, `password`, `email`, `balance`)
VALUES (1, 'Andry', 'w40k', 'andry@gmail.com', 0.0);
INSERT INTO `warhammer_addon_db`.`user` (`id`, `user_name`, `password`, `email`, `balance`)
VALUES (2, 'Edward', 'w40k', 'edwardsmail@gmail.com', 0.0);
INSERT INTO `warhammer_addon_db`.`user` (`id`, `user_name`, `password`, `email`, `balance`)
VALUES (3, 'Nikolay', 'w40k', 'nikolay@gmail.com', 0.0);
INSERT INTO `warhammer_addon_db`.`user` (`id`, `user_name`, `password`, `email`, `balance`)
VALUES (4, 'Oleg', 'w40k', 'oleg@gmail.com', 0.0);
COMMIT;

START TRANSACTION;
USE `warhammer_addon_db`;
INSERT INTO `warhammer_addon_db`.`armor` (`id`, `name`, `power`)
VALUES (1, 'first weapon', 15.0);

COMMIT;

START TRANSACTION;
USE `warhammer_addon_db`;
INSERT INTO `warhammer_addon_db`.`weapon` (`id`, `name`, `power`)
VALUES (1, 'axe', 25.0);

COMMIT;