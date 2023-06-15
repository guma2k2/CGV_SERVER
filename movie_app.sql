-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: movie_app
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_time` datetime(6) DEFAULT NULL,
  `seats` varchar(255) DEFAULT NULL,
  `total_amount` bigint DEFAULT NULL,
  `event_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiy2tdi4vrw2mljj6rqwmd698q` (`event_id`),
  KEY `FKlhxtmc0dd85lvrdfgtitxua4y` (`user_id`),
  CONSTRAINT `FKiy2tdi4vrw2mljj6rqwmd698q` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `FKlhxtmc0dd85lvrdfgtitxua4y` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (7,'2023-04-10 12:58:42.018727','A1, A2',150000,4,1),(26,'2023-04-13 23:15:03.571766','B3',120000,4,4),(31,'2023-04-14 13:20:13.975697','A3, A5',240000,4,4),(32,'2023-04-14 22:24:36.229480','C5',913000,4,4),(41,'2023-04-19 14:20:51.683515','D2, D1',240000,6,4),(47,'2023-05-17 22:38:25.644749','B1',239000,4,15),(54,'2023-06-13 16:25:41.899127','C1',120000,4,9),(66,'2023-06-13 19:55:18.760381','B5',120000,4,15),(68,'2023-06-13 20:00:03.876003','C2',120000,4,9),(69,'2023-06-14 20:00:49.416217','G4, G3, G2, G1',480000,6,9),(70,'2023-06-14 20:01:27.496239','A4, A3, A2, A1',480000,20,9),(71,'2023-06-14 20:04:14.832243','A3',120000,57,9);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_combo`
--

DROP TABLE IF EXISTS `booking_combo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_combo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `booking_id` bigint DEFAULT NULL,
  `combo_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbwek06jap4aqyxw7tj1nv9r3a` (`booking_id`),
  KEY `FKle9c5oa3ethga0frtk2yimo86` (`combo_id`),
  CONSTRAINT `FKbwek06jap4aqyxw7tj1nv9r3a` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`),
  CONSTRAINT `FKle9c5oa3ethga0frtk2yimo86` FOREIGN KEY (`combo_id`) REFERENCES `combo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_combo`
--

