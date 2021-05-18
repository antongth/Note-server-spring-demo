-- SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
-- SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
-- SET SQL_MODE=@OLD_SQL_MODE;
-- SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Schema notes
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `notes` ;
CREATE SCHEMA IF NOT EXISTS `notes` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `notes` ;
-- -----------------------------------------------------
-- Table `notes`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notes`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `pass` VARCHAR(45) NOT NULL,
--  `sessionId` VARCHAR(45) NULL DEFAULT NULL,
  `rating` INT(1) NULL DEFAULT 0,
  `status` ENUM('USER', 'SUPER', 'TEST') NOT NULL DEFAULT 'USER',
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `patronymic` VARCHAR(45) NULL,
  `state` ENUM('DELETED', 'ACTIVE', 'RESTORED') NOT NULL DEFAULT 'ACTIVE',
  `timeRegistered` DATE NULL,
  `oldPassword` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
--  UNIQUE INDEX `isActiveSession_UNIQUE` (`sessionId` ASC))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `notes`.`usersession`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notes`.`usersession` (
  `userId` INT NOT NULL,
  `sessionId` VARCHAR(45) NOT NULL,
  `lastAction` DATE NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `sessionId_UNIQUE` (`sessionId` ASC) ,
  CONSTRAINT `fk_user-session_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `notes`.`user` (`id`)
    ON DELETE cascade
    ON UPDATE cascade)
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `notes`.`folower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notes`.`follower` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `userIdFollower` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_followers_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `notes`.`user` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_followers_user2`
    FOREIGN KEY (`userIdFollower`)
    REFERENCES `notes`.`user` (`id`)
    ON DELETE cascade
    ON UPDATE cascade)
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `notes`.`ignored-by`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notes`.`ignore` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `userIdIgnored` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_ignore_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `notes`.`user` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_ignore_user2`
    FOREIGN KEY (`userIdIgnored`)
    REFERENCES `notes`.`user` (`id`)
    ON DELETE cascade
    ON UPDATE cascade)
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `notes`.`section`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `notes`.`section` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  CONSTRAINT `fk_volume_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `notes`.`user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `notes`.`note`
-- -----------------------------------------------------
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
CREATE TABLE IF NOT EXISTS `notes`.`note` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NULL,
  `revisionId` INT NULL,
  `sectionId` INT NULL,
  `subject` VARCHAR(20) NULL,
  `rating` INT(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `revisionId_UNIQUE` (`revisionId` ASC) ,
  CONSTRAINT `fk_note_user`
    FOREIGN KEY (`userId`)
    REFERENCES `notes`.`user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_note_revision`
    FOREIGN KEY (`revisionId`)
    REFERENCES `notes`.`revision` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_note_section1`
    FOREIGN KEY (`sectionId`)
    REFERENCES `notes`.`section` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `notes`.`revision`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notes`.`revision` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `noteId` INT NOT NULL,
  `sectionId` INT NOT NULL,
  `body` VARCHAR(45) NULL,
  `created` DATE NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_NoteBacks_note1`
    FOREIGN KEY (`noteId`)
    REFERENCES `notes`.`note` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_revision_section1`
    FOREIGN KEY (`sectionId`)
    REFERENCES `notes`.`section` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
-- -----------------------------------------------------
-- Table `notes`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notes`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `noteId` INT NOT NULL,
  `userId` INT NULL,
  `revisionId` INT NULL,
  `body` VARCHAR(45) NOT NULL,
  `created` DATE NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_comments_note1`
    FOREIGN KEY (`noteId`)
    REFERENCES `notes`.`note` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comments_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `notes`.`user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_revision1`
    FOREIGN KEY (`revisionId`)
    REFERENCES `notes`.`revision` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

INSERT INTO `notes`.`user` (`login`, `pass`, `firstName`, `lastName`, `patronymic`, `status`, `timeRegistered`) VALUES ("admin", "123456", "sysadmin", "anykey", "notdevops", "SUPER", now());
