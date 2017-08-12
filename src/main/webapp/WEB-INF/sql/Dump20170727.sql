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
-- Table structure for table `cashflow`
--

DROP TABLE IF EXISTS `cashflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cashflow` (
  `CASHFLOW_ID` varchar(255) NOT NULL,
  `AMOUNT` float DEFAULT NULL,
  `ENDDATE` date DEFAULT NULL,
  `INSTALLMENT_NUMBER` int(11) DEFAULT NULL,
  `STARTDATE` date DEFAULT NULL,
  `STATU` tinyint(1) DEFAULT '0',
  `CONTRACT_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CASHFLOW_ID`),
  KEY `FK5shx6bg78l3mdpr68shjx9lwf` (`CONTRACT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cashflow`
--

LOCK TABLES `cashflow` WRITE;
/*!40000 ALTER TABLE `cashflow` DISABLE KEYS */;
INSERT INTO `cashflow` VALUES ('462a30e5-83a1-4a63-8722-7f2a165f3389',800,'2017-09-07',1,'2017-08-07',1,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('be78446f-aacd-419e-bfc3-e9847f515489',800,'2017-10-07',2,'2017-09-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('311eed72-e0dd-49ff-860f-ac00b0811915',800,'2017-11-07',3,'2017-10-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('00d26e9f-e247-4047-a1c9-6aa6b68d2307',800,'2017-12-07',4,'2017-11-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('cbd97098-d520-41bf-a810-2018919bd41e',800,'2018-01-07',5,'2017-12-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('9f1357b0-f070-4e60-9b13-7b338d8be131',800,'2018-02-07',6,'2018-01-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('f2ae8f1b-ab10-410d-94ca-159f03366ae8',800,'2018-03-07',7,'2018-02-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('4c5242bd-fcaf-4b02-80d0-0e427fd45541',800,'2018-04-07',8,'2018-03-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('c3aa5d40-2c89-4668-a5f7-ef356f25f51a',800,'2018-05-07',9,'2018-04-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('b7381931-0acc-47b4-a1c7-52c6ebcd5c69',800,'2018-06-07',10,'2018-05-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('393678f5-e2d1-483b-8bfc-1db642595727',800,'2018-07-07',11,'2018-06-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('aca44a2b-4a0a-4c5c-8d78-a77cca265100',800,'2018-08-07',12,'2018-07-07',0,'737b5fdc-a53e-49c4-9825-fecbbd28d563'),('4d151c76-d5ef-4d6e-b48c-b07d94030fa6',700,'2017-09-07',1,'2017-08-07',1,'86ecefc1-fc56-4770-90e4-b7bc61a4276a'),('21a2e905-61b5-4df6-88b1-7b3a89d3d922',700,'2017-10-07',2,'2017-09-07',1,'86ecefc1-fc56-4770-90e4-b7bc61a4276a'),('d476a530-ce91-4a24-b4f9-8c56074ae745',700,'2017-11-07',3,'2017-10-07',1,'86ecefc1-fc56-4770-90e4-b7bc61a4276a'),('e352abaa-f9be-4ccf-af8d-956f1d9732ac',700,'2017-12-07',4,'2017-11-07',1,'86ecefc1-fc56-4770-90e4-b7bc61a4276a'),('14b33efa-b883-4463-96a6-8f31cf8c4fae',700,'2018-01-07',5,'2017-12-07',1,'86ecefc1-fc56-4770-90e4-b7bc61a4276a'),('8a747fb2-4b1b-45cb-92bd-bd422e29349a',700,'2018-02-07',6,'2018-01-07',1,'86ecefc1-fc56-4770-90e4-b7bc61a4276a'),('c053a47e-9a71-4ff2-88c8-1d17aa483d42',700,'2018-03-07',7,'2018-02-07',1,'86ecefc1-fc56-4770-90e4-b7bc61a4276a'),('e64838ba-e640-468a-848c-3a3ac66c3168',1200,'2017-09-07',1,'2017-08-07',1,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('acf71fbf-5ff9-43d4-9a4c-0103bf7ca55f',1200,'2017-10-07',2,'2017-09-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('3d7e9c3e-a9cd-4988-bd03-57cd86e2de81',1200,'2017-11-07',3,'2017-10-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('13775715-6299-4dc1-8a82-e126d3759461',1200,'2017-12-07',4,'2017-11-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('6267076d-4deb-4e5d-a1ff-0227e9f32c29',1200,'2018-01-07',5,'2017-12-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('235e9a68-58bd-4d7d-8fe5-4ffde6e3256e',1200,'2018-02-07',6,'2018-01-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('b804d7e1-637a-4062-a1f7-262cdf355ee8',1200,'2018-03-07',7,'2018-02-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('868831c2-3c7e-4015-b7bf-f627765c62ab',1200,'2018-04-07',8,'2018-03-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('17f4696f-1cf5-4842-95cf-42a4552d5894',1200,'2018-05-07',9,'2018-04-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('88c6cf52-0c8b-4470-bcc1-08550f67277a',1200,'2018-06-07',10,'2018-05-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('aca46619-e724-4422-9d5e-cb3da850a0eb',1200,'2018-07-07',11,'2018-06-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('5b072842-bf18-45a6-a7a1-6c522d2422a5',1200,'2018-08-07',12,'2018-07-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('38be6967-d07e-4618-8835-523146a43c28',1200,'2018-09-07',13,'2018-08-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('d4048499-560b-4ee3-8920-1fa08ffb074f',1200,'2018-10-07',14,'2018-09-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('458b3a09-e3bd-4181-8a6b-543b6a7a648f',1200,'2018-11-07',15,'2018-10-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('73efc949-a765-4312-8c20-32c446ff038b',1200,'2018-12-07',16,'2018-11-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('e49a45b9-db01-4f9c-883d-925cfe46db3a',1200,'2019-01-07',17,'2018-12-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('77c38f7c-deff-4504-a72a-fec28e56927d',1200,'2019-02-07',18,'2019-01-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('1a126edb-7473-49d5-aa47-0b269444b144',1200,'2019-03-07',19,'2019-02-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('c7b08ccf-93f8-4789-912e-01ffbd7eca05',1200,'2019-04-07',20,'2019-03-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('98385b69-f218-44c1-9c92-5820882f7e96',1200,'2019-05-07',21,'2019-04-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('e636c6db-3d11-42f6-896e-259fef06e4db',1200,'2019-06-07',22,'2019-05-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('5fc0e7d0-35f9-49d7-aa43-abaa3d8dc615',1200,'2019-07-07',23,'2019-06-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('04a59686-9285-42b3-8787-8951a154996e',1200,'2019-08-07',24,'2019-07-07',0,'18a7b7e5-58b6-4995-892f-c0e3d3a5b643'),('00c64af7-dcc1-4d5e-b778-8c14be8530ad',900,'2017-09-07',1,'2017-08-07',1,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('cd47473f-14aa-4da5-be85-e0d760dc0968',900,'2017-10-07',2,'2017-09-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('4b06040f-1816-42d9-af8b-1dd08236d6ee',900,'2017-11-07',3,'2017-10-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('1d5614b7-d5ef-4fa5-9f90-f771b61b1cbd',900,'2017-12-07',4,'2017-11-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('0ff25601-47ca-499b-8e4f-b4b6dae03e59',900,'2018-01-07',5,'2017-12-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('3696fe64-bba1-41bd-ae9f-7d10e523fa01',900,'2018-02-07',6,'2018-01-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('6748f4ad-4832-48d0-ab71-6f94c0a96b8b',900,'2018-03-07',7,'2018-02-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('11a1f9e8-4228-4b32-b4f0-e716d6cc9076',900,'2018-04-07',8,'2018-03-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('b0de2f76-527b-48d4-9d8b-fd72ea2af57c',900,'2018-05-07',9,'2018-04-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('25bad500-7f5e-4201-8b01-9885deedf40b',900,'2018-06-07',10,'2018-05-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('8eb78bb6-6c0a-48ae-8232-1ca41a87b3b6',900,'2018-07-07',11,'2018-06-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('9d5a34cd-a6e2-425c-a3e6-170c866dd2b8',900,'2018-08-07',12,'2018-07-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('f9988c0c-2067-4fca-af7e-6adb65c90cd2',900,'2018-09-07',13,'2018-08-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('e0b248b6-e61b-47b7-805e-d45b49af418d',900,'2018-10-07',14,'2018-09-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('57005e30-35dc-4bb9-b227-0a0fdfcf0ef3',900,'2018-11-07',15,'2018-10-07',0,'9d194e93-2e10-4864-aaf3-67309c21cca6'),('9f9bed11-33ee-4bf1-9f4e-7ae0e23af35e',1000,'2017-10-08',1,'2017-09-08',1,'8c643396-f037-460a-a08e-db88f43b170a'),('2e512371-db01-439c-acad-00a0e90ae995',1000,'2017-11-08',2,'2017-10-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('5a45d8ad-19a1-4f81-a49c-57d2d2130fa6',1000,'2017-12-08',3,'2017-11-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('b038cdfe-1427-4d0f-a209-d158f1d647f8',1000,'2018-01-08',4,'2017-12-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('3a36dded-c36d-4536-b5df-a240bf046136',1000,'2018-02-08',5,'2018-01-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('c2af0143-1b8f-4121-8b91-649066d7fb95',1000,'2018-03-08',6,'2018-02-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('1b2a87da-1c6c-4af3-83a8-3f70cad2da8d',1000,'2018-04-08',7,'2018-03-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('7be83d98-a016-41ad-bec5-a61827c7570b',1000,'2018-05-08',8,'2018-04-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('1571a15b-c9b8-461a-b4d2-2582830caad5',1000,'2018-06-08',9,'2018-05-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('1b266697-f4e6-4b00-b10c-8b5772870e88',1000,'2018-07-08',10,'2018-06-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('0203d4a2-d178-4dc7-b333-9e5bb242bd4c',1000,'2018-08-08',11,'2018-07-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('1dcc4ae3-bbf7-42bd-98ed-d52b9dcd64e5',1000,'2018-09-08',12,'2018-08-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('f3954c52-9464-4876-bc6d-f2a7ade37ede',1000,'2018-10-08',13,'2018-09-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('e1f9c9f2-06cb-4e40-8166-b144bbe25d6b',1000,'2018-11-08',14,'2018-10-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('d6f55a69-3cca-4586-99be-453b9d70bc2e',1000,'2018-12-08',15,'2018-11-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('dd1922c4-e2e0-4f21-b552-908de4a10568',1000,'2019-01-08',16,'2018-12-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('7c9b69fb-dc84-4564-92a3-d2ac85cdfa47',1000,'2019-02-08',17,'2019-01-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('1a74efbb-f868-4606-a4c7-9346828e48aa',1000,'2019-03-08',18,'2019-02-08',0,'8c643396-f037-460a-a08e-db88f43b170a'),('8cde165f-c9b5-41dd-acd6-af32126f84d8',950,'2017-09-10',1,'2017-08-10',1,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('c690c689-586d-4481-9242-f1468014aa74',950,'2017-10-10',2,'2017-09-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('d2e8d496-1010-4854-8689-55f871ef9569',950,'2017-11-10',3,'2017-10-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('8ff3012c-cc34-418a-a445-30352166c98d',950,'2017-12-10',4,'2017-11-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('f9639bf3-3948-41bf-8546-aede32ff2a9f',950,'2018-01-10',5,'2017-12-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('dec78b67-4430-49af-9fbb-3e6a7f2495c1',950,'2018-02-10',6,'2018-01-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('689dc937-ce73-434c-8e8c-cc782c7a3429',950,'2018-03-10',7,'2018-02-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('37d08059-509a-4caa-9c22-2864015032dc',950,'2018-04-10',8,'2018-03-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('3765f1e0-cc7e-463e-9ee0-669bab7562bf',950,'2018-05-10',9,'2018-04-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('3a05e018-8aba-444f-bece-44031d4b296c',950,'2018-06-10',10,'2018-05-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('49cb63fc-8e64-40e6-a738-58f4271a4067',950,'2018-07-10',11,'2018-06-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('8e6ef507-9062-4e4f-8ef4-d72c4d84163a',950,'2018-08-10',12,'2018-07-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('80010394-faa3-4226-b243-22fceb24afac',950,'2018-09-10',13,'2018-08-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('3dffaf65-503c-46df-9f3d-0678ee38dfd7',950,'2018-10-10',14,'2018-09-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('ba669a5c-69a1-4be4-90d4-7704211bca5b',950,'2018-11-10',15,'2018-10-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('c4ab3f36-ffd2-4531-a268-cb495a17d898',950,'2018-12-10',16,'2018-11-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('c654436d-6c5e-4131-aa44-b94a437b1db5',950,'2019-01-10',17,'2018-12-10',0,'ad2e788e-b7fe-454b-bea6-fe53c420236d'),('4460e5a7-a0ac-4597-95a4-d238886e66cc',850,'2017-09-10',1,'2017-08-10',1,'d121206b-2803-481d-9fab-c9f88c60e229'),('9ea2cdfc-7672-4ae4-97c0-e26067826f6f',850,'2017-10-10',2,'2017-09-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('c11c1f26-4951-48de-a9ef-c74926f2158a',850,'2017-11-10',3,'2017-10-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('8483ec6e-2cca-427a-8fdb-f8a95925fdb9',850,'2017-12-10',4,'2017-11-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('7b32f26f-334d-4b5f-91e7-94e4f35edc16',850,'2018-01-10',5,'2017-12-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('2aaa3127-aa58-4ff6-8855-52cc45cacb3b',850,'2018-02-10',6,'2018-01-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('45eabd94-6111-4667-9f7d-7ebe3a38d89c',850,'2018-03-10',7,'2018-02-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('4e692dd1-4062-478a-90d5-eed404b58afe',850,'2018-04-10',8,'2018-03-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('572decc1-2bbc-4b35-b3a2-9396141eec3b',850,'2018-05-10',9,'2018-04-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('bf2faf65-3a35-4cfa-bdc6-d59c41956244',850,'2018-06-10',10,'2018-05-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('5f5a93f8-1804-4832-8554-51bf46d4014b',850,'2018-07-10',11,'2018-06-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('1ff55cb8-6879-4be1-9b19-cd774a598bf3',850,'2018-08-10',12,'2018-07-10',0,'d121206b-2803-481d-9fab-c9f88c60e229'),('1800626a-5c34-4aaf-ba25-e5cb98c9954c',1050,'2017-09-10',1,'2017-08-10',1,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('a9648353-c823-4f65-9829-76cc3b796b9d',1050,'2017-10-10',2,'2017-09-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('bbf084f8-7e5f-4d17-8c76-58e769408478',1050,'2017-11-10',3,'2017-10-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('7e50a677-b26a-486a-8503-6ee73d88bde3',1050,'2017-12-10',4,'2017-11-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('86552512-d222-4e70-8398-d143a92cedcd',1050,'2018-01-10',5,'2017-12-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('db373607-079f-4a4a-8530-1a6223f7276b',1050,'2018-02-10',6,'2018-01-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('dd199e30-5b1e-4721-88e1-12fbbb318b73',1050,'2018-03-10',7,'2018-02-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('b035cdcc-cffc-402e-bcd4-c51f525bbfbe',1050,'2018-04-10',8,'2018-03-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('415411ef-8d5b-42af-9a9d-f3e17f14864d',1050,'2018-05-10',9,'2018-04-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('8c17c1cb-b505-4b12-9b58-c30ed7bf0422',1050,'2018-06-10',10,'2018-05-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('df11874f-85d9-4581-a4f9-fbaf10c34829',1050,'2018-07-10',11,'2018-06-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('0f8b02a1-5713-4ab4-a9de-6253b35fc4ee',1050,'2018-08-10',12,'2018-07-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('8b48b4d8-2f19-4f18-9a3f-df8fb2b456af',1050,'2018-09-10',13,'2018-08-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44'),('7210c7c9-c5ca-4269-996c-fd68b06976b0',1050,'2018-10-10',14,'2018-09-10',0,'5bed8a6f-5603-4f64-8a10-636b51467d44');
/*!40000 ALTER TABLE `cashflow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract` (
  `CONTRACT_ID` varchar(255) NOT NULL,
  `AMOUNT` float DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  `TERM` int(11) DEFAULT NULL,
  `CUSTOMER_ID` varchar(255) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`CONTRACT_ID`),
  KEY `FKnmcw8s7r4721islfagat54ja2` (`CUSTOMER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES ('86ecefc1-fc56-4770-90e4-b7bc61a4276a',700,'2018-03-07','2017-08-07',7,'b06442f8-866d-447d-b50e-10abd2a369a4',1),('737b5fdc-a53e-49c4-9825-fecbbd28d563',800,'2018-08-07','2017-08-07',12,'110cd2ac-1dd5-4b25-b392-636c6882a37b',1),('18a7b7e5-58b6-4995-892f-c0e3d3a5b643',1200,'2019-08-07','2017-08-07',24,'66f162fa-9459-4c78-9179-e2a1bcd484fc',1),('9d194e93-2e10-4864-aaf3-67309c21cca6',900,'2018-11-07','2017-08-07',15,'d29c8808-5d29-490d-9ec1-fb8cdf75e11d',1),('8c643396-f037-460a-a08e-db88f43b170a',1000,'2019-03-08','2017-09-08',18,'1ead89b6-4076-4ac9-a2b8-af5c161e70b2',1),('ad2e788e-b7fe-454b-bea6-fe53c420236d',950,'2019-01-10','2017-08-10',17,'e25502c0-4437-4caf-8fb6-ccc63db4d225',1),('d121206b-2803-481d-9fab-c9f88c60e229',850,'2018-08-10','2017-08-10',12,'f63a7275-721e-41ef-9a0e-51315bca6184',1),('5bed8a6f-5603-4f64-8a10-636b51467d44',1050,'2018-10-10','2017-08-10',14,'59960d5e-13aa-48b5-aebf-ca02ef3335ae',1);
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
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
  `CLOSE` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`CUSTOMER_ID`),
  KEY `FKae8b81k75mycryjm3hopdayvw` (`UNIT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('110cd2ac-1dd5-4b25-b392-636c6882a37b','Phnom Penh, Cambodia','Lida','1993-01-31','Female','Developer','016556955','dd756f47-99bb-4e3d-ba98-0f99eab295c7',0),('b06442f8-866d-447d-b50e-10abd2a369a4','Phnom Penh, Cambodia.','Mark','1990-08-01','Male','Network','016345678','021f0f41-0724-4e5a-8acf-a08c8f9d6d49',1),('66f162fa-9459-4c78-9179-e2a1bcd484fc','J32, Jade, Phnom Penh, Cambodian.','Fong','1989-10-10','Male','Sale','097345678','d76d7fa6-3d1f-4c25-b1d9-9e2f4ccdfdd5',0),('d29c8808-5d29-490d-9ec1-fb8cdf75e11d','Phnom Penh, Cambodia;','Song','1997-12-12','Male','HR','096345678','a783edad-a421-445d-a422-d4c55d3dd5fe',0),('1ead89b6-4076-4ac9-a2b8-af5c161e70b2','Phnom Penh, Cambodia.','Pov','1989-09-08','Male','Programmer','016914915','8967c3bf-1a53-4ffa-a81a-4bdcab5df36e',0),('e25502c0-4437-4caf-8fb6-ccc63db4d225','Phnom Penh, Cambodia.','Hav','1989-09-09','Male','Business','012713113','8313832a-1c29-420e-8554-1f2c607f4449',0),('f63a7275-721e-41ef-9a0e-51315bca6184','Phnom Penh, Cambodia.','Yina','1994-12-12','Female','sale','097777742','2765bc1d-6c51-4f35-8b7f-2f4806957a93',0),('59960d5e-13aa-48b5-aebf-ca02ef3335ae','Phnom Penh, Cambodia.','Doms','1986-02-18','Male','Developer','096666616','e250ce1b-8538-46e7-960a-0d44284dddc4',0);
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
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `PAYMENT_ID` varchar(255) NOT NULL,
  `AMOUNT` float DEFAULT NULL,
  `CUSTOMER_NAME` varchar(255) DEFAULT NULL,
  `FLOOR_NUMBER` int(11) DEFAULT NULL,
  `INSTALLMENT_NUMBER` int(11) DEFAULT NULL,
  `UNIT_NUMBER` int(11) DEFAULT NULL,
  `PAYMENT_DATE` date DEFAULT NULL,
  PRIMARY KEY (`PAYMENT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES ('4d151c76-d5ef-4d6e-b48c-b07d94030fa6',700,'Mark',2,1,202,'2017-08-09'),('00c64af7-dcc1-4d5e-b778-8c14be8530ad',900,'Song',1,1,103,'2017-08-09'),('462a30e5-83a1-4a63-8722-7f2a165f3389',800,'Lida',2,1,201,'2017-08-09'),('e64838ba-e640-468a-848c-3a3ac66c3168',1200,'Fong',1,1,102,'2017-08-09'),('1800626a-5c34-4aaf-ba25-e5cb98c9954c',1050,'Doms',4,1,401,'2017-08-11'),('8cde165f-c9b5-41dd-acd6-af32126f84d8',950,'Hav',1,1,101,'2017-08-11'),('4460e5a7-a0ac-4597-95a4-d238886e66cc',850,'Yina',2,1,203,'2017-08-11'),('9f9bed11-33ee-4bf1-9f4e-7ae0e23af35e',1000,'Pov',1,1,104,'2017-08-11'),('21a2e905-61b5-4df6-88b1-7b3a89d3d922',700,'Mark',2,2,202,'2017-08-11'),('d476a530-ce91-4a24-b4f9-8c56074ae745',700,'Mark',2,3,202,'2017-08-11'),('e352abaa-f9be-4ccf-af8d-956f1d9732ac',700,'Mark',2,4,202,'2017-08-11'),('14b33efa-b883-4463-96a6-8f31cf8c4fae',700,'Mark',2,5,202,'2017-08-11'),('8a747fb2-4b1b-45cb-92bd-bd422e29349a',700,'Mark',2,6,202,'2017-08-11'),('c053a47e-9a71-4ff2-88c8-1d17aa483d42',700,'Mark',2,7,202,'2017-08-11');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
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
INSERT INTO `unit` VALUES ('8313832a-1c29-420e-8554-1f2c607f4449',101,'31254ce7-6a63-4524-bb09-f07d6e08aa87',''),('d76d7fa6-3d1f-4c25-b1d9-9e2f4ccdfdd5',102,'31254ce7-6a63-4524-bb09-f07d6e08aa87',''),('a783edad-a421-445d-a422-d4c55d3dd5fe',103,'31254ce7-6a63-4524-bb09-f07d6e08aa87',''),('8967c3bf-1a53-4ffa-a81a-4bdcab5df36e',104,'31254ce7-6a63-4524-bb09-f07d6e08aa87',''),('dd756f47-99bb-4e3d-ba98-0f99eab295c7',201,'1e6a3606-48ad-419b-98ad-fb3e1ab4c587','\0'),('021f0f41-0724-4e5a-8acf-a08c8f9d6d49',202,'1e6a3606-48ad-419b-98ad-fb3e1ab4c587','\0'),('2765bc1d-6c51-4f35-8b7f-2f4806957a93',203,'1e6a3606-48ad-419b-98ad-fb3e1ab4c587',''),('38883a27-acaa-4da6-b876-2948aa96118c',204,'1e6a3606-48ad-419b-98ad-fb3e1ab4c587',''),('e250ce1b-8538-46e7-960a-0d44284dddc4',401,'58446169-b1b4-408d-b110-b4be38a8bcae',''),('4b6fdf9c-8254-48fd-b453-1239ac2d23b6',402,'58446169-b1b4-408d-b110-b4be38a8bcae','\0');
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

-- Dump completed on 2017-08-12 11:24:32