LOCK TABLES `booking_combo` WRITE;
/*!40000 ALTER TABLE `booking_combo` DISABLE KEYS */;
INSERT INTO `booking_combo` VALUES (5,1,32,2),(6,1,32,4),(7,1,32,6),(11,1,47,2);
/*!40000 ALTER TABLE `booking_combo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_seat`
--

DROP TABLE IF EXISTS `booking_seat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_seat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_id` bigint DEFAULT NULL,
  `event_id` bigint DEFAULT NULL,
  `seat_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3gcy7w2me25kc4qp8nobmg4q6` (`booking_id`),
  KEY `FKtaw673bh7dupc635hllxjrju4` (`event_id`),
  KEY `FK3y806wtfhomwvu02t1u7u2136` (`seat_id`),
  CONSTRAINT `FK3gcy7w2me25kc4qp8nobmg4q6` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`),
  CONSTRAINT `FK3y806wtfhomwvu02t1u7u2136` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`),
  CONSTRAINT `FKtaw673bh7dupc635hllxjrju4` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_seat`
--

LOCK TABLES `booking_seat` WRITE;
/*!40000 ALTER TABLE `booking_seat` DISABLE KEYS */;
INSERT INTO `booking_seat` VALUES (5,7,4,1),(6,7,4,2),(36,26,4,8),(43,31,4,3),(44,31,4,5),(45,32,4,13),(54,41,6,42),(55,41,6,43),(66,47,4,6),(75,54,4,10),(87,66,4,9),(89,68,4,11),(90,69,6,67),(91,69,6,68),(92,69,6,69),(93,69,6,70),(94,70,20,125),(95,70,20,126),(96,70,20,127),(97,70,20,128),(98,71,57,20);
/*!40000 ALTER TABLE `booking_seat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cinema`
--

DROP TABLE IF EXISTS `cinema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinema` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(1000) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `city_id` int DEFAULT NULL,
  `cinema_type` varchar(30) DEFAULT NULL,
  `main_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2lxd9v0mo9e6r5aq9qpadm19s` (`city_id`),
  CONSTRAINT `FK2lxd9v0mo9e6r5aq9qpadm19s` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cinema`
--

LOCK TABLES `cinema` WRITE;
/*!40000 ALTER TABLE `cinema` DISABLE KEYS */;
INSERT INTO `cinema` VALUES (1,'Tầng 5, Trung tâm thương mại Vincom Bà Triệu, Số 191 Bà Triệu, Hai Bà Trưng, Hà Nội','CGV Vincom Bà Triệu','02436333333',1,'Rạp 2D','cinema1anh1.jpeg'),(2,'Tầng 5, Vincom Mega Mall Times City, Số 458 Minh Khai, Hai Bà Trưng, Hà Nội','CGV Times City','02473086666',1,'Rạp 2D','time-cgv-1_4.png'),(3,'Tầng B2-Royal City, Số 72A Nguyễn Trãi, Thanh Xuân, Hà Nội','CGV Royal City','02466666666',1,'Rạp 2D','_lhh2535.jpg'),(4,'Tầng 3, Trung tâm thương mại AEON Long Biên, Số 27 Co Linh, Long Biên, Hà Nội','CGV Aeon Long Biên','02473005566',1,'Rạp 2D','cgv-vincom-long-bien.jpg'),(5,'Tầng 4, Mipec Tower, Số 229 Tây Sơn, Đống Đa, Hà Nội','CGV Mipec Tower','02471055000',1,'Rạp 2D','fasade_layer-1.jpg'),(6,'Tầng 5, Crescent Mall, Số 101 Tôn Dật Tiên, Tân Phú, Hồ Chí Minh','CGV Crescent Mall','02854134999',2,'Rạp 2D','cgv-vincom-long-bien.jpg'),(7,'Tầng 5, Pearl Plaza, Số 561A Điện Biên Phủ, Bình Thạnh, Hồ Chí Minh','CGV Pearl Plaza','02873085588',2,'Rạp 2D','cgv-vincom-long-bien.jpg'),(8,'Tầng 7, Parkson Đồng Khởi, Số 35Bis - 45 Lê Thánh Tôn, Quận 1, Hồ Chí Minh','CGV Parkson Đồng Khởi','02838242828',2,'Rạp 2D','cgv-vincom-long-bien.jpg'),(9,'Tầng 5, Vincom Center B, Số 72 Lê Thánh Tôn, Quận 1, Hồ Chí Minh','CGV Vincom Center Landmark 81','02862914000',2,'Rạp 2D','ticket_box_layer-1.jpg'),(10,'Tầng 7, SC VivoCity, Số 1058 Nguyễn Văn Linh, Tân Phong, Quận 7, Hồ Chí Minh','CGV SC VivoCity','02854124300',2,'Rạp 2D','cgv-vincom-long-bien.jpg'),(11,'L5, Vincom Center Đồng Khởi, 72 Lê Thánh Tôn, Bến Nghé, Quận 1, Hồ Chí Minh','CGV Vincom Đồng Khởi','(028) 3939 0909',2,'Rạp 2D','https://www.cgv.vn/media/imax/goldclass-2.png'),(12,'Lầu 3, TTTM Aeon Mall Bình Dương Canary, KCN VSIP II, P. Bình Hòa, Thị xã Thuận An, Bình Dương','CGV Aeon Bình Dương Canary','(0274) 221 0777',3,'Rạp 2D','https://www.cgv.vn/media/imax/goldclass-2.png'),(13,'L5, Parkson Mall Đà Nẵng, 30 Hùng Vương, Q. Hải Châu, Đà Nẵng','CGV Parkson Đà Nẵng','(0236) 363 3555',4,'Rạp 2D','https://www.cgv.vn/media/imax/goldclass-2.png'),(14,'Tầng 5, Parkson TD Plaza Hải Phòng, số 15 Thái Phiên, P. Lê Chân, Hải Phòng','CGV Parkson Hải Phòng','(0225) 382 2566',5,'Rạp 2D','https://www.cgv.vn/media/imax/goldclass-2.png'),(15,'Tầng 2, TTTM Big C Biên Hòa, 1217 Phạm Văn Thuận, P. Tam Hiệp, TP. Biên Hòa, Đồng Nai','CGV Big C Biên Hòa','(0251) 382 8777',6,'Rạp 2D','https://www.cgv.vn/media/imax/goldclass-2.png'),(16,'Tầng 3, TTTM Big C Nha Trang, 2 Trần Phú, P. Lộc Thọ, TP. Nha Trang, Khánh Hòa','CGV Big C Nha Trang','(0258) 383 3999',7,'Rạp 2D','https://www.cgv.vn/media/site/cache/1/980x415/b58515f018eb873dafa430b6f9ae0c1e/b/t/bth_3314.jpg'),(27,'G18-20, Tầng 3, Siêu thị Co.opmart, Số 239-245 Phạm Văn Thuận, P. Tân Tiến, TP. Biên Hòa, Đồng Nai','CGV Coopmart Bien Hoa','0274 655 4888',6,'Rạp PREMIUM','https://www.cgv.vn/media/imax/goldclass-2.png'),(28,'910A Ngo Quyen, An Hai Bac Ward, Son Tra District, Da Nang','CGV Vincom Da Nang','19006067',4,'Rạp PREMIUM','https://www.cgv.vn/media/imax/goldclass-2.png'),(29,'06 Nai Nam, Hoa Cuong Nam Ward, Hai Chau District, Da Nang','CGV Da Nang Lotte Mart','19006067',4,'Rạp 2D','https://www.cgv.vn/media/imax/goldclass-2.png'),(30,'Tầng 5, TTTM Vincom Thủ Đức, 216 Võ Văn Ngân, Phường Bình Thọ, Quận Thủ Đức','CGV Vincom Thủ Đức','0462755240',2,'Rạp 2D','fasade_layer-1.jpg'),(31,'255-257 đường Hùng Vương Quận Thanh Khê Tp. Đà Nẵng','CGV Vĩnh Trung Plaza','0462755240',3,'Rạp 2D','cinema2anh1.jpeg'),(32,'Tầng 4 , TTTM Vincom Thái Nguyên 286 Lương Ngọc Quyến, Phường Quang Trung, Thành phố Thái Nguyên','CGV Vincom Thái Nguyên','+84 4 6 275 5240',12,'Rạp 2D','cgv-vincom-thai-nguyen1.png'),(33,'Tầng 4, TTTM Vincom Center Hạ Long, Khu Cột Đồng Hồ, P.Bạch Đằng, Hạ Long, Quảng Ninh','CGV Vincom Hạ Long','+84 4 6 275 5240',17,'Rạp 2D','10877450_10152609257423546_268436616_n.jpg'),(34,'Tầng 4, Lam Sơn Square 9 Lê Lợi Tp. Vũng Tàu','CGV Lam Sơn Square','+84 4 6 275 5240',16,'Rạp PREMIUM','alobby_ceiling_view-low-_hinh_dai_diena_1.jpg'),(35,'Tầng 4, TTTM Vincom Plaza Trà Vinh, số 24 Đường Nguyễn Thị Minh Khai, ','CGV Vincom Trà Vinh','+84 4 6 275 5240',10,'Rạp 2D','tra-vinh-1_1.png'),(36,'Tầng 5 – TTTM VINCOM CAO LÃNH, 02 Đường 30.04, Phường 01, TP. Cao Lãnh, Đồng Tháp','CGV Vincom Cao Lãnh','+84 4 6 275 5240',40,'Rạp 2D','tra-vinh-1_1.png'),(37,'Tầng 3, Trung tâm Thương mại Vincom Plaza Hà Tĩnh,','CGV Vincom Hà Tĩnh','+84 4 6 275 5240',19,'Rạp 2D','ht1fix.jpg'),(38,'Tầng 3 & 4, Vincom Plaza Kon Tum, 02 đường Phan Đình Phùng, P. Quyết Thắng, TP. Kon Tum, Tỉnh Kon Tum','CGV Vincom Kon Tum','+84 4 6 275 5240',8,'Rạp 2D','cgv-vincom-lang-son-1_1.png');
/*!40000 ALTER TABLE `cinema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cinema_images`
--

DROP TABLE IF EXISTS `cinema_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinema_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `cinema_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKme2kgva34eerb0tv0bruv7lmo` (`cinema_id`),
  CONSTRAINT `FKme2kgva34eerb0tv0bruv7lmo` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cinema_images`
--

LOCK TABLES `cinema_images` WRITE;
/*!40000 ALTER TABLE `cinema_images` DISABLE KEYS */;
INSERT INTO `cinema_images` VALUES (15,'cinema1anh2.jpeg',1),(17,'cinema2anh3.jpeg',31),(18,'cinema2anh2.jpeg',31),(19,'cgv-vincom-thai-nguyen-3.png',32),(20,'cgv-vincom-thai-nguyen-2.png',32),(21,'10872476_10152609273538546_166226703_n.jpg',33),(22,'10841730_10152609256298546_1564428434_n.jpg',33),(23,'lounge-low-a_1.jpg',34),(24,'lobby-low-a_1.jpg',34),(25,'tra-vinh-3_1.png',35),(26,'tra-vinh-2_1.png',35),(27,'tra-vinh-2_1.png',36),(28,'tra-vinh-3_1.png',36),(29,'ht2fix.jpg',37),(30,'cgv-vincom-lang-son-3_1.png',38),(31,'cgv-vincom-lang-son-2_1.png',38),(32,'cgv_tra_vinh_v2_170304_15.jpg',1),(33,'cgv_tra_vinh_v3_170304_15.jpg',1),(34,'time-cgv-2_4.png',2),(35,'_lhh2439.jpg',3),(36,'ticket_box_layer-1.jpg',5),(37,'popcorn_pactory_layer-1.jpg',5),(38,'popcorn_pactory_layer-1.jpg',9),(39,'ticket_box_layer-1.jpg',10),(40,'ticket_box_layer-1.jpg',30);
/*!40000 ALTER TABLE `cinema_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Hà Nội','100000'),(2,'Hồ Chí Minh','700000'),(3,'Đà Nẵng','550000'),(4,'Hải Phòng','180000'),(5,'Cần Thơ','900000'),(6,'Biên Hòa','810000'),(7,'Nha Trang','650000'),(8,'Kon Tum','630000'),(10,'Trà Vinh','460000'),(12,'Thái Nguyên','250000'),(16,'Bà Rịa-Vũng Tàu','790000'),(17,'Quảng Ninh','200000'),(19,'Hà Tĩnh','430000'),(40,'Đồng Tháp','510000');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combo`
--

DROP TABLE IF EXISTS `combo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combo`
--

LOCK TABLES `combo` WRITE;
/*!40000 ALTER TABLE `combo` DISABLE KEYS */;
INSERT INTO `combo` VALUES (1,'1 bình Jungle Brown + 1 nước siêu lớn',259000,'JUNGLE BROWN MY COI','https://www.cgv.vn/media/concession/web/6426a4ac90777_1680254125.png'),(2,'1 Bắp Lớn + 2 Nước Siêu Lớn + 1 Snack. Nhận trong ngày xem phim.',119000,'CGV SNACK COMBO','https://www.cgv.vn/media/concession/web/63aaa389a9927_1672127370.png'),(3,'1 Bắp Lớn + 1 Nước Siêu Lớn + 1 Snack. Nhận trong ngày xem phim.',95000,'MY SNACK COMBO','https://www.cgv.vn/media/concession/web/63aaa361ceea4_1672127330.png'),(4,'1 Bắp Lớn + 2 Nước Siêu Lớn. Nhận trong ngày xem phim.',115000,'CGV COMBO','https://www.cgv.vn/media/concession/web/63aaa31525d4c_1672127253.png'),(5,'1 bắp lớn + 1 nước siêu lớn. Nhận trong ngày xem phim*',89000,'MY COMBO','https://www.cgv.vn/media/concession/web/63aaa2d81b6bf_1672127192.png'),(6,'2 bình Jungle Brown + 2 nước siêu lớn + 1 bắp ngọt lớn',499000,'JUNGLE BROWN COUPLE','https://www.cgv.vn/media/concession/web/6426a4fc4b1ca_1680254204.png'),(7,'1 bình Jungle Brown + 1 nước siêu lớn',259000,'JUNGLE BROWN MY COI','https://www.cgv.vn/media/concession/web/6426a4ac90777_1680254125.png'),(8,'1 Bắp Lớn + 2 Nước Siêu Lớn + 1 Snack. Nhận trong ngày xem phim.',119000,'CGV SNACK COMBO','https://www.cgv.vn/media/concession/web/63aaa389a9927_1672127370.png'),(9,'1 Bắp Lớn + 1 Nước Siêu Lớn + 1 Snack. Nhận trong ngày xem phim.',95000,'MY SNACK COMBO','https://www.cgv.vn/media/concession/web/63aaa361ceea4_1672127330.png');
/*!40000 ALTER TABLE `combo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` int DEFAULT NULL,
  `movie_id` bigint DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `start_time` varchar(20) DEFAULT NULL,
  `sub_type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo2xb2v3munbkxledunsapvbqy` (`room_id`),
  KEY `FKk878ajfp17ncw8p3sf96ube6b` (`movie_id`),
  KEY `FKnbo4edbw4rmq7xeir87c2f64l` (`sub_type_id`),
  CONSTRAINT `FKk878ajfp17ncw8p3sf96ube6b` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`),
  CONSTRAINT `FKnbo4edbw4rmq7xeir87c2f64l` FOREIGN KEY (`sub_type_id`) REFERENCES `subtitle_type` (`id`),
  CONSTRAINT `FKo2xb2v3munbkxledunsapvbqy` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (4,120000,16,1,'2023-04-10','10:30',1),(5,120000,16,2,'2023-04-10','12:30',2),(6,120000,16,3,'2023-04-10','14:30',1),(7,120000,16,4,'2023-04-10','13:30',2),(8,120000,16,5,'2023-04-10','15:30',3),(9,120000,16,6,'2023-04-10','17:30',1),(10,120000,16,4,'2023-04-11','13:30',2),(11,120000,16,5,'2023-04-11','15:30',3),(12,120000,16,6,'2023-04-11','17:30',1),(13,120000,16,1,'2023-04-11','10:30',2),(14,120000,16,2,'2023-04-11','12:30',1),(15,120000,16,3,'2023-04-11','14:30',1),(16,120000,16,7,'2023-04-11','10:30',2),(17,120000,16,8,'2023-04-11','12:30',1),(18,120000,16,9,'2023-04-11','14:30',2),(19,120000,16,10,'2023-04-10','10:30',2),(20,120000,16,10,'2023-04-10','12:30',1),(21,120000,16,10,'2023-04-10','14:30',2),(22,120000,16,11,'2023-04-10','13:30',3),(23,120000,16,11,'2023-04-10','15:30',3),(24,120000,16,11,'2023-04-10','17:30',3),(26,120000,20,13,'2023-04-12','12:30',1),(27,120000,20,14,'2023-04-12','14:30',1),(28,120000,20,15,'2023-04-12','16:30',1),(29,120000,20,13,'2023-04-12','18:30',1),(30,90000,20,12,'2023-04-12','23:30',1),(31,120000,20,15,'2023-04-12','21:30',1),(34,120000,21,14,'2023-04-12','14:30',2),(35,120000,21,15,'2023-04-12','16:30',2),(37,120000,21,12,'2023-04-12','11:30',2),(38,120000,21,15,'2023-04-12','21:30',2),(41,120000,22,14,'2023-04-12','14:30',1),(42,120000,22,15,'2023-04-12','16:30',2),(45,120000,22,15,'2023-04-12','21:30',2),(46,100000,24,12,'2023-04-12','15:30',1),(48,120000,24,14,'2023-04-12','14:30',1),(49,120000,24,15,'2023-04-12','16:30',2),(52,120000,24,15,'2023-04-12','21:30',2),(55,120000,20,2,'2023-04-10','10:30',1),(57,120000,20,3,'2023-04-10','10:30',1),(58,120000,20,3,'2023-04-10','12:30',2),(74,90000,24,1,'2023-04-13','22:30',1),(75,90000,23,1,'2023-04-10','15:00',1),(80,90000,22,1,'2023-04-12','21:50',1),(81,100000,21,1,'2023-04-10','21:30',1),(88,65000,37,10,'2023-04-11','09:30',1),(89,65000,41,10,'2023-04-11','12:30',1),(90,65000,43,10,'2023-04-12','12:00',1),(91,65000,44,10,'2023-04-12','17:30',1),(92,60000,40,10,'2023-04-12','21:30',1),(93,65000,46,10,'2023-04-13','09:30',1),(94,62000,20,10,'2023-04-13','12:30',1),(95,62000,16,11,'2023-04-13','09:30',1),(96,62000,16,11,'2023-04-14','09:30',1),(97,12000,16,11,'2023-04-15','21:30',1),(98,62000,16,11,'2023-04-16','12:30',1),(99,62000,16,11,'2023-04-17','12:30',1),(100,62000,16,11,'2023-04-18','12:30',1),(101,62000,16,11,'2023-04-19','12:30',1),(102,62000,16,6,'2023-04-12','12:30',1),(103,62000,16,7,'2023-04-12','09:30',1),(104,62000,16,50,'2023-04-10','09:40',1),(105,62000,16,53,'2023-04-10','09:45',1),(106,62000,16,12,'2023-04-10','12:30',1),(107,62000,16,54,'2023-04-10','12:30',1),(108,62000,16,55,'2023-04-10','16:33',1),(109,62000,16,56,'2023-04-10','12:39',1),(110,62000,16,58,'2023-04-10','22:00',1),(111,62000,16,59,'2023-04-10','21:30',1),(112,62000,16,60,'2023-04-10','12:30',1),(113,62000,16,61,'2023-04-10','12:30',1),(114,62000,20,2,'2023-04-10','15:30',1),(115,62000,20,2,'2023-04-10','21:30',1),(116,62000,20,1,'2023-04-11','14:30',1),(117,62000,20,1,'2023-04-12','19:00',1),(118,62000,20,1,'2023-04-13','12:30',1),(119,62000,20,1,'2023-04-14','12:00',1),(120,62000,20,1,'2023-04-15','10:33',1),(121,62000,20,1,'2023-04-16','09:00',1),(122,62000,16,1,'2023-04-17','10:00',1),(123,62000,20,1,'2023-04-18','12:30',1),(124,62000,20,1,'2023-04-19','10:00',1),(125,62000,21,41,'2023-04-10','12:00',1),(126,62000,21,41,'2023-04-10','19:00',1),(127,62000,21,46,'2023-04-11','10:30',1),(128,62000,21,18,'2023-04-11','21:33',1),(129,62000,22,47,'2023-04-11','10:30',1),(130,62000,22,48,'2023-04-11','21:00',1),(131,62000,22,48,'2023-04-12','21:00',1),(132,62000,22,48,'2023-04-13','23:00',1),(133,62000,22,16,'2023-04-14','20:00',1),(134,62000,21,16,'2023-04-15','20:00',1),(135,70000,22,16,'2023-04-15','12:30',1),(136,62000,20,16,'2023-04-16','10:00',1),(137,62000,21,16,'2023-04-16','12:00',1),(138,62000,22,16,'2023-04-16','21:00',1),(139,62000,20,7,'2023-04-17','21:00',1),(140,62000,21,7,'2023-04-17','12:00',1),(141,62000,22,7,'2023-04-17','23:00',1),(142,62000,20,7,'2023-04-18','12:00',1),(143,62000,22,7,'2023-04-18','15:00',1),(144,50000,22,8,'2023-04-18','12:12',1),(145,62000,20,9,'2023-04-18','12:00',1),(146,6200,21,58,'2023-04-18','22:00',1),(147,62000,22,59,'2023-04-18','12:30',1),(148,62000,16,59,'2023-04-18','15:33',1),(149,62000,21,59,'2023-04-18','19:00',1),(150,62000,20,59,'2023-04-19','12:30',1),(151,62000,16,55,'2023-04-19','10:00',1),(152,62000,20,55,'2023-04-19','15:00',1),(153,62000,22,55,'2023-04-20','09:00',1),(154,62000,16,54,'2023-04-19','10:00',1),(155,62000,20,54,'2023-04-19','12:30',1),(156,62000,16,53,'2023-04-18','12:30',1),(157,62000,16,41,'2023-04-11','12:30',1),(158,62000,16,41,'2023-04-11','23:00',1),(159,62000,16,46,'2023-04-12','12:30',1),(160,62000,16,18,'2023-04-13','10:00',1),(161,62000,20,18,'2023-04-13','12:30',1),(162,62000,16,47,'2023-04-13','12:30',1),(163,62000,16,47,'2023-04-13','15:00',1),(164,62000,16,47,'2023-04-13','19:00',1),(165,62000,16,48,'2023-04-13','12:30',1),(166,62000,16,16,'2023-04-13','10:00',1),(167,62000,20,16,'2023-04-13','13:30',1),(168,62000,16,41,'2023-04-13','14:00',1),(169,62000,16,18,'2023-04-14','10:00',1),(170,62000,16,18,'2023-04-15','12:00',1),(171,62000,16,18,'2023-04-16','11:00',1),(172,62000,16,42,'2023-04-14','12:30',1),(173,62000,20,42,'2023-04-14','15:30',1);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'Action'),(2,'Comedy'),(3,'Drama'),(4,'Horror'),(5,'Romance'),(6,'Thriller'),(7,'Science Fiction'),(8,'Fantasy'),(9,'Documentary'),(10,'Animation'),(11,'Adventure');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cast` varchar(255) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `duration_minutes` int NOT NULL,
  `is_showing` bit(1) NOT NULL,
  `language` varchar(20) DEFAULT NULL,
  `poster_url` varchar(255) DEFAULT NULL,
  `rating` varchar(20) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `trailer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (16,'Tom Holland, Samuel L. Jackson, Zendaya, Cobie Smulders','Following the events of Avengers: Endgame, Spider-Man must step up to take on new threats in a world that has changed forever.','Jon Watts',129,_binary '','EN','spridermanfarhome.jpeg','C13','2023-05-02','Spider-Man: Far From Home','https://www.youtube.com/watch?v=Nt9L1jCKGnE&ab_channel=SonyPicturesEntertainment'),(20,'Chris Pratt, Anya Taylor-Joy, Charlie Day, …','Câu chuyện về cuộc phiêu lưu của anh em Super Mario đến vương quốc Nấm.','Aaron Horvath, Michael Jelenic',92,_binary '','EN','mario.jpg','P','2023-05-01','PHIM ANH EM SUPER MARIO','https://www.youtube.com/watch?v=UGO_i2tf1BM'),(21,'Chris Pine, Michelle Rodriguez, Regé-Jean Page','Theo chân một tên trộm quyến rũ và một nhóm những kẻ bịp bợm nghiệp dư thực hiện vụ trộm sử thi nhằm lấy lại một di vật đã mất, nhưng mọi thứ trở nên nguy hiểm khó lường ','John Francis Daley, Jonathan Goldstein',134,_binary '','EN','nguctoivarong.jpeg','C13','2023-04-20','NGỤC TỐI & RỒNG: DANH DỰ CỦA KẺ TRỘM','https://www.youtube.com/watch?v=P4IA6pIVb-w'),(22,'Russell Crowe, Franco Nero…','Lấy cảm hứng từ những hồ sơ có thật của Cha Gabriele Amorth, Trưởng Trừ Tà của Vatican (Russell Crowe, đoạt giải Oscar®), bộ phim \"The Pope\'s Exorcist\" theo chân Amorth trong cuộc điều tra về vụ quỷ ám kinh hoàng của một cậu bé và dần khám phá ra những bí mật hàng thế kỷ mà Vatican đã cố gắng giấu kín.','Julius Avery',104,_binary '','EN','khactinhcuaquy.jpeg','C18','2023-05-04','KHẮC TINH CỦA QUỶ','https://www.youtube.com/watch?v=p4LAYNacgkI'),(23,'Dương Tử Quỳnh, Quan Kế Huy, Stephanie Hsu, James Hong, Jamie Lee Curtis,...','Một phụ nữ trung niên nhập cư người Trung Quốc bị cuốn vào một cuộc phiêu lưu điên cuồng, nơi cô ấy một mình giải cứu thế giới bằng cách khám phá các vũ trụ khác và các bản thể khác của chính cô.','Daniel Kwan, Daniel Scheinert',139,_binary '','EN','everythingallinonce.jpeg','C18','2023-05-05','CUỘC CHIẾN ĐA VŨ TRỤ','https://www.youtube.com/watch?v=4y5JUTzFlVs'),(24,'ANIME','\"Khóa Chặt Cửa Nào Suzume\" kể câu chuyện khi Suzume vô tình gặp một chàng trai trẻ ','Makoto Shinkai',122,_binary '','JP','khoachatcuasuzume.jpeg','P','2023-05-06','KHÓA CHẶT CỬA NÀO SUZUME','https://www.youtube.com/watch?v=CTxLZYbT9Rw'),(35,'ý Hải, Quốc Cường, Trung Dũng, Huy Khánh, Thanh Thức, Trần Kim Hải, Huỳnh Thi, Diệp Bảo Ngọc, Tú Tri, Quỳnh Như, Tạ Lâm, bé Thùy Linh…','Lật mặt 6 sẽ thuộc thể loại giật gân, tâm lý pha hành động, hài hước.','Lý Hải',132,_binary '','VN','latmat6.jpeg','C16','2023-04-28','LẬT MẶT 6: TẤM VÉ ĐỊNH MỆNH','https://www.youtube.com/watch?v=OobBWy3avUo'),(36,' Kim Bo Ra, Kim Jae Hyun','Lời đồn ma ám về nhà ga Oksu ngày càng nhiều khi những vụ án kinh hoàng liên tục xảy ra. Một đường ray cũ kỹ, một chiếc giếng bỏ hoang, những con số gây ám ảnh hay những vết thương kỳ dị trên thi thể người xấu số... Tất cả dẫn đến một bí mật đau lòng bị chôn vùi nhiều năm trước.','Jeong Yong Ki',80,_binary '','KR','tramtauma.jpeg','C18','2023-04-28','TRẠM TÀU MA','https://www.youtube.com/watch?v=WFJL_P4w228'),(37,'Anton Eldarov, Polina Gagarina, Aleksandr Gavrilin,…','Câu chuyện xoay quanh tình bạn của chú mèo Vincent và chú chuột Maurice. Vincent vừa nhận được công việc bảo vệ kiệt tác tranh Mona Lisa trong một viện bảo tàng, còn Maurice lại có niềm đam mê gặm nhấm bức tranh này. Mọi chuyện phức tạp hơn khi có người cũng đang nung nấu ý định cướp lấy tuyệt tác Mona Lisa. Liệu Vincent và đồng đội của mình có thể cứu lấy những kiệt tác của Davinci và bảo vệ danh cho bảo tàng không?','Vasiliy Rovenskiy',80,_binary '','EN','meosieuquay.jpeg','C13','2023-04-26','MÈO SIÊU QUẬY Ở VIỆN BẢO TÀNG','https://www.youtube.com/watch?v=yU7dSdeAH7U'),(38,'Matt Damon, Ben Affleck, Jason Bateman, Chris Messina, Matthew Maher, Marlon Wayans, Jay Mohr, Julius Tennon, Chris Tucker, Viola Davis','Từ đạo diễn đã từng đoạt giải thưởng Ben Affleck, AIR hé lộ mối quan hệ đột phá giữa huyền thoại Michael Jordan khi mới bắt đầu sự nghiệp và bộ phận bóng rổ còn non trẻ của Nike, đã làm thay đổi thế giới thể thao và văn hóa đương đại với thương hiệu Air Jordan. Câu chuyện cảm động này kể về sự đánh cược khi đặt lên bàn cân tình hình kinh doanh của cả công ty, tầm nhìn vĩ đại của một người mẹ biết giá trị và tài năng của con trai mình, và một siêu sao bóng rổ đã trở thành huyền thoại.','Ben Affleck',112,_binary '','EN','airlegend.jpeg','C18','2023-04-14','AIR - THEO ĐUỔI MỘT HUYỀN THOẠI','https://www.youtube.com/watch?v=0h9vZ52Vals'),(39,'Thái Hòa, Thu Trang, Tiến Luật, NSND Hồng Vân, Huỳnh Phương, Vinh Râu, Thái Vũ,...','Lấy cảm hứng từ web drama Chuyện Xóm Tui, phiên bản điện ảnh sẽ mang một màu sắc hoàn toàn khác: hài hước hơn, gần gũi và nhiều cảm xúc hơn Bộ phim là câu chuyện của Nhót - người phụ nữ “chưa kịp già” đã sắp bị mãn kinh, vội vàng đi tìm chồng. Nhưng sâu thẳm trong cô, là khao khát muốn có một đứa con và luôn muốn hàn gắn với người cha suốt ngày say xỉn của mình.','Vũ Ngọc Đãng',112,_binary '','VN','connhotmotchong.jpeg','C16','2023-04-28','CON NHÓT MÓT CHỒNG','https://www.youtube.com/watch?v=3OGtcxl_8Ro'),(40,'Vin Diesel, Jason Momoa, Brie Larson,…   Hành Động, Tội phạm','Dom Toretto và gia đình của anh ấy bị trở thành mục tiêu của người con trai đầy thù hận của ông trùm ma túy Hernan Reyes.','Louis Leterrier',141,_binary '','EN','fastandfurios10.jpeg','C16','2023-05-19','FAST AND FURIOUS X','https://www.youtube.com/watch?v=Jphd23nUCLs'),(41,'','Phim điện ảnh Doraemon: Nobita Và Vùng Đất Lý Tưởng Trên Bầu Trời kể câu chuyện khi Nobita tìm thấy một hòn đảo hình lưỡi liềm trên trời mây. Ở nơi đó, tất cả đều hoàn hảo… đến mức cậu nhóc Nobita mê ngủ ngày cũng có thể trở thành một thần đồng toán học, một siêu sao thể thao. Cả hội Doraemon cùng sử dụng một món bảo bối độc đáo chưa từng xuất hiện trước đây để đến với vương quốc tuyệt vời này.','Takumi Doyama',108,_binary '','JP','doraemonNobita.jpeg','P','2023-05-26','PHIM ĐIỆN ẢNH DORAEMON: NOBITA VÀ VÙNG ĐẤT LÝ TƯỞNG TRÊN BẦU TRỜI','https://www.youtube.com/watch?v=8-KHCOIEkRQ&ab_channel=CGVCinemasVietnam'),(42,'Halle Bailey, Jonah Hauer-King, Daveed Diggs, Awkwafina, Jacob Tremblay, Noma Dumezweni, Art Malik, with Javier Bardem and Melissa McCarthy','“Nàng Tiên Cá” là câu chuyện được yêu thích về Ariel - một nàng tiên cá trẻ xinh đẹp và mạnh mẽ với khát khao phiêu lưu. Ariel là con gái út của Vua Triton và cũng là người ngang ngạnh nhất, nàng khao khát khám phá về thế giới bên kia đại dương. Trong một lần ghé thăm đất liền, nàng đã phải lòng Hoàng tử Eric bảnh bao.','Rob Marshall',135,_binary '','EN','nangtienca.jpeg','C13','2023-05-26','NÀNG TIÊN CÁ','https://www.youtube.com/watch?v=R2cjgaopZcg&ab_channel=CGVCinemasVietnam'),(43,'Chris Pratt, Zoe Saldana, Dave Bautista','Cho dù vũ trụ này có bao la đến đâu, các Vệ Binh của chúng ta cũng không thể trốn chạy mãi mãi. Vệ Binh Dải Ngân Hà 3 dự kiến khởi chiếu tại rạp từ 03.05.2023.',' James Gunn',149,_binary '','EN','vebinhdainganha3.png','C13','2023-05-03','VỆ BINH DẢI NGÂN HÀ 3','https://www.youtube.com/watch?v=O402pXqj79c&ab_channel=CGVCinemasVietnam'),(44,'ulio Cesar, Josh Lucas','Quái Vật Đen xoay quanh câu chuyện khi kỳ nghỉ bình dị của gia đình Oilman Paul Sturges biến thành cơn ác mộng. Bởi họ đã gặp phải một con cá mập Megalodon hung dữ, không từ bất kỳ khoảnh khắc nào để bảo vệ lãnh thổ của mình. Bị mắc kẹt và tấn công liên tục, Paul và gia đình của mình phải tìm cách để an toàn sống sót trở về bờ trước khi con cá mập khát máu này tấn công lần nữa','Adrian Grunberg',100,_binary '','EN','quaivatden.jpeg','C18','2023-05-12','QUÁI VẬT ĐEN','https://www.youtube.com/watch?v=GBh3CLB46UQ&ab_channel=CGVCinemasVietnam'),(46,'Masaya Fukunishi, Yoshiaki Hasegawa, Katsuhisa Hoki, Tetsu Inada, Ryota Iwasaki, Shinichiro Kamio, Mitsuaki Kanuka, Jun Kasama, Subaru Kimura,..','Bộ phim hoạt hình chuyển thể từ loạt truyện tranh “Slam Dunk” nổi tiếng của Takehiko Inoue, khắc họa quá trình trưởng thành cá nhân của những học sinh trung học cống hiến tuổi trẻ cho bóng rổ. Phim theo chân Ryota Miyagi, hậu vệ của đội bóng rổ trường trung học Shohoku. ','Takehiko Inoue, Yasuyuki Ebara',124,_binary '','JP','cuuprodautien.jpeg','C13','2023-04-14','PHIM CÚ ÚP RỔ ĐẦU TIÊN','https://www.youtube.com/watch?v=NEa0J_Q-NIY');
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_genre`
--

DROP TABLE IF EXISTS `movie_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_genre` (
  `movie_id` bigint NOT NULL,
  `genre_id` int NOT NULL,
  PRIMARY KEY (`movie_id`,`genre_id`),
  KEY `FK86p3roa187k12avqfl28klp1q` (`genre_id`),
  CONSTRAINT `FK86p3roa187k12avqfl28klp1q` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`),
  CONSTRAINT `FKp6vjabv2e2435at1hnuxg64yv` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_genre`
--

LOCK TABLES `movie_genre` WRITE;
/*!40000 ALTER TABLE `movie_genre` DISABLE KEYS */;
INSERT INTO `movie_genre` VALUES (16,1),(20,1),(21,1),(24,1),(40,1),(44,1),(46,1),(23,2),(35,2),(37,2),(39,2),(41,2),(39,3),(22,4),(23,4),(36,4),(44,4),(46,8),(38,9),(22,10),(37,10),(40,10),(41,10),(42,10),(43,10),(44,10),(42,11),(43,11);
/*!40000 ALTER TABLE `movie_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'CLIENT');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `capacity` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `cinema_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK838jvntrkjvmbpm310wsdad1r` (`cinema_id`),
  CONSTRAINT `FK838jvntrkjvmbpm310wsdad1r` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'4x6','Cinema 1',1),(2,'7x8','Cinema 2',1),(3,'11x10','Cinema 3',1),(4,'22x11','Cinema 1',6),(5,'21x15','Cinema 2',6),(6,'4x5','Cinema 3',6),(7,'4x5','Cinema 1',12),(8,'7x8','Cinema 2',12),(9,'4x5','Cinema 3',12),(10,'15x21','Cinema 1',2),(11,'7x8','Cinema 2',2),(12,'11x17','Cinema 1',15),(13,'10x11','Cinema 2',15),(14,'11x17','Cinema 1',27),(15,'11x10','Cinema 2',27),(16,'10x24','Cinema1',30),(17,'11x19','Cinema2',30),(18,'18x9','Cinema5',9),(41,'4x4','Cinema1',7),(42,'4x4','Cinema1',3),(43,'4x4','Cinema1',4),(44,'3x4','Cinema1',5),(45,'3x3','Cinema1',9),(46,'3x2','Cinema1',8),(47,'2x2','Cinema1',10),(48,'4x2','Cinema1',11),(49,'2x2','Cinema1',31),(50,'2x2','Cinema1',13),(51,'2x2','Cinema1',28),(52,'2x2','Cinema1',29),(53,'3x2','Cinema1',14),(54,'3x3','Cinema1',16),(55,'3x3','Cinema1',38),(56,'2x3','Cinema1',35),(57,'2x2','Cinema1',32),(58,'2x2','Cinema1',34),(59,'2x2','Cinema1',33),(60,'2x2','Cinema1',37),(61,'2x2','Cinema1',36);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat`
--

DROP TABLE IF EXISTS `seat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `column_num` int NOT NULL,
  `row_num` int NOT NULL,
  `seat_name` varchar(255) DEFAULT NULL,
  `type` smallint DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd7f42843rt05tt66t6vcb7s9u` (`room_id`),
  CONSTRAINT `FKd7f42843rt05tt66t6vcb7s9u` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1794 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat`
--

LOCK TABLES `seat` WRITE;
/*!40000 ALTER TABLE `seat` DISABLE KEYS */;
INSERT INTO `seat` VALUES (1,1,1,'A1',0,1),(2,2,1,'A2',0,1),(3,3,1,'A3',0,1),(4,4,2,'B4',0,1),(5,5,1,'A5',0,1),(6,1,2,'B1',0,1),(7,2,2,'B2',0,1),(8,3,2,'B3',0,1),(9,5,2,'B5',0,1),(10,1,3,'C1',1,1),(11,2,3,'C2',1,1),(12,3,3,'C3',1,1),(13,5,3,'C5',1,1),(14,2,1,'A9',0,3),(15,3,1,'A8',0,3),(16,4,1,'A7',0,3),(17,5,1,'A6',0,3),(18,6,1,'A5',0,3),(19,7,1,'A4',0,3),(20,8,1,'A3',0,3),(21,2,2,'B9',0,3),(22,3,2,'B8',0,3),(23,4,2,'B7',0,3),(24,5,2,'B6',0,3),(25,6,2,'B5',0,3),(26,7,2,'B4',0,3),(27,8,2,'B3',0,3),(28,2,3,'C9',0,3),(29,3,3,'C8',0,3),(30,4,3,'C7',0,3),(31,5,3,'C6',0,3),(32,6,3,'C5',0,3),(33,7,3,'C4',0,3),(34,8,3,'C3',0,3),(35,2,4,'D9',0,3),(36,3,4,'D8',0,3),(37,4,4,'D7',0,3),(38,5,4,'D6',0,3),(39,6,4,'D5',0,3),(40,7,4,'D4',0,3),(41,8,4,'D3',0,3),(42,9,4,'D2',0,3),(43,10,4,'D1',0,3),(44,2,5,'E9',1,3),(45,3,5,'E8',1,3),(46,4,5,'E7',1,3),(47,5,5,'E6',1,3),(48,6,5,'E5',1,3),(49,7,5,'E4',1,3),(50,8,5,'E3',1,3),(51,9,5,'E2',1,3),(52,10,5,'E1',1,3),(53,2,6,'F9',1,3),(54,3,6,'F8',1,3),(55,4,6,'F7',1,3),(56,5,6,'F6',1,3),(57,6,6,'F5',1,3),(58,7,6,'F4',1,3),(59,8,6,'F3',1,3),(60,9,6,'F2',1,3),(61,10,6,'F1',1,3),(62,2,7,'G9',1,3),(63,3,7,'G8',1,3),(64,4,7,'G7',1,3),(65,5,7,'G6',1,3),(66,6,7,'G5',1,3),(67,7,7,'G4',1,3),(68,8,7,'G3',1,3),(69,9,7,'G2',1,3),(70,10,7,'G1',1,3),(71,2,8,'H9',1,3),(72,3,8,'H8',1,3),(73,4,8,'H7',1,3),(74,5,8,'H6',1,3),(75,6,8,'H5',1,3),(76,7,8,'H4',1,3),(77,8,8,'H3',1,3),(78,9,8,'H2',1,3),(79,10,8,'H1',1,3),(80,2,9,'J9',1,3),(81,3,9,'J8',1,3),(82,4,9,'J7',1,3),(83,5,9,'J6',1,3),(84,6,9,'J5',1,3),(85,7,9,'J4',1,3),(86,8,9,'J3',1,3),(87,9,9,'J2',1,3),(88,10,9,'J1',1,3),(89,2,10,'K9',1,3),(90,3,10,'K8',1,3),(91,4,10,'K7',1,3),(92,5,10,'K6',1,3),(93,6,10,'K5',1,3),(94,7,10,'K4',1,3),(95,8,10,'K3',1,3),(96,9,10,'K2',1,3),(97,10,10,'K1',1,3),(98,1,11,'L10',2,3),(99,2,11,'L9',2,3),(100,3,11,'L8',2,3),(101,4,11,'L7',2,3),(102,5,11,'L6',2,3),(103,6,11,'L5',2,3),(104,7,11,'L4',2,3),(105,8,11,'L3',2,3),(106,9,11,'L2',2,3),(107,10,11,'L1',2,3),(108,1,1,'A21',0,10),(109,2,1,'A20',0,10),(110,3,1,'A19',0,10),(111,4,1,'A18',0,10),(112,5,1,'A17',0,10),(113,6,1,'A16',0,10),(114,7,1,'A15',0,10),(115,8,1,'A14',0,10),(116,9,1,'A13',0,10),(117,10,1,'A12',0,10),(118,11,1,'A11',0,10),(119,12,1,'A10',0,10),(120,13,1,'A9',0,10),(121,14,1,'A8',0,10),(122,15,1,'A7',0,10),(123,16,1,'A6',0,10),(124,17,1,'A5',0,10),(125,18,1,'A4',0,10),(126,19,1,'A3',0,10),(127,20,1,'A2',0,10),(128,21,1,'A1',0,10),(129,1,2,'B21',0,10),(130,2,2,'B20',0,10),(131,3,2,'B19',0,10),(132,4,2,'B18',0,10),(133,5,2,'B17',0,10),(134,6,2,'B16',0,10),(135,7,2,'B15',0,10),(136,8,2,'B14',0,10),(137,9,2,'B13',0,10),(138,10,2,'B12',0,10),(139,11,2,'B11',0,10),(140,12,2,'B10',0,10),(141,13,2,'B9',0,10),(142,14,2,'B8',0,10),(143,15,2,'B7',0,10),(144,16,2,'B6',0,10),(145,17,2,'B5',0,10),(146,18,2,'B4',0,10),(147,19,2,'B3',0,10),(148,20,2,'B2',0,10),(149,21,2,'B1',0,10),(150,1,3,'C21',0,10),(151,2,3,'C20',0,10),(152,3,3,'C19',0,10),(153,4,3,'C18',0,10),(154,5,3,'C17',0,10),(155,6,3,'C16',0,10),(156,7,3,'C15',0,10),(157,8,3,'C14',0,10),(158,9,3,'C13',0,10),(159,10,3,'C12',0,10),(160,11,3,'C11',0,10),(161,12,3,'C10',0,10),(162,13,3,'C9',0,10),(163,14,3,'C8',0,10),(164,15,3,'C7',0,10),(165,16,3,'C6',0,10),(166,17,3,'C5',0,10),(167,18,3,'C4',0,10),(168,19,3,'C3',0,10),(169,20,3,'C2',0,10),(170,21,3,'C1',0,10),(171,1,4,'D21',0,10),(172,2,4,'D20',0,10),(173,3,4,'D19',0,10),(174,4,4,'D18',0,10),(175,5,4,'D17',0,10),(176,6,4,'D16',0,10),(177,7,4,'D15',0,10),(178,8,4,'D14',0,10),(179,9,4,'D13',0,10),(180,10,4,'D12',0,10),(181,11,4,'D11',0,10),(182,12,4,'D10',0,10),(183,13,4,'D9',0,10),(184,14,4,'D8',0,10),(185,15,4,'D7',0,10),(186,16,4,'D6',0,10),(187,17,4,'D5',0,10),(188,18,4,'D4',0,10),(189,19,4,'D3',0,10),(190,20,4,'D2',0,10),(191,21,4,'D1',0,10),(192,1,5,'E21',1,10),(193,2,5,'E20',1,10),(194,3,5,'E19',1,10),(195,4,5,'E18',1,10),(196,5,5,'E17',1,10),(197,6,5,'E16',1,10),(198,7,5,'E15',1,10),(199,8,5,'E14',1,10),(200,9,5,'E13',1,10),(201,10,5,'E12',1,10),(202,11,5,'E11',1,10),(203,12,5,'E10',1,10),(204,13,5,'E9',1,10),(205,14,5,'E8',1,10),(206,15,5,'E7',1,10),(207,16,5,'E6',1,10),(208,17,5,'E5',1,10),(209,18,5,'E4',1,10),(210,19,5,'E3',1,10),(211,20,5,'E2',1,10),(212,21,5,'E1',1,10),(213,1,6,'F21',1,10),(214,2,6,'F20',1,10),(215,3,6,'F19',1,10),(216,4,6,'F18',1,10),(217,5,6,'F17',1,10),(218,6,6,'F16',1,10),(219,7,6,'F15',1,10),(220,8,6,'F14',1,10),(221,9,6,'F13',1,10),(222,10,6,'F12',1,10),(223,11,6,'F11',1,10),(224,12,6,'F10',1,10),(225,13,6,'F9',1,10),(226,14,6,'F8',1,10),(227,15,6,'F7',1,10),(228,16,6,'F6',1,10),(229,17,6,'F5',1,10),(230,18,6,'F4',1,10),(231,19,6,'F3',1,10),(232,20,6,'F2',1,10),(233,21,6,'F1',1,10),(234,1,7,'G21',1,10),(235,2,7,'G20',1,10),(236,3,7,'G19',1,10),(237,4,7,'G18',1,10),(238,5,7,'G17',1,10),(239,6,7,'G16',1,10),(240,7,7,'G15',1,10),(241,8,7,'G14',1,10),(242,9,7,'G13',1,10),(243,10,7,'G12',1,10),(244,11,7,'G11',1,10),(245,12,7,'G10',1,10),(246,13,7,'G9',1,10),(247,14,7,'G8',1,10),(248,15,7,'G7',1,10),(249,16,7,'G6',1,10),(250,17,7,'G5',1,10),(251,18,7,'G4',1,10),(252,19,7,'G3',1,10),(253,20,7,'G2',1,10),(254,21,7,'G1',1,10),(255,1,8,'H21',1,10),(256,2,8,'H20',1,10),(257,3,8,'H19',1,10),(258,4,8,'H18',1,10),(259,5,8,'H17',1,10),(260,6,8,'H16',1,10),(261,7,8,'H15',1,10),(262,8,8,'H14',1,10),(263,9,8,'H13',1,10),(264,10,8,'H12',1,10),(265,11,8,'H11',1,10),(266,12,8,'H10',1,10),(267,13,8,'H9',1,10),(268,14,8,'H8',1,10),(269,15,8,'H7',1,10),(270,16,8,'H6',1,10),(271,17,8,'H5',1,10),(272,18,8,'H4',1,10),(273,19,8,'H3',1,10),(274,20,8,'H2',1,10),(275,21,8,'H1',1,10),(276,1,9,'I21',1,10),(277,2,9,'I20',1,10),(278,3,9,'I19',1,10),(279,4,9,'I18',1,10),(280,5,9,'I17',1,10),(281,6,9,'I16',1,10),(282,7,9,'I15',1,10),(283,8,9,'I14',1,10),(284,9,9,'I13',1,10),(285,10,9,'I12',1,10),(286,11,9,'I11',1,10),(287,12,9,'I10',1,10),(288,13,9,'I9',1,10),(289,14,9,'I8',1,10),(290,15,9,'I7',1,10),(291,16,9,'I6',1,10),(292,17,9,'I5',1,10),(293,18,9,'I4',1,10),(294,19,9,'I3',1,10),(295,20,9,'I2',1,10),(296,21,9,'I1',1,10),(297,1,10,'J21',1,10),(298,2,10,'J20',1,10),(299,3,10,'J19',1,10),(300,4,10,'J18',1,10),(301,5,10,'J17',1,10),(302,6,10,'J16',1,10),(303,7,10,'J15',1,10),(304,8,10,'J14',1,10),(305,9,10,'J13',1,10),(306,10,10,'J12',1,10),(307,11,10,'J11',1,10),(308,12,10,'J10',1,10),(309,13,10,'J9',1,10),(310,14,10,'J8',1,10),(311,15,10,'J7',1,10),(312,16,10,'J6',1,10),(313,17,10,'J5',1,10),(314,18,10,'J4',1,10),(315,19,10,'J3',1,10),(316,20,10,'J2',1,10),(317,21,10,'J1',1,10),(318,1,11,'K21',1,10),(319,2,11,'K20',1,10),(320,3,11,'K19',1,10),(321,4,11,'K18',1,10),(322,5,11,'K17',1,10),(323,6,11,'K16',1,10),(324,7,11,'K15',1,10),(325,8,11,'K14',1,10),(326,9,11,'K13',1,10),(327,10,11,'K12',1,10),(328,11,11,'K11',1,10),(329,12,11,'K10',1,10),(330,13,11,'K9',1,10),(331,14,11,'K8',1,10),(332,15,11,'K7',1,10),(333,16,11,'K6',1,10),(334,17,11,'K5',1,10),(335,18,11,'K4',1,10),(336,19,11,'K3',1,10),(337,20,11,'K2',1,10),(338,21,11,'K1',1,10),(339,1,12,'L21',1,10),(340,2,12,'L20',1,10),(341,3,12,'L19',1,10),(342,4,12,'L18',1,10),(343,5,12,'L17',1,10),(344,6,12,'L16',1,10),(345,7,12,'L15',1,10),(346,8,12,'L14',1,10),(347,9,12,'L13',1,10),(348,10,12,'L12',1,10),(349,11,12,'L11',1,10),(350,12,12,'L10',1,10),(351,13,12,'L9',1,10),(352,14,12,'L8',1,10),(353,15,12,'L7',1,10),(354,16,12,'L6',1,10),(355,17,12,'L5',1,10),(356,18,12,'L4',1,10),(357,19,12,'L3',1,10),(358,20,12,'L2',1,10),(359,21,12,'L1',1,10),(360,1,13,'M21',1,10),(361,2,13,'M20',1,10),(362,3,13,'M19',1,10),(363,4,13,'M18',1,10),(364,5,13,'M17',1,10),(365,6,13,'M16',1,10),(366,7,13,'M15',1,10),(367,8,13,'M14',1,10),(368,9,13,'M13',1,10),(369,10,13,'M12',1,10),(370,11,13,'M11',1,10),(371,12,13,'M10',1,10),(372,13,13,'M9',1,10),(373,14,13,'M8',1,10),(374,15,13,'M7',1,10),(375,16,13,'M6',1,10),(376,17,13,'M5',1,10),(377,18,13,'M4',1,10),(378,19,13,'M3',1,10),(379,20,13,'M2',1,10),(380,21,13,'M1',1,10),(381,1,14,'N18',5,10),(382,2,14,'N17',5,10),(383,3,14,'N16',5,10),(384,4,14,'N15',5,10),(385,5,14,'N14',5,10),(386,6,14,'N13',5,10),(387,7,14,'N12',5,10),(388,8,14,'N11',5,10),(389,9,14,'N10',5,10),(390,10,14,'N9',5,10),(391,13,14,'N8',5,10),(392,14,14,'N7',5,10),(393,15,14,'N6',5,10),(394,16,14,'N5',5,10),(395,17,14,'N4',5,10),(396,18,14,'N3',5,10),(397,19,14,'N2',5,10),(398,20,14,'N1',5,10),(399,1,15,'018',5,10),(400,2,15,'017',5,10),(401,3,15,'016',5,10),(402,4,15,'015',5,10),(403,5,15,'014',5,10),(404,6,15,'013',5,10),(405,7,15,'012',5,10),(406,8,15,'011',5,10),(407,9,15,'010',5,10),(408,10,15,'09',5,10),(409,13,15,'08',5,10),(410,14,15,'07',5,10),(411,15,15,'06',5,10),(412,16,15,'05',5,10),(413,17,15,'04',5,10),(414,18,15,'03',5,10),(415,19,15,'02',5,10),(416,20,15,'01',5,10),(654,2,1,'A2',0,15),(655,3,1,'A3',0,15),(656,1,2,'B1',0,15),(657,2,2,'B2',1,15),(658,3,2,'B3',1,15),(659,4,1,'A4',0,15),(670,1,1,'A1',0,15),(671,5,1,'A5',0,15),(673,4,2,'B4',1,15),(1501,17,11,'K1',1,14),(1502,16,11,'K2',1,14),(1503,15,11,'K3',1,14),(1504,14,11,'K4',1,14),(1505,13,11,'K5',1,14),(1506,12,11,'K6',1,14),(1507,11,11,'K7',1,14),(1508,10,11,'K8',1,14),(1509,9,11,'K9',1,14),(1510,8,11,'K10',1,14),(1511,7,11,'K11',1,14),(1512,6,11,'K12',1,14),(1513,5,11,'K13',1,14),(1514,4,11,'K14',1,14),(1515,3,11,'K15',1,14),(1516,2,11,'K16',1,14),(1517,1,11,'K17',1,14),(1518,16,10,'J1',1,14),(1519,15,10,'J2',1,14),(1520,14,10,'J3',1,14),(1521,13,10,'J4',1,14),(1522,12,10,'J5',1,14),(1523,11,10,'J6',1,14),(1524,10,10,'J7',1,14),(1525,9,10,'J8',1,14),(1526,8,10,'J9',1,14),(1527,7,10,'J10',1,14),(1528,6,10,'J11',1,14),(1529,5,10,'J12',1,14),(1530,4,10,'J13',1,14),(1531,3,10,'J14',1,14),(1532,16,9,'H1',1,14),(1533,16,8,'G1',1,14),(1534,3,9,'H14',1,14),(1535,3,8,'G14',1,14),(1536,16,7,'F1',0,14),(1537,3,7,'F14',0,14),(1538,16,6,'E1',0,14),(1539,6,6,'E11',0,14),(1540,16,5,'D1',0,14),(1541,6,5,'D11',0,14),(1542,15,4,'C1',0,14),(1544,16,2,'B1',0,14),(1545,16,1,'A1',0,14),(1546,4,2,'B13',0,14),(1547,4,1,'A13',0,14),(1548,15,1,'A2',0,14),(1549,14,1,'A3',0,14),(1550,13,1,'A4',0,14),(1551,12,1,'A5',0,14),(1553,11,1,'A6',0,14),(1554,10,1,'A7',0,14),(1555,9,1,'A8',0,14),(1556,8,1,'A9',0,14),(1557,7,1,'A10',0,14),(1558,6,1,'A11',0,14),(1559,5,1,'A12',0,14),(1560,15,2,'B2',0,14),(1561,14,2,'B3',0,14),(1562,13,2,'B4',0,14),(1563,12,2,'B5',0,14),(1564,11,2,'B6',0,14),(1565,10,2,'B7',0,14),(1566,9,2,'B8',0,14),(1567,8,2,'B9',0,14),(1568,7,2,'B10',0,14),(1569,6,2,'B11',0,14),(1570,5,2,'B12',0,14),(1571,14,4,'C2',0,14),(1572,13,4,'C3',0,14),(1573,12,4,'C4',0,14),(1574,11,4,'C5',0,14),(1575,10,4,'C6',0,14),(1576,9,4,'C7',0,14),(1577,8,4,'C8',0,14),(1578,15,5,'D2',0,14),(1579,14,5,'D3',0,14),(1580,13,5,'D4',0,14),(1581,12,5,'D5',0,14),(1582,11,5,'D6',0,14),(1583,10,5,'D7',0,14),(1584,9,5,'D8',0,14),(1585,8,5,'D9',0,14),(1586,7,5,'D10',0,14),(1587,15,6,'E2',0,14),(1588,14,6,'E3',0,14),(1589,13,6,'E4',0,14),(1590,12,6,'E5',0,14),(1591,11,6,'E6',0,14),(1592,10,6,'E7',0,14),(1593,9,6,'E8',0,14),(1594,8,6,'E9',0,14),(1595,7,6,'E10',0,14),(1596,15,7,'F2',0,14),(1597,14,7,'F3',0,14),(1598,13,7,'F4',0,14),(1599,12,7,'F5',0,14),(1600,11,7,'F6',0,14),(1601,10,7,'F7',0,14),(1602,9,7,'F8',0,14),(1603,8,7,'F9',0,14),(1604,7,7,'F10',0,14),(1605,6,7,'F11',0,14),(1606,5,7,'F12',0,14),(1607,4,7,'F13',0,14),(1609,15,8,'G2',1,14),(1610,14,8,'G3',1,14),(1611,13,8,'G4',1,14),(1612,12,8,'G5',1,14),(1613,11,8,'G6',1,14),(1614,10,8,'G7',1,14),(1615,9,8,'G8',1,14),(1616,8,8,'G9',1,14),(1617,7,8,'G10',1,14),(1618,6,8,'G11',1,14),(1619,5,8,'G12',1,14),(1620,4,8,'G13',1,14),(1621,15,9,'H2',1,14),(1622,14,9,'H3',1,14),(1623,13,9,'H4',1,14),(1624,12,9,'H5',1,14),(1625,11,9,'H6',1,14),(1626,10,9,'H7',1,14),(1627,9,9,'H8',1,14),(1628,8,9,'H9',1,14),(1629,7,9,'H10',1,14),(1630,6,9,'H11',1,14),(1631,4,9,'H13',1,14),(1632,5,9,'H12',1,14),(1650,1,1,'A1',0,61),(1651,2,2,'B2',0,61),(1652,2,1,'A2',1,61),(1653,1,2,'B1',1,61),(1654,1,1,'A1',0,60),(1655,1,2,'A2',0,60),(1656,2,1,'A3',0,60),(1657,2,2,'A4',0,60),(1658,1,1,'A1',0,59),(1659,2,1,'A2',0,59),(1660,1,2,'A3',0,59),(1661,2,2,'A4',0,59),(1662,1,1,'A1',0,58),(1663,2,1,'A2',0,58),(1664,1,2,'A3',0,58),(1665,2,2,'A4',0,58),(1666,1,1,'A1',0,57),(1667,2,1,'A2',0,57),(1668,1,2,'A3',0,57),(1669,2,2,'A4',0,57),(1670,1,1,'A1',0,56),(1671,2,1,'A2',0,56),(1672,3,1,'A3',0,56),(1673,1,2,'B1',0,56),(1674,2,2,'B2',0,56),(1675,3,2,'B3',1,56),(1676,1,1,'A1',0,55),(1677,2,1,'A2',0,55),(1678,3,1,'A3',0,55),(1679,1,2,'B1',0,55),(1680,2,2,'B2',0,55),(1681,3,2,'B3',1,55),(1682,1,3,'C1',1,55),(1683,2,3,'C2',1,55),(1684,3,3,'C3',1,55),(1685,1,1,'C1',0,54),(1686,2,1,'C2',0,54),(1687,3,1,'C3',0,54),(1688,1,2,'A1',0,54),(1689,1,3,'B1',0,54),(1690,2,2,'A2',0,54),(1691,2,3,'B2',0,54),(1692,1,1,'A1',0,53),(1693,2,1,'A2',0,53),(1694,1,2,'B1',0,53),(1695,2,2,'B2',0,53),(1696,1,3,'C1',0,53),(1697,2,3,'C2',0,53),(1698,1,1,'A1',0,50),(1699,2,1,'A2',0,50),(1700,1,2,'B1',0,50),(1701,2,2,'B2',0,50),(1702,1,1,'A1',0,51),(1703,2,1,'A2',0,51),(1704,1,2,'A3',0,51),(1705,2,2,'A4',0,51),(1706,1,1,'A1',0,52),(1707,2,1,'A2',0,52),(1708,2,2,'A3',0,52),(1709,1,2,'A4',0,52),(1710,1,1,'A1',0,7),(1711,5,4,'D5',0,7),(1712,1,4,'D1',0,7),(1713,5,1,'A5',0,7),(1714,2,2,'B2',0,7),(1715,2,3,'C2',0,7),(1716,4,2,'B4',0,7),(1717,4,3,'C4',0,7),(1718,4,3,'A1',0,8),(1719,5,3,'A2',0,8),(1720,4,4,'B1',0,8),(1721,5,4,'B2',0,8),(1722,6,3,'A3',0,8),(1723,6,4,'B3',0,8),(1724,5,5,'A1',0,13),(1725,6,5,'A2',0,13),(1726,7,5,'A3',0,13),(1727,5,6,'B1',0,13),(1728,6,6,'B2',0,13),(1729,7,6,'B3',0,13),(1730,5,7,'C1',0,13),(1731,6,7,'C2',0,13),(1732,7,7,'C3',0,13),(1733,7,4,'A1',0,12),(1734,8,4,'A2',0,12),(1735,7,5,'A3',0,12),(1736,8,5,'A4',0,12),(1737,5,2,'B5',1,15),(1738,1,1,'A1',0,9),(1739,2,1,'A2',0,9),(1740,1,4,'D1',0,9),(1741,2,4,'D2',0,9),(1742,5,2,'C1',0,9),(1743,5,3,'B1',0,9),(1744,1,1,'A1',0,49),(1745,2,2,'B2',0,49),(1746,2,1,'A2',0,49),(1747,1,2,'B1',0,49),(1748,5,8,'A1',0,4),(1749,6,8,'A2',0,4),(1750,7,8,'A3',0,4),(1751,5,9,'B1',0,4),(1752,6,9,'B2',0,4),(1753,7,9,'B3',0,4),(1754,1,1,'A1',0,6),(1755,1,2,'B1',0,6),(1756,1,3,'C1',0,6),(1757,1,4,'D1',0,6),(1758,3,1,'A3',0,6),(1759,3,2,'B3',0,6),(1760,3,3,'C3',0,6),(1761,3,4,'D3',0,6),(1762,5,1,'A5',0,6),(1763,5,2,'B5',0,6),(1764,2,1,'A2',0,6),(1765,4,1,'A4',0,6),(1766,2,2,'B2',0,6),(1767,4,2,'B4',0,6),(1768,1,1,'A1',0,16),(1769,1,2,'B1',0,16),(1770,1,3,'C1',0,16),(1771,5,1,'A5',0,16),(1772,5,3,'C5',0,16),(1773,5,2,'B5',0,16),(1774,1,1,'A1',0,2),(1775,1,2,'A2',0,2),(1776,1,3,'A3',0,2),(1777,1,4,'A4',0,2),(1778,1,5,'A5',0,2),(1779,2,1,'B1',0,2),(1780,2,5,'B5',0,2),(1781,7,1,'A7',0,2),(1782,8,1,'A8',0,2),(1783,7,2,'B2',0,2),(1784,8,2,'B3',0,2),(1785,7,3,'B4',0,2),(1786,1,1,'A1',0,11),(1787,2,1,'A2',0,11),(1788,3,1,'A3',0,11),(1789,4,1,'A4',0,11),(1790,5,1,'A5',0,11),(1791,6,1,'A6',0,11),(1792,7,1,'A7',0,11),(1793,8,1,'A8',0,11);
/*!40000 ALTER TABLE `seat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setting` (
  `key` varchar(100) NOT NULL,
  `type` varchar(255) NOT NULL,
  `value` varchar(1000) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES ('CUSTOMER_VERIFY_CONTENT','MAIL_TEMPLATES','<span style=\"font-size:18px;\">Dear [[name]]<br>\r\nPlease the link below to verify your registration&nbsp;<br>\r\n<br>\r\n<a href=\"[[URL]]\" target=\"_self\"><font color=\"#0000ff\">Verify</font></a><br>\r\n<br>\r\nThank you,&nbsp;<br>\r\nShopBee team&nbsp;</span><div><br></div>'),('CUSTOMER_VERIFY_SUBJECT','MAIL_TEMPLATES','Please verify your registration to continue shopping'),('MAIL_FROM','MAIL_SERVER','thuanngo3072002@gmail.com'),('MAIL_HOST','MAIL_SERVER','smtp.gmail.com'),('MAIL_PASSWORD','MAIL_SERVER','jsudbijepfpjzjwc'),('MAIL_PORT','MAIL_SERVER','587'),('MAIL_SENDER_NAME','MAIL_SERVER','CGV Management'),('MAIL_USERNAME','MAIL_SERVER','thuanngo3072002@gmail.com'),('SMTP_AUTH','MAIL_SERVER','true'),('SMTP_SECURED','MAIL_SERVER','true');
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subtitle_type`
--

DROP TABLE IF EXISTS `subtitle_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subtitle_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subtitle_type`
--

LOCK TABLES `subtitle_type` WRITE;
/*!40000 ALTER TABLE `subtitle_type` DISABLE KEYS */;
INSERT INTO `subtitle_type` VALUES (1,'2D Phụ Đề Việt'),(2,'2D Eng&Viet Sub'),(3,'IMAX2D Phụ Đề Việt'),(4,'2D Lồng Tiếng Việt'),(5,'2D Phụ Đề Việt'),(6,'2D Eng&Viet Sub'),(7,'IMAX2D Phụ Đề Việt'),(8,'2D Lồng Tiếng Việt'),(9,'2D Phụ Đề Việt'),(10,'2D Eng&Viet Sub'),(11,'IMAX2D Phụ Đề Việt'),(12,'2D Lồng Tiếng Việt');
/*!40000 ALTER TABLE `subtitle_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `qr_code` varchar(255) DEFAULT NULL,
  `booking_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrg7x158t96nucwslhq2bad6qm` (`booking_id`),
  KEY `FKntdk71xc348htxtr66qu07goi` (`user_id`),
  CONSTRAINT `FKntdk71xc348htxtr66qu07goi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrg7x158t96nucwslhq2bad6qm` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,'thuanhocnguvl',7,1,'2023-04-14 22:24:45.611207',NULL,NULL),(2,'uglyBoy',26,4,'2023-04-14 22:24:45.611207',NULL,NULL),(5,'uglyBoy',31,4,'2023-04-14 22:24:45.611207',NULL,NULL),(6,'uglyBoy',32,4,'2023-04-14 22:24:45.611207',NULL,NULL),(7,'uglyBoy',41,4,'2023-04-19 14:21:15.547318',NULL,NULL),(8,'uglyBoy',47,15,'2023-05-17 22:38:40.853027',NULL,NULL),(11,'uglyBoy',54,9,'2023-06-09 16:25:52.002737',NULL,NULL),(12,'uglyBoy',66,15,'2023-06-10 19:55:32.268628',NULL,NULL),(13,'uglyBoy',68,9,'2023-06-11 20:00:16.247106',NULL,NULL),(14,'uglyBoy',69,9,'2023-06-12 20:01:08.727430',NULL,NULL),(15,'uglyBoy',70,9,'2023-06-13 20:01:37.153260',NULL,NULL),(16,'uglyBoy',71,9,'2023-06-14 20:04:23.334821',NULL,NULL);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl10xjn274m2rkxo54knt2xqvy` (`user_id`),
  CONSTRAINT `FKl10xjn274m2rkxo54knt2xqvy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1066 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (1051,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MTY2NTUsImV4cCI6MTY4NjgyMDI1NX0.8Y7scasKMP2lX2eqFYqM54Dv2pdNwMERGP9ZudsFyj0','BEARER',4),(1052,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MTY5MDMsImV4cCI6MTY4NjgyMDUwM30.x-QngAIPkEZMmGQNkYPZ-7arWmhHQpEEAY02r5471Iw','BEARER',4),(1053,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MjUzODEsImV4cCI6MTY4NjgyODk4MX0.rBE84Eoi5kOYS_-LHlxc28TYkM19kxNkIeesGYwevto','BEARER',4),(1054,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MjU4OTAsImV4cCI6MTY4NjgyOTQ5MH0.emQApnukiBlqPKH6CBbJRtna7sYME95jSrMPNL3Sxk8','BEARER',4),(1055,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MjYwNDEsImV4cCI6MTY4NjgyOTY0MX0.1NMAQaTJJS_Eyahm_IBo4HnIMJ5RkTSp3QQHPfA4VsA','BEARER',4),(1056,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MjYyMTIsImV4cCI6MTY4NjgyOTgxMn0._nFp1d4mas3miIkpRpWVboR-lWc70HNDQU5qUlMr-f4','BEARER',4),(1057,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MjY0OTksImV4cCI6MTY4NjgzMDA5OX0.SEmh3iGQNPrxEXSkMk21gnAun7PxQ7BHgiYdlEtkDLY','BEARER',4),(1058,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MjY3NjYsImV4cCI6MTY4NjgzMDM2Nn0.NkpOykSetMUSKVAmXCKr09lsSgyLdyxtrn_pOiAhlA8','BEARER',4),(1059,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MjczNzQsImV4cCI6MTY4NjgzMDk3NH0.E7A6afr1OdE5G5yHAeYu3HFfWXuxu-PCCe22ZMbQfi0','BEARER',4),(1060,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MjgxMjUsImV4cCI6MTY4NjgzMTcyNX0.9da3XRAjQz5gtGurP4k4VJw7WZYgurGJqL7DrMpfBNw','BEARER',4),(1061,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MzA2MTQsImV4cCI6MTY4NjgzNDIxNH0.oWpm-z-OTmZk56BU3LCuWFxXJXAVFyU1g9ZIdZGugm8','BEARER',4),(1062,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MzE1MDYsImV4cCI6MTY4NjgzNTEwNn0.xJ5B50JsxvwegpAcdumq3qDUJTC656Cx2NoPmq9CUGo','BEARER',4),(1063,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MzI1MTMsImV4cCI6MTY4NjgzNjExM30.yG7QzRA1iyTRuzO1d_ikjWPKy0Al_8F9ya2w6NyYIsg','BEARER',4),(1064,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MzI1ODcsImV4cCI6MTY4NjgzNjE4N30.zdqxUXcqqiisgp8mNBYkzPtl67REXWRStu7P3we0-BY','BEARER',4),(1065,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aHVhbm5nbzMwNzIwMkBnbWFpbC5jb20iLCJpYXQiOjE2ODY4MzQyMDcsImV4cCI6MTY4NjgzNzgwN30.QnHce1-DCdbiCPLECbkxrpkGN6zSH8Fpi36XRCJbcxA','BEARER',4);
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `verification_code` varchar(255) DEFAULT NULL,
  `forgot_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_4bgmpi98dylab6qdvf9xyaxu4` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'thuan2022@gmail.com','thu','thu','$2a$10$cbC3GxjMi3OPWPFFVm2fwuvRa3MobBwzgvT8DNlQJTjNaHFABqKOK','0919462412','vebinhdainganha3.png',_binary '',NULL,NULL),(2,'thuan2023@gmail.com','thuaaa','aaaaaa','$2a$10$1.r10G2FWq6faHp36RKU2uqNVYPXkfsazzT43Q1YTwvXfhMz2AKqm',NULL,'nangtienca.jpeg',_binary '',NULL,NULL),(4,'thuanngo307202@gmail.com','van','ngo','$2a$10$rWcdFPwNbwvpPwPaYtlECeoNbB0HxUydXXJQLA0oUzgoPvUdrg6z2',NULL,'avatar.png',_binary '',NULL,NULL),(9,'thu2k2@gmail.com','van','ann','$2a$10$b2BoD9DW5CY/7473izAJG.E7hFDxsG9zsdFBRmHV00YN2q3FUyppa',NULL,'avatar2.jpeg',_binary '',NULL,NULL),(12,'thuanngo722@yahoo.com','van','truc','$2a$10$wIxyuRCKtMo7f35NM2.Aheu3cNJzdl2Vbpb6cc3f8OMteyu3oKd7e',NULL,'quanlongloe.jpeg',_binary '',NULL,NULL),(15,'kanna.allada@gmail.com','thuan','truc','$2a$10$9BTtGD.MGZagoFXwKjwSOu8KdmZOTeRwtTsAj/yXFoWEREYORoAUC',NULL,'avatar.png',_binary '',NULL,NULL),(18,'thuan2021@gmail.com','thuan','NGU','$2a$10$p/dvPXyoez7owYlrbiy7SeahcKMalmwzlmlqGrPpLr3xH39cFieOy',NULL,'aokhoac2.jpeg',_binary '',NULL,NULL),(19,'thuan2020@gmail.com','thanh','truc','$2a$10$fab1HNYfY4ovsrZ2R6Pqeu/35zjlKuOTJYvaWm39NV.8Dl3ekIMvy',NULL,'avatar.png',_binary '',NULL,NULL),(20,'n20dccn153@student.ptithcm.edu.vn','thuan','af','$2a$10$kRf2fPJOdEgSgDQAOhtm..KGleH3qnXDH4uo1DQLmTDOVZzwxubbK',NULL,'quanlongloe.jpeg',_binary '','BytRrSIFAwAk6ro1aq2OwiurFfE3Nxrg8lKHE9UOvxpaC9zmhjrNCpuzzT0H6LVS','KRE3wPbrtho4iCksbz52NH4SzencPiipOn49AcGSyJRiUNiYdLW09IZXBFyszX6y');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKt4v0rrweyk393bdgt107vdx0x` (`role_id`),
  CONSTRAINT `FKb1ip7h4hxtw8hw3axfhvxwl8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (4,1),(1,2),(2,2),(4,2),(9,2),(12,2),(15,2),(18,2),(19,2),(20,2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-15 22:13:56
