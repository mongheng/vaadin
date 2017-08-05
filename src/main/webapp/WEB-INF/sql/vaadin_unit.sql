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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-05 12:05:51