start transaction; 
 
create database `acme-rookies`; 
 
use `acme-rookies`; 
 
create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577'; 
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F'; 
 
grant select, insert, update, delete on `acme-rookies`.* to 'acme-user'@'%'; 
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables, lock tables, create view, create routine, alter routine, execute, trigger, show view on `acme-rookies`.* to 'acme-manager'@'%'; 
 
 
-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: acme-rookies
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor_credit_cards`
--

DROP TABLE IF EXISTS `actor_credit_cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_credit_cards` (
  `actor` int(11) NOT NULL,
  `credit_cards` int(11) NOT NULL,
  UNIQUE KEY `UK_5joicgghxrrobgxtj0wsdnb3s` (`credit_cards`),
  CONSTRAINT `FK_5joicgghxrrobgxtj0wsdnb3s` FOREIGN KEY (`credit_cards`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_credit_cards`
--

LOCK TABLES `actor_credit_cards` WRITE;
/*!40000 ALTER TABLE `actor_credit_cards` DISABLE KEYS */;
INSERT INTO `actor_credit_cards` VALUES (1008,999),(1009,1000),(1029,1002),(1006,1003),(952,1004);
/*!40000 ALTER TABLE `actor_credit_cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_messages`
--

DROP TABLE IF EXISTS `actor_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_messages` (
  `actor` int(11) NOT NULL,
  `messages` int(11) NOT NULL,
  UNIQUE KEY `UK_b1336eo2tc3f26b38f2drhyk8` (`messages`),
  CONSTRAINT `FK_b1336eo2tc3f26b38f2drhyk8` FOREIGN KEY (`messages`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_messages`
--

LOCK TABLES `actor_messages` WRITE;
/*!40000 ALTER TABLE `actor_messages` DISABLE KEYS */;
INSERT INTO `actor_messages` VALUES (1029,1049),(1029,1050),(1029,1051),(1029,1052),(1029,1053),(1029,1054),(1030,1055),(1031,1056);
/*!40000 ALTER TABLE `actor_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_social_profiles`
--

DROP TABLE IF EXISTS `actor_social_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_social_profiles` (
  `actor` int(11) NOT NULL,
  `social_profiles` int(11) NOT NULL,
  UNIQUE KEY `UK_4suhrykpl9af1ubs85ycbyt6q` (`social_profiles`),
  CONSTRAINT `FK_4suhrykpl9af1ubs85ycbyt6q` FOREIGN KEY (`social_profiles`) REFERENCES `social_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_social_profiles`
--

LOCK TABLES `actor_social_profiles` WRITE;
/*!40000 ALTER TABLE `actor_social_profiles` DISABLE KEYS */;
INSERT INTO `actor_social_profiles` VALUES (1008,992),(1009,993),(1009,994),(1029,995),(1006,997),(952,998);
/*!40000 ALTER TABLE `actor_social_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surnames` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (952,0,'Reina Mercedes st..','admin1@acme.com','\0','','Admin','951236498','https://photos.com/admin.jpg','Admin','00000-ZZZ',942);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `answer_link` varchar(255) DEFAULT NULL,
  `register_moment` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `submitted_moment` datetime DEFAULT NULL,
  `curricula` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  `problem` int(11) NOT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hsw5exxa4qe3jxi8xdhnn0dl5` (`curricula`),
  UNIQUE KEY `UK_67lpdm77p0ia8oehbe0j4re24` (`position`,`rookie`),
  KEY `FK_7gn6fojv7rim6rxyglc6n9mt2` (`problem`),
  KEY `FK_dq1om37bx4hgli24rbkjo2n7` (`rookie`),
  CONSTRAINT `FK_dq1om37bx4hgli24rbkjo2n7` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`),
  CONSTRAINT `FK_7gn6fojv7rim6rxyglc6n9mt2` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`),
  CONSTRAINT `FK_cqpb54jon3yjef814oj6g6o4n` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_hsw5exxa4qe3jxi8xdhnn0dl5` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1043,0,'','','2018-01-04 00:00:00','PENDING',NULL,964,1018,1010,1029),(1044,0,'Answer Problem 5','http://github.com/answer5','2018-01-04 00:00:00','SUBMITTED','2018-02-04 00:00:00',968,1022,1010,1029),(1045,0,'Answer Problem 2','http://github.com/answer3','2018-01-05 00:00:00','SUBMITTED','2018-02-07 00:00:00',972,1025,1011,1029),(1046,0,'','','2018-01-04 00:00:00','PENDING',NULL,976,1026,1015,1029),(1047,0,'Answer Problem 6','http://github.com/answer5','2018-01-04 00:00:00','SUBMITTED','2018-02-04 00:00:00',980,1027,1015,1029),(1048,0,'Answer Problem 2','http://github.com/answer6','2018-01-05 00:00:00','REJECTED','2018-02-07 00:00:00',984,1028,1011,1029);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_final_mode` bit(1) DEFAULT NULL,
  `moment` date DEFAULT NULL,
  `score` double DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `auditor` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3m6p53wfvy7kcl4f4fvphkh9h` (`auditor`),
  KEY `FK_bumsxo4hc038y25pbfsinc38u` (`position`),
  CONSTRAINT `FK_bumsxo4hc038y25pbfsinc38u` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_3m6p53wfvy7kcl4f4fvphkh9h` FOREIGN KEY (`auditor`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
INSERT INTO `audit` VALUES (1039,0,'\0','2011-03-03',8,'Audit text',1037,1018),(1040,0,'','2011-05-04',6.4,'Audit text 2',1037,1019),(1041,0,'\0','2017-01-01',9,'Audit text 3',1038,1020),(1042,0,'','2019-11-09',3,'Audit text 4',1038,1021);
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surnames` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1hfceldjralkadedlv9lg1tl8` (`user_account`),
  CONSTRAINT `FK_1hfceldjralkadedlv9lg1tl8` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor`
--

LOCK TABLES `auditor` WRITE;
/*!40000 ALTER TABLE `auditor` DISABLE KEYS */;
INSERT INTO `auditor` VALUES (1037,0,'Reina Mercedes st..','alberto@gmail.com','\0','','Alberto','951236498','https://photos.com/photo3.jpg','Garcia Garcia','34343-AAA',948),(1038,0,'Reina Mercedes st..','pedro@gmail.com','\0','','Pedro','555555555','https://photos.com/photo3.jpg','Garcia Garcia','12121-BBB',947);
/*!40000 ALTER TABLE `auditor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surnames` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `score` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pno7oguspp7fxv0y2twgplt0s` (`user_account`),
  CONSTRAINT `FK_pno7oguspp7fxv0y2twgplt0s` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1008,0,'Reina Mercedes st..','enrique@gmail.com','\0','','Enrique','951236498','https://photos.com/photo3.jpg','Garcia Garcia','46573-JFH',943,'Softonic',NULL),(1009,0,'Reina Mercedes st..','aguado@gmail.com','\0','Francisco','Pablo','+34951236498','https://photos.com/photo3.jpg','Aguado Garcia','46583-lFH',944,'Apple Inc.',NULL);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit_card` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_date` date DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_card`
--

LOCK TABLES `credit_card` WRITE;
/*!40000 ALTER TABLE `credit_card` DISABLE KEYS */;
INSERT INTO `credit_card` VALUES (999,0,'VISA',459,'2017-03-06','Albert Pérez','4466296845389421'),(1000,0,'VISA',876,'2020-01-09','Rosa Garcia','4729515936753713'),(1001,0,'VISA',615,'2028-05-05','José Perez','4063461326345618'),(1002,0,'VISA',516,'2027-01-11','Javier Rodriguez Pelaez','4609154971436011'),(1003,0,'VISA',516,'2027-01-11','Provider1','4609154651436034'),(1004,0,'VISA',988,'2020-01-01','administrator1','4215099633128561'),(1005,0,'AMEX',684,'2009-05-05','Manoli rez','344209713413500');
/*!40000 ALTER TABLE `credit_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula`
--

DROP TABLE IF EXISTS `curricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `git_hub_profile` varchar(255) DEFAULT NULL,
  `is_copy` bit(1) DEFAULT NULL,
  `linked_in_profile` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `miscellaneous_data` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6y23mggh2cyj1tgg0jh9v415k` (`miscellaneous_data`),
  CONSTRAINT `FK_6y23mggh2cyj1tgg0jh9v415k` FOREIGN KEY (`miscellaneous_data`) REFERENCES `miscellaneous_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula`
--

LOCK TABLES `curricula` WRITE;
/*!40000 ALTER TABLE `curricula` DISABLE KEYS */;
INSERT INTO `curricula` VALUES (956,0,'Javier Rodriguez Pelaez','http://github.com/jrp','\0','http://linkedin.com/in/jrp','654182738','Statement',957),(960,0,'Alejandro Alfonso','http://github.com/alal','\0','http://linkedin.com/in/alal','654123458','Statement2',961),(964,0,'Javier Rodriguez Pelaez','http://github.com/jrp','','http://linkedin.com/in/jrp','654182738','Statement',965),(968,0,'Javier Rodriguez Pelaez','http://github.com/jrp','','http://linkedin.com/in/jrp','654182738','Statement',969),(972,0,'Javier Rodriguez Pelaez','http://github.com/jrp','','http://linkedin.com/in/jrp','654182738','Statement',973),(976,0,'Javier Rodriguez Pelaez','http://github.com/jrp','','http://linkedin.com/in/jrp','654182738','Statement',977),(980,0,'Javier Rodriguez Pelaez','http://github.com/jrp','','http://linkedin.com/in/jrp','654182738','Statement',981),(984,0,'Javier Rodriguez Pelaez','http://github.com/jrp','','http://linkedin.com/in/jrp','654182738','Statement',985),(988,0,'Manolo Gonzalez Pelaez','http://github.com/mangol','\0','http://linkedin.com/in/mangol','68852738','Statement Manolo',989);
/*!40000 ALTER TABLE `curricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula_education_data`
--

DROP TABLE IF EXISTS `curricula_education_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_education_data` (
  `curricula` int(11) NOT NULL,
  `education_data` int(11) NOT NULL,
  UNIQUE KEY `UK_r552kg3pwybsy7igk77depn9l` (`education_data`),
  KEY `FK_a133bnrmd36opa9yi2dvx0rly` (`curricula`),
  CONSTRAINT `FK_a133bnrmd36opa9yi2dvx0rly` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_r552kg3pwybsy7igk77depn9l` FOREIGN KEY (`education_data`) REFERENCES `education_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula_education_data`
--

LOCK TABLES `curricula_education_data` WRITE;
/*!40000 ALTER TABLE `curricula_education_data` DISABLE KEYS */;
INSERT INTO `curricula_education_data` VALUES (956,958),(960,962),(964,966),(968,970),(972,974),(976,978),(980,982),(984,986),(988,990);
/*!40000 ALTER TABLE `curricula_education_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula_position_data`
--

DROP TABLE IF EXISTS `curricula_position_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_position_data` (
  `curricula` int(11) NOT NULL,
  `position_data` int(11) NOT NULL,
  UNIQUE KEY `UK_drj10dtgcblo3nirh2se0c0su` (`position_data`),
  KEY `FK_mns5pfwnlroqw4udu760ye28v` (`curricula`),
  CONSTRAINT `FK_mns5pfwnlroqw4udu760ye28v` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_drj10dtgcblo3nirh2se0c0su` FOREIGN KEY (`position_data`) REFERENCES `position_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula_position_data`
--

LOCK TABLES `curricula_position_data` WRITE;
/*!40000 ALTER TABLE `curricula_position_data` DISABLE KEYS */;
INSERT INTO `curricula_position_data` VALUES (956,959),(960,963),(964,967),(968,971),(972,975),(976,979),(980,983),(984,987),(988,991);
/*!40000 ALTER TABLE `curricula_position_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education_data`
--

DROP TABLE IF EXISTS `education_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `education_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `mark` double DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `curricula` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b1949439gwkx4pjayup8gn2d6` (`curricula`),
  CONSTRAINT `FK_b1949439gwkx4pjayup8gn2d6` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education_data`
--

LOCK TABLES `education_data` WRITE;
/*!40000 ALTER TABLE `education_data` DISABLE KEYS */;
INSERT INTO `education_data` VALUES (958,0,'Software Engineer','2022-01-06 00:00:00','ETSII',9.7,'2016-09-09 00:00:00',956),(962,0,'Computer Engineer','2017-01-06 00:00:00','ETSII',7.6,'2012-09-09 00:00:00',960),(966,0,'Software Engineer','2022-01-06 00:00:00','ETSII',9.7,'2016-09-09 00:00:00',964),(970,0,'Software Engineer','2022-01-06 00:00:00','ETSII',9.7,'2016-09-09 00:00:00',968),(974,0,'Software Engineer','2022-01-06 00:00:00','ETSII',9.7,'2016-09-09 00:00:00',972),(978,0,'Software Engineer','2022-01-06 00:00:00','ETSII',9.7,'2016-09-09 00:00:00',976),(982,0,'Software Engineer','2022-01-06 00:00:00','ETSII',9.7,'2018-09-09 00:00:00',980),(986,0,'Software Engineer','2022-01-06 00:00:00','ETSII',9.7,'2016-09-09 00:00:00',984),(990,0,'Software Engineer','2022-01-06 00:00:00','ETSII',9.7,'2016-09-09 00:00:00',988);
/*!40000 ALTER TABLE `education_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `deadline` datetime DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  `maximum_deadline` datetime DEFAULT NULL,
  `minimum_salary` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
INSERT INTO `finder` VALUES (953,0,'2019-01-04 00:00:00','','2019-06-04 17:00:00','2020-05-01 00:00:00',1200),(954,0,'2019-01-04 00:00:00','','2019-06-04 17:00:00','2019-05-10 00:00:00',1500),(955,0,'2019-01-04 00:00:00','','2019-06-04 17:00:00','2019-05-10 00:00:00',2000);
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_positions`
--

DROP TABLE IF EXISTS `finder_positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_positions` (
  `finder` int(11) NOT NULL,
  `positions` int(11) NOT NULL,
  KEY `FK_3d46gil0nks2dhgg7cyhv2m39` (`positions`),
  KEY `FK_l0b3qg4nly59oeqhe8ig5yssc` (`finder`),
  CONSTRAINT `FK_l0b3qg4nly59oeqhe8ig5yssc` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_3d46gil0nks2dhgg7cyhv2m39` FOREIGN KEY (`positions`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_positions`
--

LOCK TABLES `finder_positions` WRITE;
/*!40000 ALTER TABLE `finder_positions` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `provider` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_isojc9iaj7goju6s26847atbn` (`provider`),
  CONSTRAINT `FK_isojc9iaj7goju6s26847atbn` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1007,0,'description for the item 1','item1',1006);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_links`
--

DROP TABLE IF EXISTS `item_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_links` (
  `item` int(11) NOT NULL,
  `links` varchar(255) DEFAULT NULL,
  KEY `FK_g63x29gj6aimcehw00xht6eni` (`item`),
  CONSTRAINT `FK_g63x29gj6aimcehw00xht6eni` FOREIGN KEY (`item`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_links`
--

LOCK TABLES `item_links` WRITE;
/*!40000 ALTER TABLE `item_links` DISABLE KEYS */;
INSERT INTO `item_links` VALUES (1007,'http://test.com');
/*!40000 ALTER TABLE `item_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_photos`
--

DROP TABLE IF EXISTS `item_photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_photos` (
  `item` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  KEY `FK_gg8p4th6f0ou8x3iw3mgdb31d` (`item`),
  CONSTRAINT `FK_gg8p4th6f0ou8x3iw3mgdb31d` FOREIGN KEY (`item`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_photos`
--

LOCK TABLES `item_photos` WRITE;
/*!40000 ALTER TABLE `item_photos` DISABLE KEYS */;
INSERT INTO `item_photos` VALUES (1007,'https://photo.com/5id4c4n');
/*!40000 ALTER TABLE `item_photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `send_date` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `recipient` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1049,0,'Texto de prueba 1','2017-01-02 23:59:00','Prueba1',1008,1029),(1050,0,'Texto de prueba 2','2018-01-02 23:59:00','Prueba2',1008,1029),(1051,0,'Texto de prueba 3','2018-01-05 20:59:00','Prueba3',1008,1029),(1052,0,'Texto de prueba 4','2017-04-09 23:59:00','Prueba4',1008,1029),(1053,0,'Texto de prueba 5','2015-01-02 13:59:00','Prueba5',1008,1029),(1054,0,'Texto de prueba 6','2018-01-10 23:59:00','Prueba6',1008,1029),(1055,0,'sex viagra Nigeria','2019-01-01 23:59:00','Spam',1008,1030),(1056,0,'sex viagra Nigeria','2019-01-01 23:59:00','Spam',1008,1031);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_tags`
--

DROP TABLE IF EXISTS `message_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_tags` (
  `message` int(11) NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  KEY `FK_suckduhsrrtot7go5ejeev57w` (`message`),
  CONSTRAINT `FK_suckduhsrrtot7go5ejeev57w` FOREIGN KEY (`message`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_tags`
--

LOCK TABLES `message_tags` WRITE;
/*!40000 ALTER TABLE `message_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_data`
--

DROP TABLE IF EXISTS `miscellaneous_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  `curricula` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_h48spqfrohqicpoi2y2kmdvhb` (`curricula`),
  CONSTRAINT `FK_h48spqfrohqicpoi2y2kmdvhb` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_data`
--

LOCK TABLES `miscellaneous_data` WRITE;
/*!40000 ALTER TABLE `miscellaneous_data` DISABLE KEYS */;
INSERT INTO `miscellaneous_data` VALUES (957,1,'http://miscellaneusdata1.com',956),(961,1,'http://miscellaneusdata2.com',960),(965,1,'http://miscellaneusdata1.com',964),(969,1,'http://miscellaneusdata4.com',968),(973,1,'http://miscellaneusdata5.com',972),(977,1,'http://miscellaneusdata5.com',976),(981,1,'http://miscellaneusdata7.com',980),(985,1,'http://miscellaneusdata8.com',984),(989,1,'http://miscellaneusdata9.com',988);
/*!40000 ALTER TABLE `miscellaneous_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `deadline` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_cancelled` bit(1) DEFAULT NULL,
  `is_final_mode` bit(1) DEFAULT NULL,
  `profile_required` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `skills_required` varchar(255) DEFAULT NULL,
  `technologies_required` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `auditor` int(11) DEFAULT NULL,
  `company` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dvslehbjl7styqrm2tfvqg4d2` (`auditor`),
  KEY `FK_7qlfn4nye234rrm4w83nms1g8` (`company`),
  CONSTRAINT `FK_7qlfn4nye234rrm4w83nms1g8` FOREIGN KEY (`company`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_dvslehbjl7styqrm2tfvqg4d2` FOREIGN KEY (`auditor`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (1018,1,'2021-01-03','Esto es un puesto de prueba 1','\0','','Perfil requerido de prueba 1',1200,'Habilidades requeridas de prueba 1','Tecnologías requeridas de prueba 1','Soft-1111','Puesto de prueba 1',1037,1008),(1019,1,'2021-11-03','Esto es un puesto de prueba 2','\0','','Perfil requerido de prueba 2',2200,'Habilidades requeridas de prueba 2','Tecnologías requeridas de prueba 2','Appl-2222','Puesto de prueba 2',1037,1009),(1020,1,'2022-09-03','Esto es un puesto de prueba 3','\0','\0','Perfil requerido de prueba 3',3200,'Habilidades requeridas de prueba 3','Tecnologías requeridas de prueba 3','Appl-3333','Puesto de prueba 3',1038,1009),(1021,1,'2011-01-03','Esto es un puesto de prueba 4','\0','','Perfil requerido de prueba 4',200,'Habilidades requeridas de prueba 4','Tecnologías requeridas de prueba 4','Soft-3412','Puesto de prueba 4',1038,1008),(1022,0,'2021-01-04','Esto es un puesto de prueba 5','\0','','Perfil requerido de prueba 5',1900,'Habilidades requeridas de prueba 5','Tecnologías requeridas de prueba 5','Soft-2547','Puesto de prueba 5',NULL,1008),(1023,0,'2021-01-04','Esto es un puesto de prueba 6','\0','\0','Perfil requerido de prueba 6',1900,'Habilidades requeridas de prueba 6','Tecnologías requeridas de prueba 6','Soft-3547','Puesto de prueba 6',NULL,1008),(1024,0,'2021-01-04','Esto es un puesto de prueba 7','','\0','Perfil requerido de prueba 7',1900,'Habilidades requeridas de prueba 7','Tecnologías requeridas de prueba 7','Soft-7047','Puesto de prueba 7',NULL,1008),(1025,0,'2021-01-04','Esto es un puesto de prueba 8','\0','','Perfil requerido de prueba 8',1900,'Habilidades requeridas de prueba 8','Tecnologías requeridas de prueba 8','Soft-8047','Puesto de prueba 8',NULL,1008),(1026,0,'2021-01-03','Esto es un puesto de prueba 9','\0','','Perfil requerido de prueba 9',1200,'Habilidades requeridas de prueba 9','Tecnologías requeridas de prueba 9','Soft-9999','Puesto de prueba 9',NULL,1008),(1027,0,'2021-01-04','Esto es un puesto de prueba 10','\0','','Perfil requerido de prueba 10',1900,'Habilidades requeridas de prueba 10','Tecnologías requeridas de prueba 10','Soft-10000','Puesto de prueba 8',NULL,1008),(1028,0,'2021-01-04','Esto es un puesto de prueba 11','\0','','Perfil requerido de prueba 11',1900,'Habilidades requeridas de prueba 11','Tecnologías requeridas de prueba 11','Soft-1147','Puesto de prueba 11',NULL,1008);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_data`
--

DROP TABLE IF EXISTS `position_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `curricula` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_50pf203n1ob4iihr5n18eeoxf` (`curricula`),
  CONSTRAINT `FK_50pf203n1ob4iihr5n18eeoxf` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_data`
--

LOCK TABLES `position_data` WRITE;
/*!40000 ALTER TABLE `position_data` DISABLE KEYS */;
INSERT INTO `position_data` VALUES (959,0,'position1','2021-01-03 00:00:00','2020-09-11 00:00:00','position1',956),(963,0,'position2','2021-01-12 00:00:00','2020-02-07 00:00:00','position2',960),(967,0,'position1','2021-01-03 00:00:00','2020-09-11 00:00:00','position1',964),(971,0,'position1','2021-01-03 00:00:00','2020-09-11 00:00:00','position4',968),(975,0,'position1','2021-01-03 00:00:00','2020-09-11 00:00:00','position5',972),(979,0,'position1','2021-01-03 00:00:00','2020-09-11 00:00:00','position6',976),(983,0,'position1','2021-01-03 00:00:00','2020-09-11 00:00:00','position7',980),(987,0,'position1','2021-01-03 00:00:00','2020-09-11 00:00:00','position8',984),(991,0,'position9','2021-01-03 00:00:00','2020-09-11 00:00:00','position9',988);
/*!40000 ALTER TABLE `position_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_problems`
--

DROP TABLE IF EXISTS `position_problems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_problems` (
  `position` int(11) NOT NULL,
  `problems` int(11) NOT NULL,
  KEY `FK_7pe330ganri24wsftsajlm4l9` (`problems`),
  KEY `FK_iji6l3ytrjgytbgo6oi061elj` (`position`),
  CONSTRAINT `FK_iji6l3ytrjgytbgo6oi061elj` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_7pe330ganri24wsftsajlm4l9` FOREIGN KEY (`problems`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_problems`
--

LOCK TABLES `position_problems` WRITE;
/*!40000 ALTER TABLE `position_problems` DISABLE KEYS */;
INSERT INTO `position_problems` VALUES (1018,1010),(1018,1011),(1019,1012),(1019,1013),(1020,1012),(1020,1013),(1021,1010),(1021,1011),(1022,1010),(1022,1011),(1023,1010),(1023,1011),(1024,1010),(1024,1011),(1025,1010),(1025,1011),(1026,1010),(1026,1011),(1027,1010),(1027,1011),(1028,1010),(1028,1011);
/*!40000 ALTER TABLE `position_problems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `hint` varchar(255) DEFAULT NULL,
  `is_final_mode` bit(1) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1dla8eoii1nn6emoo4yv86pgb` (`company`),
  CONSTRAINT `FK_1dla8eoii1nn6emoo4yv86pgb` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` VALUES (1010,0,'Pista de prueba 1','','Mensaje del problema de prueba 1','Problema de prueba 1',1008),(1011,0,'Pista de prueba 2','','Mensaje del problema de prueba 2','Problema de prueba 2',1008),(1012,0,'Pista de prueba 3','','Mensaje del problema de prueba 3','Problema de prueba 3',1009),(1013,0,'Pista de prueba 4','','Mensaje del problema de prueba 4','Problema de prueba 4',1009),(1014,0,'Pista de prueba 5','\0','Mensaje del problema de prueba 5','Problema de prueba 5',1008),(1015,0,'Pista de prueba 6','','Mensaje del problema de prueba 6','Problema de prueba 6',1008),(1016,0,'Pista de prueba 7','\0','Mensaje del problema de prueba 7','Problema de prueba 7',1008),(1017,0,'Pista de prueba 8','\0','Mensaje del problema de prueba 8','Problema de prueba 8',1008);
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_attachments`
--

DROP TABLE IF EXISTS `problem_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_attachments` (
  `problem` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  KEY `FK_mhrgqo77annlexxubmtv4qvf7` (`problem`),
  CONSTRAINT `FK_mhrgqo77annlexxubmtv4qvf7` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_attachments`
--

LOCK TABLES `problem_attachments` WRITE;
/*!40000 ALTER TABLE `problem_attachments` DISABLE KEYS */;
INSERT INTO `problem_attachments` VALUES (1015,'https://attachments.com/3r8ypwf'),(1015,'https://attachments.com/23f23gf');
/*!40000 ALTER TABLE `problem_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider`
--

DROP TABLE IF EXISTS `provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surnames` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `make` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pj40m1p8m3lcs2fkdl1n3f3lq` (`user_account`),
  CONSTRAINT `FK_pj40m1p8m3lcs2fkdl1n3f3lq` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider`
--

LOCK TABLES `provider` WRITE;
/*!40000 ALTER TABLE `provider` DISABLE KEYS */;
INSERT INTO `provider` VALUES (1006,0,'Reina Mercedes st..','pedro@gmail.com','\0','','Pedro','951236463','https://photos.com/photo5.jpg','Garcia Garcia','46573-JFH',946,'Cocacola');
/*!40000 ALTER TABLE `provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rookie`
--

DROP TABLE IF EXISTS `rookie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rookie` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surnames` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `finder` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2n8nv9qsl5pnxhnosngfkkm4i` (`user_account`),
  KEY `FK_n7rveqfq9lm0cwcxhwyvtyi1g` (`finder`),
  CONSTRAINT `FK_2n8nv9qsl5pnxhnosngfkkm4i` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_n7rveqfq9lm0cwcxhwyvtyi1g` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rookie`
--

LOCK TABLES `rookie` WRITE;
/*!40000 ALTER TABLE `rookie` DISABLE KEYS */;
INSERT INTO `rookie` VALUES (1029,0,'Av Reina Mercedes 12','javierrodriguezpelaez@mail.com','\0','','Javier','654182738','https://myfoto.com/javierrp','Rodriguez Pelaez','16324234772341',945,955),(1030,0,'Av Reina Mercedes 10','manolito@mail.com','\0','','Manolo','688185738','https://myfoto.com/manologoz','Gonzalez Pelaez','ES-34772341',949,954),(1031,0,'Av Reina Mercedes 12','samuelitoelloco@mail.com','\0','','Samuel','688185438','https://myfoto.com/samueloz','Gonzalez Pelaez','ES-34774341',950,955),(1033,0,'','removed@removed.com','','','removed','000000000','https://removed.removed/removed','removed','00removed00',1032,NULL);
/*!40000 ALTER TABLE `rookie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rookie_curriculas`
--

DROP TABLE IF EXISTS `rookie_curriculas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rookie_curriculas` (
  `rookie` int(11) NOT NULL,
  `curriculas` int(11) NOT NULL,
  UNIQUE KEY `UK_sm59fk1li1x8oyscgqthbk1n3` (`curriculas`),
  KEY `FK_jxblfcd24o9f290m4yqgcduxw` (`rookie`),
  CONSTRAINT `FK_jxblfcd24o9f290m4yqgcduxw` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`),
  CONSTRAINT `FK_sm59fk1li1x8oyscgqthbk1n3` FOREIGN KEY (`curriculas`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rookie_curriculas`
--

LOCK TABLES `rookie_curriculas` WRITE;
/*!40000 ALTER TABLE `rookie_curriculas` DISABLE KEYS */;
INSERT INTO `rookie_curriculas` VALUES (1029,956),(1029,960),(1029,964),(1030,988);
/*!40000 ALTER TABLE `rookie_curriculas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_profile`
--

DROP TABLE IF EXISTS `social_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_profile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `social_network` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_profile`
--

LOCK TABLES `social_profile` WRITE;
/*!40000 ALTER TABLE `social_profile` DISABLE KEYS */;
INSERT INTO `social_profile` VALUES (992,0,'https://twitter.com/2938f2','albertito1234','Twitter'),(993,0,'https://twitter.com/@macarena','Macareba','Twitter'),(994,0,'https://twitter.com/@lased','Lased','Twitter'),(995,0,'https://twitter.com/@jesusito','jesusito','Twitter'),(996,0,'https://twitter.com/@Elias','Elias','Twitter'),(997,0,'https://twitter.com/@Pepito','pepito','Twitter'),(998,0,'https://acme.com/Admin1','Administrator1','Acme');
/*!40000 ALTER TABLE `social_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `charge` double NOT NULL,
  `target_page` varchar(255) DEFAULT NULL,
  `credit_card` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  `provider` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b1c71pwhg9slb986j8kl7uul1` (`credit_card`),
  KEY `FK_jnrjojfnyyaie1n4jhsdxjbig` (`position`),
  KEY `FK_dwk5ymekhnr143u957f7gtns6` (`provider`),
  CONSTRAINT `FK_dwk5ymekhnr143u957f7gtns6` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`),
  CONSTRAINT `FK_b1c71pwhg9slb986j8kl7uul1` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`),
  CONSTRAINT `FK_jnrjojfnyyaie1n4jhsdxjbig` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
INSERT INTO `sponsorship` VALUES (1034,0,'https://recursoshumanos.us.es/images/marca-dos-tintas_300.gif',10.5,'http://www.us.es/',1003,1018,1006),(1035,0,'http://uvsfajardo.sld.cu/sites/uvsfajardo.sld.cu/files/resize/rootcandy_fixed_logo-273x51.gif',5.5,'https://github.com/',1003,1019,1006),(1036,0,'http://uvsfajardo.sld.cu/sites/uvsfajardo.sld.cu/files/resize/usuarios/noevias/guantanamo-250x85.png',0,'https://www.infosol.com.mx/',1003,1018,1006);
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_status`
--

DROP TABLE IF EXISTS `system_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_status` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vat` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `fare` double NOT NULL,
  `finder_results_number` int(11) NOT NULL,
  `finder_time` int(11) NOT NULL,
  `rebranding` bit(1) DEFAULT NULL,
  `security_breach` bit(1) DEFAULT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  `welcome_messageen` varchar(255) DEFAULT NULL,
  `welcome_messagees` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_status`
--

LOCK TABLES `system_status` WRITE;
/*!40000 ALTER TABLE `system_status` DISABLE KEYS */;
INSERT INTO `system_status` VALUES (951,0,21,'https://bsbproduction.s3.amazonaws.com/portals/10812/images/rookies.jpg','+34',10,10,1,'','\0','Acme Rookies','Welcome to Acme rookie  Rank! We’re IT rookie’s favourite job marketplace!','¡Bienvenidos a Acme Rookies!  ¡Somos el mercado de trabajo favorito de los profesionales de las TICs!');
/*!40000 ALTER TABLE `system_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_status_credit_card_makes`
--

DROP TABLE IF EXISTS `system_status_credit_card_makes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_status_credit_card_makes` (
  `system_status` int(11) NOT NULL,
  `credit_card_makes` varchar(255) DEFAULT NULL,
  KEY `FK_adm65ijenuvrio4dsui44l580` (`system_status`),
  CONSTRAINT `FK_adm65ijenuvrio4dsui44l580` FOREIGN KEY (`system_status`) REFERENCES `system_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_status_credit_card_makes`
--

LOCK TABLES `system_status_credit_card_makes` WRITE;
/*!40000 ALTER TABLE `system_status_credit_card_makes` DISABLE KEYS */;
INSERT INTO `system_status_credit_card_makes` VALUES (951,'VISA'),(951,'MASTER'),(951,'DINNERS'),(951,'AMEX');
/*!40000 ALTER TABLE `system_status_credit_card_makes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_status_spam_wordsen`
--

DROP TABLE IF EXISTS `system_status_spam_wordsen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_status_spam_wordsen` (
  `system_status` int(11) NOT NULL,
  `spam_wordsen` varchar(255) DEFAULT NULL,
  KEY `FK_q2m2joslxos6tlnveo5q2phdw` (`system_status`),
  CONSTRAINT `FK_q2m2joslxos6tlnveo5q2phdw` FOREIGN KEY (`system_status`) REFERENCES `system_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_status_spam_wordsen`
--

LOCK TABLES `system_status_spam_wordsen` WRITE;
/*!40000 ALTER TABLE `system_status_spam_wordsen` DISABLE KEYS */;
INSERT INTO `system_status_spam_wordsen` VALUES (951,'sex'),(951,'viagra'),(951,'cialis'),(951,'one million'),(951,'you\'ve been selected'),(951,'Nigeria');
/*!40000 ALTER TABLE `system_status_spam_wordsen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_status_spam_wordses`
--

DROP TABLE IF EXISTS `system_status_spam_wordses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_status_spam_wordses` (
  `system_status` int(11) NOT NULL,
  `spam_wordses` varchar(255) DEFAULT NULL,
  KEY `FK_9t8mc9rcg1bnq5jtu62ac3cdb` (`system_status`),
  CONSTRAINT `FK_9t8mc9rcg1bnq5jtu62ac3cdb` FOREIGN KEY (`system_status`) REFERENCES `system_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_status_spam_wordses`
--

LOCK TABLES `system_status_spam_wordses` WRITE;
/*!40000 ALTER TABLE `system_status_spam_wordses` DISABLE KEYS */;
INSERT INTO `system_status_spam_wordses` VALUES (951,'sexo'),(951,'viagra'),(951,'un millón'),(951,'has sido seleccionado'),(951,'Nigeria');
/*!40000 ALTER TABLE `system_status_spam_wordses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (942,0,'21232f297a57a5a743894a0e4a801fc3','admin'),(943,0,'df655f976f7c9d3263815bd981225cd9','company1'),(944,0,'d196a28097115067fefd73d25b0c0be8','company2'),(945,0,'9701eb1802a4c63f51e1501512fbdd30','rookie1'),(946,0,'cdb82d56473901641525fbbd1d5dab56','provider1'),(947,0,'04dd94ba3212ac52ad3a1f4cbfb52d4f','auditor2'),(948,0,'175d2e7a63f386554a4dd66e865c3e14','auditor1'),(949,0,'124be4fa2a59341a1d7e965ac49b2923','rookie2'),(950,0,'b723fa2fd1c2dc65d166df3e7f83e329','rookie3'),(1032,0,'8b3bce575105dde616e9afbf51623078','removedUser');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (942,'ADMIN'),(943,'COMPANY'),(944,'COMPANY'),(945,'ROOKIE'),(946,'PROVIDER'),(947,'AUDITOR'),(948,'AUDITOR'),(949,'ROOKIE'),(950,'ROOKIE'),(1032,'BANNED');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-09 18:03:09
