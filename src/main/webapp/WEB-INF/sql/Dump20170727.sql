-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: vaadin
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `access_roles`
--

DROP TABLE IF EXISTS `access_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `access_roles` (
  `ROLE_ID` varchar(255) NOT NULL,
  `ROLE_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access_roles`
--

LOCK TABLES `access_roles` WRITE;
/*!40000 ALTER TABLE `access_roles` DISABLE KEYS */;
INSERT INTO `access_roles` VALUES ('5fe942e6-7d77-48a0-bbf4-b7094cb1cf80','Manager'),('eb8cb55c-604f-4408-8bc7-c2d173586189','User'),('5a2f0cab-240e-49e2-b41c-bdfa5b8248f6','HR'),('603d38c3-a902-45d0-a640-ceba6f1cfc88','Technical'),('c24266d6-28ee-4a3d-859a-2cb514c46115','Product Manager');
/*!40000 ALTER TABLE `access_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `floor`
--

DROP TABLE IF EXISTS `floor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `floor` (
  `FLOOR_ID` varchar(255) NOT NULL,
  `FLOOR_NUMBER` int(11) DEFAULT NULL,
  `TOTAL_FLOOR` int(11) DEFAULT NULL,
  PRIMARY KEY (`FLOOR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `floor`
--

LOCK TABLES `floor` WRITE;
/*!40000 ALTER TABLE `floor` DISABLE KEYS */;
INSERT INTO `floor` VALUES ('31254ce7-6a63-4524-bb09-f07d6e08aa87',1,4),('1e6a3606-48ad-419b-98ad-fb3e1ab4c587',2,4),('58446169-b1b4-408d-b110-b4be38a8bcae',4,5),('46577e08-aa8f-43f6-811f-19669edcb496',5,3);
/*!40000 ALTER TABLE `floor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unit` (
  `UNIT_ID` varchar(255) NOT NULL,
  `UNIT_NUMBER` int(11) DEFAULT NULL,
  `FLOOR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UNIT_ID`),
  KEY `FKrtwgbkspi9hbyjoq8nskhmnbl` (`FLOOR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `USER_ID` varchar(255) NOT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `TELEPHONE` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  KEY `FKg89s6fkd55tvmmk22ov263cdt` (`ROLE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('670c2fad-9bae-46ac-9d49-3dff500d0b08','mongheng@mail.com','12345','081345671','mongheng','5fe942e6-7d77-48a0-bbf4-b7094cb1cf80'),('27abfc1c-a909-4df7-b83f-e8c80559c9b2','sochenda@gmail.com','12345','012345678','sochenda','eb8cb55c-604f-4408-8bc7-c2d173586189'),('4489d1e1-1b51-4dbe-9c6c-d2df827080ab','tian@gmail.com','12345','016345678','tian','5a2f0cab-240e-49e2-b41c-bdfa5b8248f6'),('989d0da5-e7f8-479f-92a0-190365b1f719','sok@gmail.com','12345','015345678','sok','603d38c3-a902-45d0-a640-ceba6f1cfc88'),('d500eb10-f01e-4a6c-afaf-03805cbed406','hellen@gmail.com','12345','092345678','hellen','c24266d6-28ee-4a3d-859a-2cb514c46115'),('0f8369d5-1d80-47c7-8e63-6dbd2995ce54','san@gmail.com','12345','012713113','san','5a2f0cab-240e-49e2-b41c-bdfa5b8248f6'),('41ba91a6-d67d-4f79-85d0-08a6e02dd9ae','jeff@gmail.com','12345','097345678','jeff','5fe942e6-7d77-48a0-bbf4-b7094cb1cf80');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-27 18:07:31
