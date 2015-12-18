create user  'belous'@'localhost' IDENTIFIED BY 'belous';
GRANT ALL PRIVILEGES ON * . * TO 'belous'@'localhost';
FLUSH PRIVILEGES;

CREATE DATABASE `contacts`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';
    
USE `contacts`;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MOcontactDE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment` (
  `attachment_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_upload` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `attachment_comment` varchar(30) DEFAULT NULL,
  `file_name` varchar(80) NOT NULL,
  `contact_id` int(10) unsigned NOT NULL,
  `isDeleted` bit(1) DEFAULT b'0',
  `file_path` varchar(200) NOT NULL,
  PRIMARY KEY (`attachment_id`),
  KEY `attachment_contact_id_index` (`contact_id`),
  CONSTRAINT `attachment_ibfk_1` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`contact_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `contact_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `middle_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `birthday` date NOT NULL,
  `gender` enum('MALE','FEMALE') COLLATE utf8_unicode_ci NOT NULL,
  `nationality` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `relationship_status` enum('SINGLE','MARRIED') COLLATE utf8_unicode_ci DEFAULT NULL,
  `web_site` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `current_job` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `photo_path` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'photo.jpg',
  `street` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `house` varchar(7) COLLATE utf8_unicode_ci NOT NULL,
  `flat` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `location_id` int(10) unsigned NOT NULL,
  `isDeleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`contact_id`),
  KEY `contact_location_id_index` (`location_id`),
  KEY `contact_first_name_last_name_index` (`first_name`,`last_name`),
  KEY `contact_gender_index` (`gender`),
  KEY `contact_relationship_status_index` (`relationship_status`),
  CONSTRAINT `contact_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `location_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `zip_code` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `country` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`location_id`),
  UNIQUE KEY `unique_id` (`location_id`),
  UNIQUE KEY `location_zip_code_uindex` (`zip_code`),
  KEY `unique_zip_code` (`zip_code`),
  KEY `location_city_index` (`city`),
  KEY `location_country_index` (`country`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'220030','Minsk','Belarus'),(2,'224000','Brest','Belarus'),(3,'210000','Vitebsk','Belarus'),(4,'246000','Gomel','Belarus'),(5,'212000','Mogilev','Belarus'), (6,'230000','Grodno','Belarus');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone` (
  `phone_id` int(11) NOT NULL AUTO_INCREMENT,
  `country_code` smallint(5) unsigned NOT NULL DEFAULT '0',
  `operator_code` smallint(5) unsigned NOT NULL DEFAULT '0',
  `phone_number` int(10) unsigned NOT NULL DEFAULT '0',
  `phone_type` enum('MOBILE','HOME') NOT NULL,
  `contact_id` int(10) unsigned NOT NULL,
  `phone_comment` varchar(30) NOT NULL,
  `isDeleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`phone_id`),
  KEY `phone_contact_id_index` (`contact_id`),
  CONSTRAINT `phone_ibfk_1` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;