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
-- Table `musicplayer`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`user` (
  `PK_UserID` INT(11) NOT NULL AUTO_INCREMENT,
  `UserName` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `FirstName` VARCHAR(45) NULL DEFAULT NULL,
  `LastName` VARCHAR(45) NULL DEFAULT NULL,
  `Gender` VARCHAR(1) NULL DEFAULT NULL,
  `Birthday` TIMESTAMP(4) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_UserID`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`album`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`album` (
  `PK_AlbumID` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_UserID` INT(11) NOT NULL,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `Artist` VARCHAR(45) NULL DEFAULT NULL,
  `Cover` LONGBLOB NULL DEFAULT NULL,
  PRIMARY KEY (`PK_AlbumID`),
  INDEX `Album_UserID_idx` (`FK_UserID` ASC) VISIBLE,
  CONSTRAINT `Album_UserID`
    FOREIGN KEY (`FK_UserID`)
    REFERENCES `musicplayer`.`user` (`PK_UserID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`genre` (
  `PK_GenreID` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_UserID` INT(11) NOT NULL,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_GenreID`),
  INDEX `Genre_UserID_idx` (`FK_UserID` ASC) VISIBLE,
  CONSTRAINT `Genre_UserID`
    FOREIGN KEY (`FK_UserID`)
    REFERENCES `musicplayer`.`user` (`PK_UserID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`playlist` (
  `PK_PlaylistID` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_UserID` INT(11) NULL DEFAULT NULL,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `Favorite` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NULL DEFAULT 'No',
  PRIMARY KEY (`PK_PlaylistID`),
  INDEX `Playlist_UserID_idx` (`FK_UserID` ASC) VISIBLE,
  CONSTRAINT `Playlist_UserID`
    FOREIGN KEY (`FK_UserID`)
    REFERENCES `musicplayer`.`user` (`PK_UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`song`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`song` (
  `PK_SongID` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_UserID` INT(11) NULL DEFAULT NULL,
  `FK_AlbumID` INT(11) NULL DEFAULT NULL,
  `FK_GenreID` INT(11) NULL DEFAULT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Year` YEAR(4) NULL DEFAULT NULL,
  `Favorite` VARCHAR(45) NULL DEFAULT NULL,
  `PlayTime` INT(11) NULL DEFAULT NULL,
  `LastPlayed` TIMESTAMP(4) NULL DEFAULT NULL,
  `File` LONGBLOB NULL DEFAULT NULL,
  PRIMARY KEY (`PK_SongID`),
  INDEX `Song_GenreID_idx` (`FK_GenreID` ASC) VISIBLE,
  INDEX `Song_UserID_idx` (`FK_UserID` ASC) VISIBLE,
  INDEX `Song_AlbumID_idx` (`FK_AlbumID` ASC) VISIBLE,
  CONSTRAINT `Song_AlbumID`
    FOREIGN KEY (`FK_AlbumID`)
    REFERENCES `musicplayer`.`album` (`PK_AlbumID`)
    ON DELETE SET NULL,
  CONSTRAINT `Song_GenreID`
    FOREIGN KEY (`FK_GenreID`)
    REFERENCES `musicplayer`.`genre` (`PK_GenreID`)
    ON DELETE SET NULL,
  CONSTRAINT `Song_UserID`
    FOREIGN KEY (`FK_UserID`)
    REFERENCES `musicplayer`.`user` (`PK_UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicplayer`.`playlistsong`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`playlistsong` (
  `FK_PlaylistID` INT(11) NOT NULL,
  `FK_SongID` INT(11) NOT NULL,
  PRIMARY KEY (`FK_PlaylistID`, `FK_SongID`),
  INDEX `FK_SongID_idx` (`FK_SongID` ASC) VISIBLE,
  CONSTRAINT `FK_PlaylistID`
    FOREIGN KEY (`FK_PlaylistID`)
    REFERENCES `musicplayer`.`playlist` (`PK_PlaylistID`)
    ON DELETE CASCADE,
  CONSTRAINT `FK_SongID`
    FOREIGN KEY (`FK_SongID`)
    REFERENCES `musicplayer`.`song` (`PK_SongID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
