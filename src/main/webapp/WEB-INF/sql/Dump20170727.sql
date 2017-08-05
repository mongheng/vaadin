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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `CUSTOMER_ID` varchar(255) NOT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `CUSTOMER_NAME` varchar(255) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `GENDER` varchar(255) DEFAULT NULL,
  `JOB` varchar(255) DEFAULT NULL,
  `PHONE_NUMBER` varchar(255) DEFAULT NULL,
  `UNIT_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CUSTOMER_ID`),
  KEY `FKae8b81k75mycryjm3hopdayvw` (`UNIT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('110cd2ac-1dd5-4b25-b392-636c6882a37b','Phnom Penh, Cambodia','Lida','1993-01-31','Female','Developer','016556955','dd756f47-99bb-4e3d-ba98-0f99eab295c7'),('b06442f8-866d-447d-b50e-10abd2a369a4','Phnom Penh, Cambodia.','Mark','1990-08-01','Male','Network','016345678','021f0f41-0724-4e5a-8acf-a08c8f9d6d49'),('66f162fa-9459-4c78-9179-e2a1bcd484fc','J32, Jade, Phnom Penh, Cambodian.','Fong','1989-10-10','Male','Sale','097345678','d76d7fa6-3d1f-4c25-b1d9-9e2f4ccdfdd5'),('d29c8808-5d29-490d-9ec1-fb8cdf75e11d','Phnom Penh, Cambodia;','Song','1997-12-12','Male','HR','096345678','a783edad-a421-445d-a422-d4c55d3dd5fe');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
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
  `STATU` bit(1) DEFAULT NULL,
  PRIMARY KEY (`UNIT_ID`),
  KEY `FKrtwgbkspi9hbyjoq8nskhmnbl` (`FLOOR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` VALUES ('8313832a-1c29-420e-8554-1f2c607f4449',101,'31254ce7-6a63-4524-bb09-f07d6e08aa87','\0'),('d76d7fa6-3d1f-4c25-b1d9-9e2f4ccdfdd5',102,'31254ce7-6a63-4524-bb09-f07d6e08aa87',''),('a783edad-a421-445d-a422-d4c55d3dd5fe',103,'31254ce7-6a63-4524-bb09-f07d6e08aa87',''),('8967c3bf-1a53-4ffa-a81a-4bdcab5df36e',104,'31254ce7-6a63-4524-bb09-f07d6e08aa87','\0'),('dd756f47-99bb-4e3d-ba98-0f99eab295c7',201,'1e6a3606-48ad-419b-98ad-fb3e1ab4c587','\0'),('021f0f41-0724-4e5a-8acf-a08c8f9d6d49',202,'1e6a3606-48ad-419b-98ad-fb3e1ab4c587',''),('2765bc1d-6c51-4f35-8b7f-2f4806957a93',203,'1e6a3606-48ad-419b-98ad-fb3e1ab4c587','\0'),('38883a27-acaa-4da6-b876-2948aa96118c',204,'1e6a3606-48ad-419b-98ad-fb3e1ab4c587',''),('e250ce1b-8538-46e7-960a-0d44284dddc4',401,'58446169-b1b4-408d-b110-b4be38a8bcae','\0'),('4b6fdf9c-8254-48fd-b453-1239ac2d23b6',402,'58446169-b1b4-408d-b110-b4be38a8bcae','\0');
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

-- Dump completed on 2017-08-05 12:05:45
