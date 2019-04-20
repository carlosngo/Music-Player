-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema musicplayer
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema musicplayer
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `musicplayer` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `musicplayer` ;

-- -----------------------------------------------------
-- Table `musicplayer`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`account` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`account` (
  `PK_AccountID` INT(11) NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PK_AccountID`))
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`artist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`artist` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`artist` (
  `PK_ArtistID` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_AccountID` INT(11) NOT NULL,
  `FirstName` VARCHAR(45) NULL DEFAULT NULL,
  `LastName` VARCHAR(45) NULL DEFAULT NULL,
  `Gender` VARCHAR(1) NULL DEFAULT NULL,
  `Birthday` TIMESTAMP(4) NULL DEFAULT NULL,
  `Genre` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_ArtistID`),
  INDEX `Artist_AccountID_idx` (`FK_AccountID` ASC) VISIBLE,
  CONSTRAINT `Artist_AccountID`
    FOREIGN KEY (`FK_AccountID`)
    REFERENCES `musicplayer`.`account` (`PK_AccountID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`album`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`album` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`album` (
  `PK_AlbumID` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_ArtistID` INT(11) NULL DEFAULT NULL,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `Cover` LONGBLOB NULL DEFAULT NULL,
  `DateCreated` TIMESTAMP(4) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_AlbumID`),
  INDEX `FK_ArtistID_idx` (`FK_ArtistID` ASC) VISIBLE,
  CONSTRAINT `FK_ArtistID`
    FOREIGN KEY (`FK_ArtistID`)
    REFERENCES `musicplayer`.`artist` (`PK_ArtistID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`accountalbum`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`accountalbum` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`accountalbum` (
  `FK_AccountID` INT(11) NOT NULL,
  `FK_AlbumID` INT(11) NOT NULL,
  `isFavorite` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`FK_AlbumID`, `FK_AccountID`),
  INDEX `Useralbum_AlbumID_idx` (`FK_AlbumID` ASC) VISIBLE,
  INDEX `accountalbum_AccountID_idx` (`FK_AccountID` ASC) VISIBLE,
  CONSTRAINT `accountalbum_AccountID`
    FOREIGN KEY (`FK_AccountID`)
    REFERENCES `musicplayer`.`account` (`PK_AccountID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `accountalbum_AlbumID`
    FOREIGN KEY (`FK_AlbumID`)
    REFERENCES `musicplayer`.`album` (`PK_AlbumID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`playlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`playlist` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`playlist` (
  `PK_PlaylistID` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_AccountID` INT(11) NULL DEFAULT NULL,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `DateCreated` TIMESTAMP(4) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_PlaylistID`),
  INDEX `playlist_AccountID_idx` (`FK_AccountID` ASC) VISIBLE,
  CONSTRAINT `playlist_AccountID`
    FOREIGN KEY (`FK_AccountID`)
    REFERENCES `musicplayer`.`account` (`PK_AccountID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`accountplaylist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`accountplaylist` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`accountplaylist` (
  `FK_AccountID` INT(11) NOT NULL,
  `FK_PlaylistID` INT(11) NOT NULL,
  `isFavorite` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`FK_AccountID`, `FK_PlaylistID`),
  INDEX `Userplaylist_PlaylistID_idx` (`FK_PlaylistID` ASC) VISIBLE,
  CONSTRAINT `accountplaylist_AccountID`
    FOREIGN KEY (`FK_AccountID`)
    REFERENCES `musicplayer`.`account` (`PK_AccountID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `accountplaylist_PlaylistID`
    FOREIGN KEY (`FK_PlaylistID`)
    REFERENCES `musicplayer`.`playlist` (`PK_PlaylistID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`song`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`song` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`song` (
  `PK_SongID` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_ArtistID` INT(11) NULL DEFAULT NULL,
  `FK_AlbumID` INT(11) NULL DEFAULT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Genre` VARCHAR(45) NULL DEFAULT NULL,
  `Year` YEAR(4) NULL DEFAULT NULL,
  `File` LONGBLOB NULL DEFAULT NULL,
  `DateUploaded` TIMESTAMP(4) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_SongID`),
  INDEX `Song_AlbumID_idx` (`FK_AlbumID` ASC) VISIBLE,
  INDEX `Song_ArtistID_idx` (`FK_ArtistID` ASC) VISIBLE,
  CONSTRAINT `Song_AlbumID`
    FOREIGN KEY (`FK_AlbumID`)
    REFERENCES `musicplayer`.`album` (`PK_AlbumID`)
    ON UPDATE CASCADE,
  CONSTRAINT `Song_ArtistID`
    FOREIGN KEY (`FK_ArtistID`)
    REFERENCES `musicplayer`.`artist` (`PK_ArtistID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 51
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`accountsong`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`accountsong` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`accountsong` (
  `FK_AccountID` INT(11) NOT NULL,
  `FK_SongID` INT(11) NOT NULL,
  `isFavorite` TINYINT(1) NOT NULL DEFAULT '0',
  `playTime` INT(11) NOT NULL DEFAULT '0',
  `LastPlayed` TIMESTAMP(4) NULL DEFAULT NULL,
  PRIMARY KEY (`FK_AccountID`, `FK_SongID`),
  INDEX `SongID_idx` (`FK_SongID` ASC) VISIBLE,
  CONSTRAINT `accountsong_AccountID`
    FOREIGN KEY (`FK_AccountID`)
    REFERENCES `musicplayer`.`account` (`PK_AccountID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `accountsong_SongID`
    FOREIGN KEY (`FK_SongID`)
    REFERENCES `musicplayer`.`song` (`PK_SongID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`playlistsong`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`playlistsong` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`playlistsong` (
  `FK_PlaylistID` INT(11) NOT NULL,
  `FK_SongID` INT(11) NOT NULL,
  PRIMARY KEY (`FK_PlaylistID`, `FK_SongID`),
  INDEX `FK_SongID_idx` (`FK_SongID` ASC) VISIBLE,
  CONSTRAINT `FK_PlaylistID`
    FOREIGN KEY (`FK_PlaylistID`)
    REFERENCES `musicplayer`.`playlist` (`PK_PlaylistID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_SongID`
    FOREIGN KEY (`FK_SongID`)
    REFERENCES `musicplayer`.`song` (`PK_SongID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`subscription`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`subscription` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`subscription` (
  `FK_SubscriberID` INT(11) NOT NULL,
  `FK_SubscribeeID` INT(11) NOT NULL,
  PRIMARY KEY (`FK_SubscriberID`, `FK_SubscribeeID`),
  INDEX `subscriberID_idx` (`FK_SubscriberID` ASC) VISIBLE,
  INDEX `subscribeeID_idx` (`FK_SubscribeeID` ASC) VISIBLE,
  CONSTRAINT `subscribeeID`
    FOREIGN KEY (`FK_SubscribeeID`)
    REFERENCES `musicplayer`.`account` (`PK_AccountID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `subscriberID`
    FOREIGN KEY (`FK_SubscriberID`)
    REFERENCES `musicplayer`.`account` (`PK_AccountID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicplayer`.`user` ;

CREATE TABLE IF NOT EXISTS `musicplayer`.`user` (
  `PK_UserID` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_AccountID` INT(11) NOT NULL,
  `FirstName` VARCHAR(45) NULL DEFAULT NULL,
  `LastName` VARCHAR(45) NULL DEFAULT NULL,
  `Gender` VARCHAR(1) NULL DEFAULT NULL,
  `Birthday` TIMESTAMP(4) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_UserID`),
  INDEX `User_AccountID_idx` (`FK_AccountID` ASC) VISIBLE,
  CONSTRAINT `User_AccountID`
    FOREIGN KEY (`FK_AccountID`)
    REFERENCES `musicplayer`.`account` (`PK_AccountID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 30
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
