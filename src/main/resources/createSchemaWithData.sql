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
-- Table `warhammer_addon_db`.`weapon`
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

-- -----------------------------------------------------
-- Table `warhammer_addon_db`.`skin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warhammer_addon_db`.`skin`
(
  `id`    INT          NOT NULL AUTO_INCREMENT,
  `name`  VARCHAR(255) NOT NULL,
  `price` DOUBLE       NOT NULL,
  `url`   VARCHAR(255) NOT NULL,

  PRIMARY KEY (id)

)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci;

-- -----------------------------------------------------
-- Table `warhammer_addon_db`.`warrior`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warhammer_addon_db`.`warrior`
(
  `id`                 INT                   NOT NULL AUTO_INCREMENT,
  `warrior_name`       VARCHAR(255)          NOT NULL UNIQUE,
  `balance`            DOUBLE                NOT NULL,
  `attack`             DOUBLE                NOT NULL,
  `defence`            DOUBLE                NOT NULL,
  `agility`            INT,
  `level`              INT                   NOT NULL,
  `experience`         LONG                  NOT NULL,
  `user_id`            INT                   NOT NULL,
  `armor_id`           INT,
  `weapon_id`          INT,
  `skin_id`            INT,
  `warrior_speciality` ENUM ('APOTHECARY', 'ASSAULT',
    'LIBRARIAN', 'HEAVY_WEAPON', 'TACTICAL') NOT NULL,


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
VALUES (1, 'Andry', 'w40k', 'andry@gmail.com', 5.0);
INSERT INTO `warhammer_addon_db`.`user` (`id`, `user_name`, `password`, `email`, `balance`)
VALUES (2, 'Edward', 'w40k', 'edwardsmail@gmail.com', 5.0);
INSERT INTO `warhammer_addon_db`.`user` (`id`, `user_name`, `password`, `email`, `balance`)
VALUES (3, 'Nikolay', 'w40k', 'nikolay@gmail.com', 5.0);
INSERT INTO `warhammer_addon_db`.`user` (`id`, `user_name`, `password`, `email`, `balance`)
VALUES (4, 'Oleg', 'w40k', 'oleg@gmail.com', 5.0);
COMMIT;

-- -----------------------------------------------------
-- Data for table `warhammer_addon_db`.`warrior`
-- -----------------------------------------------------
START TRANSACTION;
USE `warhammer_addon_db`;
INSERT INTO `warhammer_addon_db`.`warrior` (`id`, `warrior_name`, `balance`, experience, level, `agility`, `attack`,
                                            defence, warrior_speciality, user_id)
VALUES (1, 'Destroyer', '0', '0', 1, 3, 25.5, 15.5, 'HEAVY_WEAPON', 1);

COMMIT;

START TRANSACTION;
USE `warhammer_addon_db`;
INSERT INTO `warhammer_addon_db`.`armor` (`id`, `name`, `power`)
VALUES (1, 'Imperial Crusaders', 15.0);

COMMIT;

START TRANSACTION;
USE `warhammer_addon_db`;
INSERT INTO `warhammer_addon_db`.`weapon` (`id`, `name`, `power`)
VALUES (1, 'Grinder', 25.0);

COMMIT;

START TRANSACTION;
USE `warhammer_addon_db`;
INSERT INTO `warhammer_addon_db`.`skin` (`id`, `name`, `price`, `url`)
VALUES (1, 'terminator', 2, 'https://files.gamebanana.com/img/ss/srends/39757.jpg');

COMMIT;
