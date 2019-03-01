-- MySQL Workbench Forward Engineerin
-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema musicplayer
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema musicplayer
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `musicplayer`;
USE `musicplayer` ;

-- -----------------------------------------------------
-- Table `musicplayer`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`user` (
  `PK_UserID` INT(11) NOT NULL,
  `UserName` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `FirstName` VARCHAR(45) NULL DEFAULT NULL,
  `LastName` VARCHAR(45) NULL DEFAULT NULL,
  `Birthday` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`PK_UserID`));


-- -----------------------------------------------------
-- Table `musicplayer`.`album`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`album` (
  `PK_AlbumID` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_UserID` INT(11) NOT NULL,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `Artist` VARCHAR(45) NULL DEFAULT NULL,
  `Cover` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`PK_AlbumID`),
  INDEX `FK_UserID_idx` (`FK_UserID` ASC) ,
  CONSTRAINT `Album_UserID`
    FOREIGN KEY (`FK_UserID`)
    REFERENCES `musicplayer`.`user` (`PK_UserID`)
    ON DELETE CASCADE);

-- -----------------------------------------------------
-- Table `musicplayer`.`genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`genre` (
  `PK_GenreID` INT(11) NOT NULL,
  `FK_UserID` INT(11) NOT NULL,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_GenreID`),
  INDEX `Genre_UserID_idx` (`FK_UserID` ASC) ,
  CONSTRAINT `Genre_UserID`
    FOREIGN KEY (`FK_UserID`)
    REFERENCES `musicplayer`.`user` (`PK_UserID`)
    ON DELETE CASCADE);


-- -----------------------------------------------------
-- Table `musicplayer`.`playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`playlist` (
  `PK_PlaylistID` INT(11) NOT NULL,
  `FK_UserID` INT(11) NULL DEFAULT NULL,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `Favorite` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NULL DEFAULT 'No',
  PRIMARY KEY (`PK_PlaylistID`),
  INDEX `FK_UserID_idx` (`FK_UserID` ASC) ,
  CONSTRAINT `Playlist_UserID`
    FOREIGN KEY (`FK_UserID`)
    REFERENCES `musicplayer`.`user` (`PK_UserID`));


-- -----------------------------------------------------
-- Table `musicplayer`.`song`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`song` (
  `PK_SongID` INT(11) NOT NULL,
  `FK_UserID` INT(11) NULL DEFAULT NULL,
  `FK_AlbumID` INT(11) NULL DEFAULT NULL,
  `FK_GenreID` INT(11) NULL DEFAULT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Year` YEAR(4) NULL DEFAULT NULL,
  `Favorite` VARCHAR(45) NULL DEFAULT NULL,
  `PlayTime` INT(11) NULL DEFAULT NULL,
  `LastPlayed` DATETIME NULL DEFAULT NULL,
  `File` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`PK_SongID`),
  INDEX `Song_UserID_idx` (`FK_UserID` ASC) ,
  INDEX `Song_GenreID_idx` (`FK_GenreID` ASC) ,
  INDEX `Song_AlbumID_idx` (`FK_AlbumID` ASC) ,
  CONSTRAINT `Song_AlbumID`
    FOREIGN KEY (`FK_AlbumID`)
    REFERENCES `musicplayer`.`album` (`PK_AlbumID`)
    ON DELETE CASCADE,
  CONSTRAINT `Song_GenreID`
    FOREIGN KEY (`FK_GenreID`)
    REFERENCES `musicplayer`.`genre` (`PK_GenreID`)
    ON DELETE SET NULL,
  CONSTRAINT `Song_UserID`
    FOREIGN KEY (`FK_UserID`)
    REFERENCES `musicplayer`.`user` (`PK_UserID`)
    ON DELETE CASCADE);


-- -----------------------------------------------------
-- Table `musicplayer`.`playlistsong`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicplayer`.`playlistsong` (
  `FK_PlaylistID` INT(11) NOT NULL,
  `FK_SongID` INT(11) NOT NULL,
  PRIMARY KEY (`FK_PlaylistID`, `FK_SongID`),
  INDEX `FK_SongID_idx` (`FK_SongID` ASC) ,
  CONSTRAINT `FK_PlaylistID`
    FOREIGN KEY (`FK_PlaylistID`)
    REFERENCES `musicplayer`.`playlist` (`PK_PlaylistID`)
    ON DELETE CASCADE,
  CONSTRAINT `FK_SongID`
    FOREIGN KEY (`FK_SongID`)
    REFERENCES `musicplayer`.`song` (`PK_SongID`)
    ON DELETE CASCADE);