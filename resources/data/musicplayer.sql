CREATE DATABASE  IF NOT EXISTS `musicplayer` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `musicplayer`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: musicplayer
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `account` (
  `PK_AccountID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`PK_AccountID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'andrew','ing');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `album` (
  `PK_AlbumID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_ArtistID` int(11) DEFAULT NULL,
  `FK_UserID` int(11) DEFAULT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Cover` longblob,
  `DateCreated` timestamp(4) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_AlbumID`),
  KEY `FK_UserID_idx` (`FK_UserID`),
  KEY `FK_ArtistID_idx` (`FK_ArtistID`),
  CONSTRAINT `FK_ArtistID` FOREIGN KEY (`FK_ArtistID`) REFERENCES `artist` (`pk_artistid`),
  CONSTRAINT `FK_UserID` FOREIGN KEY (`FK_UserID`) REFERENCES `user` (`pk_userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist`
--

DROP TABLE IF EXISTS `artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `artist` (
  `PK_ArtistID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_AccountID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Genre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PK_ArtistID`),
  KEY `Artist_AccountID_idx` (`FK_AccountID`),
  CONSTRAINT `Artist_AccountID` FOREIGN KEY (`FK_AccountID`) REFERENCES `account` (`pk_accountid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES (1,1,'andrew','pop');
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `playlist` (
  `PK_PlaylistID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_UserID` int(11) DEFAULT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `DateCreated` timestamp(4) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_PlaylistID`),
  KEY `Playlist_UserID_idx` (`FK_UserID`),
  CONSTRAINT `Playlist_UserID` FOREIGN KEY (`FK_UserID`) REFERENCES `user` (`pk_userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlistsong`
--

DROP TABLE IF EXISTS `playlistsong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `playlistsong` (
  `FK_PlaylistID` int(11) NOT NULL,
  `FK_SongID` int(11) NOT NULL,
  PRIMARY KEY (`FK_PlaylistID`,`FK_SongID`),
  KEY `FK_SongID_idx` (`FK_SongID`),
  CONSTRAINT `FK_PlaylistID` FOREIGN KEY (`FK_PlaylistID`) REFERENCES `playlist` (`pk_playlistid`) ON DELETE CASCADE,
  CONSTRAINT `FK_SongID` FOREIGN KEY (`FK_SongID`) REFERENCES `song` (`pk_songid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlistsong`
--

LOCK TABLES `playlistsong` WRITE;
/*!40000 ALTER TABLE `playlistsong` DISABLE KEYS */;
/*!40000 ALTER TABLE `playlistsong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `song`
--

DROP TABLE IF EXISTS `song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `song` (
  `PK_SongID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_ArtistID` int(11) DEFAULT NULL,
  `FK_UserID` int(11) DEFAULT NULL,
  `FK_AlbumID` int(11) DEFAULT NULL,
  `Name` varchar(45) NOT NULL,
  `Genre` varchar(45) DEFAULT NULL,
  `Year` year(4) DEFAULT NULL,
  `File` longblob,
  PRIMARY KEY (`PK_SongID`),
  KEY `Song_UserID_idx` (`FK_UserID`),
  KEY `Song_AlbumID_idx` (`FK_AlbumID`),
  KEY `Song_ArtistID_idx` (`FK_ArtistID`),
  CONSTRAINT `Song_AlbumID` FOREIGN KEY (`FK_AlbumID`) REFERENCES `album` (`pk_albumid`) ON DELETE SET NULL,
  CONSTRAINT `Song_ArtistID` FOREIGN KEY (`FK_ArtistID`) REFERENCES `artist` (`pk_artistid`),
  CONSTRAINT `Song_UserID` FOREIGN KEY (`FK_UserID`) REFERENCES `user` (`pk_userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song`
--

LOCK TABLES `song` WRITE;
/*!40000 ALTER TABLE `song` DISABLE KEYS */;
/*!40000 ALTER TABLE `song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `subscription` (
  `FK_SubscriberID` int(11) NOT NULL,
  `FK_SubscribeeID` int(11) NOT NULL,
  KEY `subscriberID_idx` (`FK_SubscriberID`),
  KEY `subscribeeID_idx` (`FK_SubscribeeID`),
  CONSTRAINT `subscribeeID` FOREIGN KEY (`FK_SubscribeeID`) REFERENCES `account` (`pk_accountid`),
  CONSTRAINT `subscriberID` FOREIGN KEY (`FK_SubscriberID`) REFERENCES `account` (`pk_accountid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `PK_UserID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_AccountID` int(11) NOT NULL,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `Gender` varchar(1) DEFAULT NULL,
  `Birthday` timestamp(4) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_UserID`),
  KEY `User_AccountID_idx` (`FK_AccountID`),
  CONSTRAINT `User_AccountID` FOREIGN KEY (`FK_AccountID`) REFERENCES `account` (`pk_accountid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useralbum`
--

DROP TABLE IF EXISTS `useralbum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `useralbum` (
  `FK_UserID` int(11) NOT NULL,
  `FK_AlbumID` int(11) NOT NULL,
  `isFavorite` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`FK_UserID`,`FK_AlbumID`),
  KEY `Useralbum_AlbumID_idx` (`FK_AlbumID`),
  KEY `Useralbum_UserID_idx` (`FK_UserID`),
  CONSTRAINT `Useralbum_AlbumID` FOREIGN KEY (`FK_AlbumID`) REFERENCES `album` (`pk_albumid`),
  CONSTRAINT `Useralbum_UserID` FOREIGN KEY (`FK_UserID`) REFERENCES `user` (`pk_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useralbum`
--

LOCK TABLES `useralbum` WRITE;
/*!40000 ALTER TABLE `useralbum` DISABLE KEYS */;
/*!40000 ALTER TABLE `useralbum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userplaylist`
--

DROP TABLE IF EXISTS `userplaylist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `userplaylist` (
  `FK_UserID` int(11) NOT NULL,
  `FK_PlaylistID` int(11) NOT NULL,
  `isFavorite` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`FK_UserID`,`FK_PlaylistID`),
  KEY `Userplaylist_UserID_idx` (`FK_UserID`),
  KEY `Userplaylist_PlaylistID_idx` (`FK_PlaylistID`),
  CONSTRAINT `Userplaylist_PlaylistID` FOREIGN KEY (`FK_PlaylistID`) REFERENCES `playlist` (`pk_playlistid`),
  CONSTRAINT `Userplaylist_UserID` FOREIGN KEY (`FK_UserID`) REFERENCES `user` (`pk_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userplaylist`
--

LOCK TABLES `userplaylist` WRITE;
/*!40000 ALTER TABLE `userplaylist` DISABLE KEYS */;
/*!40000 ALTER TABLE `userplaylist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersong`
--

DROP TABLE IF EXISTS `usersong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usersong` (
  `FK_UserID` int(11) NOT NULL,
  `FK_SongID` int(11) NOT NULL,
  `isFavorite` tinyint(4) DEFAULT NULL,
  `playTime` int(11) DEFAULT NULL,
  `LastPlayed` timestamp(4) NULL DEFAULT NULL,
  PRIMARY KEY (`FK_UserID`,`FK_SongID`),
  KEY `SongID_idx` (`FK_SongID`),
  CONSTRAINT `usersong_SongID` FOREIGN KEY (`FK_SongID`) REFERENCES `song` (`pk_songid`),
  CONSTRAINT `usersong_UserID` FOREIGN KEY (`FK_UserID`) REFERENCES `user` (`pk_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersong`
--

LOCK TABLES `usersong` WRITE;
/*!40000 ALTER TABLE `usersong` DISABLE KEYS */;
/*!40000 ALTER TABLE `usersong` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-08 17:57:42
