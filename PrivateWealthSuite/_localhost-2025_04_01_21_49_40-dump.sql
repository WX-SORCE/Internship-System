-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: privatewealthsuite
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `advisors`
--

DROP TABLE IF EXISTS `advisors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `advisors` (
  `advisor_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户经理 ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色（客户经理/风控/合规）',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关联的用户 ID',
  PRIMARY KEY (`advisor_id`) USING BTREE,
  UNIQUE KEY `phone_number` (`phone_number`) USING BTREE,
  KEY `fk_advisors_user` (`user_id`) USING BTREE,
  CONSTRAINT `fk_advisors_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advisors`
--

LOCK TABLES `advisors` WRITE;
/*!40000 ALTER TABLE `advisors` DISABLE KEYS */;
INSERT INTO `advisors` VALUES ('advisor001','顾问001','advisor001@example.com','15000000001','客户经理','client001'),('advisor002','顾问002','advisor002@example.com','15000000002','风控','client002'),('advisor003','顾问003','advisor003@example.com','15000000003','合规','client003'),('advisor004','顾问004','advisor004@example.com','15000000004','客户经理','client004'),('advisor005','顾问005','advisor005@example.com','15000000005','风控','client005'),('advisor006','顾问006','advisor006@example.com','15000000006','合规','client006'),('advisor007','顾问007','advisor007@example.com','15000000007','客户经理','client007'),('advisor008','顾问008','advisor008@example.com','15000000008','风控','client008'),('advisor009','顾问009','advisor009@example.com','15000000009','合规','client009'),('advisor010','顾问010','advisor010@example.com','15000000010','客户经理','client010'),('advisor011','顾问011','advisor011@example.com','15000000011','风控','client011'),('advisor012','顾问012','advisor012@example.com','15000000012','合规','client012'),('advisor013','顾问013','advisor013@example.com','15000000013','客户经理','client013'),('advisor014','顾问014','advisor014@example.com','15000000014','风控','client014'),('advisor015','顾问015','advisor015@example.com','15000000015','合规','client015'),('advisor016','顾问016','advisor016@example.com','15000000016','客户经理','client016'),('advisor017','顾问017','advisor017@example.com','15000000017','风控','client017'),('advisor018','顾问018','advisor018@example.com','15000000018','合规','client018'),('advisor019','顾问019','advisor019@example.com','15000000019','客户经理','client019'),('advisor020','顾问020','advisor020@example.com','15000000020','风控','client020');
/*!40000 ALTER TABLE `advisors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `approval_records`
--

DROP TABLE IF EXISTS `approval_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `approval_records` (
  `approval_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `trade_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `level` int NOT NULL COMMENT '审批等级 (1客户经理, 2风控, 3合规, 4通过)',
  `approver_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `decision` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '审批意见',
  `created_at` datetime NOT NULL COMMENT '审批时间',
  PRIMARY KEY (`approval_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='审批记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `approval_records`
--

LOCK TABLES `approval_records` WRITE;
/*!40000 ALTER TABLE `approval_records` DISABLE KEYS */;
INSERT INTO `approval_records` VALUES ('0f9478546495','22b5beda1822',4,'risk005','审核中',NULL,'2025-04-01 15:25:28'),('7494f3faee94','cd52eb256d32',3,'advisor001','审核中',NULL,'2025-04-01 15:11:05'),('9a955901fe55','aceba4897c9e',2,'none','未处理',NULL,'2025-04-01 15:25:28'),('a3897349d7c3','869fcce0f909',2,'none','未处理',NULL,'2025-04-01 15:25:28'),('approval001','trade001',5,'compliance001','通过','交易合理，同意审批','2024-01-01 09:30:00'),('approval002','trade001',4,'risk001','审核中','风险在可控范围内，同意','2024-01-01 10:00:00'),('approval003','trade001',5,'compliance001','通过','符合合规要求，同意通过','2024-01-01 10:30:00'),('approval004','trade002',3,'advisor001','审核中','交易内容清晰，同意','2024-01-02 10:30:00'),('approval005','trade002',3,'advisor001','审核中','经过评估，风险可接受','2024-01-02 11:00:00'),('approval006','trade002',3,'advisor003','同意','合规方面无问题，同意','2024-01-02 11:30:00'),('approval007','trade003',3,'advisor001','审核中','交易目的明确，同意审批','2024-01-03 11:30:00'),('approval008','trade003',3,'advisor001','审核中','风险评估结果良好，同意','2024-01-03 12:00:00'),('approval009','trade003',3,'advisor003','同意','符合公司规定，同意通过','2024-01-03 12:30:00'),('approval010','trade004',3,'advisor001','审核中','客户需求合理，同意','2024-01-04 12:30:00'),('approval011','trade004',2,'advisor002','同意','对交易风险有充分认识，同意','2024-01-04 13:00:00'),('approval012','trade004',3,'advisor003','同意','合规审核通过，同意交易','2024-01-04 13:30:00'),('approval013','trade005',2,'advisor001','同意','交易计划可行，同意','2024-01-05 13:30:00'),('approval014','trade005',2,'advisor002','同意','风险控制措施到位，同意','2024-01-05 14:00:00'),('approval015','trade005',3,'advisor003','同意','符合相关法规要求，同意','2024-01-05 14:30:00'),('approval016','trade006',2,'advisor001','同意','客户情况了解清楚，同意审批','2024-01-06 14:30:00'),('approval017','trade006',2,'advisor002','同意','风险等级匹配，同意','2024-01-06 15:00:00'),('approval018','trade006',3,'advisor003','同意','合规方面无异议，同意通过','2024-01-06 15:30:00'),('approval019','trade007',2,'advisor001','同意','交易安排合理，同意','2024-01-07 15:30:00'),('approval020','trade007',2,'advisor002','同意','对风险有足够应对方案，同意','2024-01-07 16:00:00');
/*!40000 ALTER TABLE `approval_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assessment_info`
--

DROP TABLE IF EXISTS `assessment_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assessment_info` (
  `ai_id` int NOT NULL AUTO_INCREMENT,
  `adverse_credit_history` int NOT NULL DEFAULT '0',
  `car_owned` int NOT NULL DEFAULT '0',
  `client_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `credit_limit` int NOT NULL DEFAULT '0',
  `ege` int NOT NULL DEFAULT '1',
  `loan_amount` int NOT NULL DEFAULT '0',
  `marital_status` int NOT NULL DEFAULT '0',
  `monthly_fixed_expenses` int NOT NULL DEFAULT '0',
  `monthly_income` int NOT NULL DEFAULT '0',
  `occupation_type` int NOT NULL DEFAULT '0',
  `other_debt_amount` int NOT NULL DEFAULT '0',
  `past_due_status` int NOT NULL DEFAULT '0',
  `properties_owned` int NOT NULL DEFAULT '0',
  `risk_profile_score` int NOT NULL DEFAULT '0',
  `risk_tolerance_score` int NOT NULL DEFAULT '0',
  `state` int NOT NULL DEFAULT '0',
  `total_assets` decimal(38,2) NOT NULL DEFAULT '0.00',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ai_id`) USING BTREE,
  KEY `idx_client_id` (`client_id`) USING BTREE,
  CONSTRAINT `fk_assessment_info_client` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assessment_info`
--

LOCK TABLES `assessment_info` WRITE;
/*!40000 ALTER TABLE `assessment_info` DISABLE KEYS */;
INSERT INTO `assessment_info` VALUES (2,0,0,'client001','2025-03-31 14:20:50',0,1,0,0,3000,8000,0,0,0,0,50,40,0,100000.00,'2025-03-31 14:20:50'),(3,0,1,'client002','2025-03-31 14:20:50',5000,1,10000,1,4000,10000,1,5000,0,1,40,30,0,120000.00,'2025-03-31 14:20:50'),(4,0,0,'client003','2025-03-31 14:20:50',0,1,0,0,2500,7000,0,0,0,0,60,50,0,80000.00,'2025-03-31 14:20:50'),(5,0,0,'client004','2025-03-31 14:20:50',0,1,0,0,3500,9000,0,0,0,0,30,20,0,150000.00,'2025-03-31 14:20:50'),(6,0,1,'client005','2025-03-31 14:20:50',8000,1,15000,1,4500,12000,1,8000,0,1,55,45,0,110000.00,'2025-03-31 14:20:50'),(7,0,0,'client006','2025-03-31 14:20:50',0,1,0,0,3200,8500,0,0,0,0,42,35,0,130000.00,'2025-03-31 14:20:50'),(8,0,0,'client007','2025-03-31 14:20:50',0,1,0,0,2800,7500,0,0,0,0,65,55,0,90000.00,'2025-03-31 14:20:50'),(9,0,1,'client008','2025-03-31 14:20:50',6000,1,12000,1,4200,11000,1,6000,0,1,38,28,0,160000.00,'2025-03-31 14:20:50'),(10,0,0,'client009','2025-03-31 14:20:50',0,1,0,0,3300,8800,0,0,0,0,52,42,0,120000.00,'2025-03-31 14:20:50'),(11,0,0,'client010','2025-03-31 14:20:50',0,1,0,0,3600,9200,0,0,0,0,48,38,0,140000.00,'2025-03-31 14:20:50'),(12,0,1,'client011','2025-03-31 14:20:50',9000,1,18000,1,5000,13000,1,9000,0,1,58,48,0,100000.00,'2025-03-31 14:20:50'),(13,0,0,'client012','2025-03-31 14:20:50',0,1,0,0,2600,7200,0,0,0,0,68,58,0,170000.00,'2025-03-31 14:20:50'),(14,0,0,'client013','2025-03-31 14:20:50',0,1,0,0,3100,8300,0,0,0,0,54,44,0,130000.00,'2025-03-31 14:20:50'),(15,0,1,'client014','2025-03-31 14:20:50',7000,1,14000,1,4300,10500,1,7000,0,1,35,25,0,150000.00,'2025-03-31 14:20:50'),(16,0,0,'client015','2025-03-31 14:20:50',0,1,0,0,2900,7800,0,0,0,0,62,52,0,110000.00,'2025-03-31 14:20:50'),(17,0,0,'client016','2025-03-31 14:20:50',0,1,0,0,3400,9500,0,0,0,0,46,36,0,180000.00,'2025-03-31 14:20:50'),(18,0,1,'client017','2025-03-31 14:20:50',10000,1,20000,1,5200,14000,1,10000,0,1,60,50,0,120000.00,'2025-03-31 14:20:50'),(19,0,0,'client018','2025-03-31 14:20:50',0,1,0,0,2700,7400,0,0,0,0,32,22,0,160000.00,'2025-03-31 14:20:50'),(20,0,0,'client019','2025-03-31 14:20:50',0,1,0,0,3700,9800,0,0,0,0,56,46,0,140000.00,'2025-03-31 14:20:50'),(21,0,1,'client020','2025-03-31 14:20:50',8500,1,16000,1,4800,12500,1,8500,0,1,44,34,0,130000.00,'2025-03-31 14:20:50');
/*!40000 ALTER TABLE `assessment_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `nationality` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `id_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `id_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `income_level` int DEFAULT NULL,
  `risk_level` int DEFAULT NULL,
  `total_assets` decimal(38,2) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `kyc_due_date` date DEFAULT NULL COMMENT '下次KYC日期',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL COMMENT '最后更新时间',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关联的用户 ID',
  `advisor_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关联的经理 ID',
  PRIMARY KEY (`client_id`) USING BTREE,
  UNIQUE KEY `phone_number` (`phone_number`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_register_date` (`register_date`) USING BTREE,
  KEY `fk_clients_user` (`user_id`) USING BTREE,
  CONSTRAINT `fk_clients_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES ('06973e9efe6b','53','f','2025-04-01','14543645645','44444','454',NULL,'4354353453454344444',NULL,NULL,444444.00,'2025-04-01 14:53:09',NULL,'正常',NULL,'2025-04-01 14:53:09','96ebfdeb454c','advisor001'),('56ed1b614634','11111','m','2025-04-02','11231322322','222222222@qq.com','中国',NULL,'222222222222222222',NULL,NULL,27838263.00,'2025-04-01 14:51:39',NULL,'正常',NULL,'2025-04-01 14:51:39','21f028268ecd','advisor001'),('client001','客户001','男','1990-01-01','13800138001','client001@example.com','中国','身份证','110101199001010001',5,3,100000.00,'2023-01-01 10:00:00','2024-01-01','活跃','无','2023-01-01 10:00:00','client001','advisor001'),('client002','客户002','女','1991-02-02','13800138002','client002@example.com','中国','身份证','110101199102020002',6,2,120000.00,'2023-02-02 11:00:00','2024-02-02','活跃','无','2023-02-02 11:00:00','client002','advisor002'),('client003','客户003','男','1992-03-03','13800138003','client003@example.com','中国','身份证','110101199203030003',4,4,80000.00,'2023-03-03 12:00:00','2024-03-03','活跃','无','2023-03-03 12:00:00','client003','advisor003'),('client004','客户004','女','1993-04-04','13800138004','client004@example.com','中国','身份证','110101199304040004',7,1,150000.00,'2023-04-04 13:00:00','2024-04-04','活跃','无','2023-04-04 13:00:00','client004','advisor004'),('client005','客户005','男','1994-05-05','13800138005','client005@example.com','中国','身份证','110101199405050005',5,3,110000.00,'2023-05-05 14:00:00','2024-05-05','活跃','无','2023-05-05 14:00:00','client005','advisor005'),('client006','客户006','女','1995-06-06','13800138006','client006@example.com','中国','身份证','110101199506060006',6,2,130000.00,'2023-06-06 15:00:00','2024-06-06','活跃','无','2023-06-06 15:00:00','client006','advisor001'),('client007','客户007','男','1996-07-07','13800138007','client007@example.com','中国','身份证','110101199607070007',4,4,90000.00,'2023-07-07 16:00:00','2024-07-07','活跃','无','2023-07-07 16:00:00','client007','advisor002'),('client008','客户008','女','1997-08-08','13800138008','client008@example.com','中国','身份证','110101199708080008',7,1,160000.00,'2023-08-08 17:00:00','2024-08-08','活跃','无','2023-08-08 17:00:00','client008','advisor003'),('client009','客户009','男','1998-09-09','13800138009','client009@example.com','中国','身份证','110101199809090009',5,3,120000.00,'2023-09-09 18:00:00','2024-09-09','活跃','无','2023-09-09 18:00:00','client009','advisor004'),('client010','客户010','女','1999-10-10','13800138010','client010@example.com','中国','身份证','110101199910100010',6,2,140000.00,'2023-10-10 19:00:00','2024-10-10','活跃','无','2023-10-10 19:00:00','client010','advisor005'),('client011','客户011','男','2000-11-11','13800138011','client011@example.com','中国','身份证','110101200011110011',4,4,100000.00,'2023-11-11 20:00:00','2024-11-11','活跃','无','2023-11-11 20:00:00','client011','advisor001'),('client012','客户012','女','2001-12-12','13800138012','client012@example.com','中国','身份证','110101200112120012',7,1,170000.00,'2023-12-12 21:00:00','2024-12-12','活跃','无','2023-12-12 21:00:00','client012','advisor002'),('client013','客户013','男','2002-01-13','13800138013','client013@example.com','中国','身份证','110101200201130013',5,3,130000.00,'2024-01-13 10:00:00','2025-01-13','活跃','无','2024-01-13 10:00:00','client013','advisor003'),('client014','客户014','女','2003-02-14','13800138014','client014@example.com','中国','身份证','110101200302140014',6,2,150000.00,'2024-02-14 11:00:00','2025-02-14','活跃','无','2024-02-14 11:00:00','client014','advisor004'),('client015','客户015','男','2004-03-15','13800138015','client015@example.com','中国','身份证','110101200403150015',4,4,110000.00,'2024-03-15 12:00:00','2025-03-15','活跃','无','2024-03-15 12:00:00','client015','advisor005'),('client016','客户016','女','2005-04-16','13800138016','client016@example.com','中国','身份证','110101200504160016',7,1,180000.00,'2024-04-16 13:00:00','2025-04-16','活跃','无','2024-04-16 13:00:00','client016','advisor001'),('client017','客户017','男','2006-05-17','13800138017','client017@example.com','中国','身份证','110101200605170017',5,3,140000.00,'2024-05-17 14:00:00','2025-05-17','活跃','无','2024-05-17 14:00:00','client017','advisor002'),('client018','客户018','女','2007-06-18','13800138018','client018@example.com','中国','身份证','110101200706180018',6,2,160000.00,'2024-06-18 15:00:00','2025-06-18','活跃','无','2024-06-18 15:00:00','client018','advisor003'),('client019','客户019','男','2008-07-19','13800138019','client019@example.com','中国','身份证','110101200807190019',4,4,120000.00,'2024-07-19 16:00:00','2025-07-19','活跃','无','2024-07-19 16:00:00','client019','advisor004'),('client020','客户020','女','2009-08-20','13800138020','client020@example.com','中国','身份证','110101200908200020',7,1,190000.00,'2024-08-20 17:00:00','2025-08-20','活跃','无','2024-08-20 17:00:00','client020','advisor005');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logs` (
  `log_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `log_message` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `log_time` datetime(6) DEFAULT NULL,
  `trade_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
INSERT INTO `logs` VALUES ('08008caf-0084-4a9b-9db9-9df3808a7392','创建交易，状态：待审批','2025-04-01 15:11:05.227000','cd52eb256d32'),('28352cbc-8168-4468-ab28-626189ce99d8','创建交易，状态：待审批','2025-04-01 11:55:25.960000','769906c57a1d'),('294b6d47-869a-44c7-93fe-7984f6c95582','创建交易，状态：待审批','2025-04-01 11:55:25.929000','4f557efc9832'),('369ccc2d-4563-4bd2-a530-8223bc01252b','创建交易，状态：待审批','2025-04-01 15:25:28.297000','aceba4897c9e'),('38870b03-9fae-4865-ae10-c9d660547f1e','创建交易，状态：待审批','2025-04-01 13:23:03.900000','af3612056648'),('3b031737-585f-472c-95a2-e0df416b805b','变更交易状态为：已执行','2025-04-01 14:55:23.904000','trade001'),('48345974-9fda-49dd-8afc-55f82867363f','创建交易，状态：待审批','2025-04-01 14:58:11.498000','9d5b91b5e507'),('5b7faa6b-1da1-400d-946e-c09cc9190189','创建交易，状态：待审批','2025-04-01 13:23:03.849000','7e352cc1ddf1'),('640eadba-7363-47d2-91b3-69c86289e073','创建交易，状态：待审批','2025-04-01 13:23:42.744000','b04dcf8a3062'),('64c78d1a-c39c-45b6-940d-0634a5730987','创建交易，状态：待审批','2025-04-01 13:23:42.712000','eb899d027848'),('65498556-a600-4978-ab5f-a3df8ac0f53d','创建交易，状态：待审批','2025-04-01 13:20:52.556000','6a722ca6f200'),('6bde67af-17df-4d6e-8750-91656d8a96d4','创建交易，状态：待审批','2025-04-01 13:20:52.524000','6785358ce8c6'),('6e472abd-a49f-489e-93c4-2659fcb3ba77','创建交易，状态：待审批','2025-04-01 15:07:03.826000','9933176eaa11'),('7b03a94c-5911-4a62-bff2-1a58f58fb5df','创建交易，状态：待审批','2025-04-01 15:25:28.226000','869fcce0f909'),('87c665e3-9381-449f-8835-f7a1b4f733c8','创建交易，状态：待审批','2025-04-01 13:20:52.453000','fae96c1d2a8a'),('899f4a06-01e9-4a24-ae0f-6b73836175e3','创建交易，状态：待审批','2025-04-01 15:07:51.005000','77e2dbc1b02d'),('8cdf8c03-158c-40f3-99a2-4580f072d240','创建交易，状态：待审批','2025-04-01 15:05:30.334000','3978fe5e495f'),('90474f8c-65c4-4aea-81d7-851961881a5e','创建交易，状态：待审批','2025-04-01 13:23:42.792000','4885047932db'),('9d4a56c5-c18c-4904-8b64-b3986e5f66a0','创建交易，状态：待审批','2025-04-01 11:55:25.649000','cd2048b4282d'),('a23a44de-3c58-4b24-9ac8-c7d3e120c96c','创建交易，状态：待审批','2025-04-01 13:23:03.877000','8641d6c58cff'),('a65e121a-3b60-4095-b813-ff2bdca33293','创建交易，状态：待审批','2025-04-01 14:58:11.535000','4f37d3bb6ce5'),('bb67a16c-ffe8-41ec-91c9-9810c95d4003','创建交易，状态：待审批','2025-04-01 14:58:11.556000','325d7294bff6'),('d09f9c51-b7e1-42be-a7d7-ddb2be36f7df','创建交易，状态：待审批','2025-04-01 15:25:28.344000','22b5beda1822'),('d0c521c0-0381-4399-b0e2-97d56f875313','变更交易状态为：已执行','2025-04-01 01:09:38.842000','trade001'),('eedd3227-4a12-4c21-8127-d89dcee0fd23','创建交易，状态：待审批','2025-04-01 15:01:56.819000','ebab63838fc9'),('log001','交易 trade001 开始创建','2024-01-01 09:00:00.000000','trade001'),('log002','交易 trade001 已提交审批','2024-01-01 09:15:00.000000','trade001'),('log003','交易 trade002 开始创建','2024-01-02 10:00:00.000000','trade002'),('log004','交易 trade002 已提交审批','2024-01-02 10:15:00.000000','trade002'),('log005','交易 trade003 开始创建','2024-01-03 11:00:00.000000','trade003'),('log006','交易 trade003 已提交审批','2024-01-03 11:15:00.000000','trade003'),('log007','交易 trade004 开始创建','2024-01-04 12:00:00.000000','trade004'),('log008','交易 trade004 已提交审批','2024-01-04 12:15:00.000000','trade004'),('log009','交易 trade005 开始创建','2024-01-05 13:00:00.000000','trade005'),('log010','交易 trade005 已提交审批','2024-01-05 13:15:00.000000','trade005'),('log011','交易 trade006 开始创建','2024-01-06 14:00:00.000000','trade006'),('log012','交易 trade006 已提交审批','2024-01-06 14:15:00.000000','trade006'),('log013','交易 trade007 开始创建','2024-01-07 15:00:00.000000','trade007'),('log014','交易 trade007 已提交审批','2024-01-07 15:15:00.000000','trade007'),('log015','交易 trade008 开始创建','2024-01-08 16:00:00.000000','trade008'),('log016','交易 trade008 已提交审批','2024-01-08 16:15:00.000000','trade008'),('log017','交易 trade009 开始创建','2024-01-09 17:00:00.000000','trade009'),('log018','交易 trade009 已提交审批','2024-01-09 17:15:00.000000','trade009'),('log019','交易 trade010 开始创建','2024-01-10 18:00:00.000000','trade010'),('log020','交易 trade010 已提交审批','2024-01-10 18:15:00.000000','trade010');
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `notification_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `read` bit(1) DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`notification_id`) USING BTREE,
  KEY `idx_client_id` (`client_id`) USING BTREE,
  CONSTRAINT `fk_notifications_client` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES ('NOTIF-01d3b430','client001','交易 22b5beda1822 的审批结果为【客户经理审批通过】。审批意见：无意见','2025-04-01 15:26:20.749000',_binary '\0','APPROVAL_RESULT'),('NOTIF-02478ff1','client007','您的交易 cd52eb256d32 状态已更新为：订单创建成功！','2025-04-01 15:11:10.833000',_binary '\0','TRADE_STATUS'),('NOTIF-0d0c36b0','client007','交易 cd52eb256d32 的审批结果为【客户经理审批通过】。审批意见：无意见','2025-04-01 15:11:29.500000',_binary '\0','APPROVAL_RESULT'),('NOTIF-0fa144db','client001','您的交易 aceba4897c9e 状态已更新为：订单创建成功！','2025-04-01 15:25:28.333000',_binary '\0','TRADE_STATUS'),('NOTIF-1705be31','client001','交易 trade001 的审批结果为【合规人员审批通过】。审批意见：无意见','2025-04-01 01:09:38.585000',_binary '\0','APPROVAL_RESULT'),('NOTIF-285031ac','client001','交易 trade001 的审批结果为【风控人员审批通过】。审批意见：无意见','2025-04-01 14:55:01.350000',_binary '\0','APPROVAL_RESULT'),('NOTIF-36335ff7','client001','您的交易 869fcce0f909 状态已更新为：订单创建成功！','2025-04-01 15:25:28.283000',_binary '\0','TRADE_STATUS'),('NOTIF-43b45ceb','client001','交易 trade001 的审批结果为【客户经理审批通过】。审批意见：无意见','2025-04-01 01:00:58.544000',_binary '\0','APPROVAL_RESULT'),('NOTIF-4ac65356','client001','交易 trade001 的审批结果为【客户经理审批通过】。审批意见：无意见','2025-04-01 09:48:49.793000',_binary '\0','APPROVAL_RESULT'),('NOTIF-58df1623','client003','交易 trade003 的审批结果为【客户经理审批通过】。审批意见：无意见','2025-04-01 13:10:28.106000',_binary '\0','APPROVAL_RESULT'),('NOTIF-7249622f','client004','交易 trade004 的审批结果为【客户经理审批通过】。审批意见：无意见','2025-04-01 14:53:48.069000',_binary '\0','APPROVAL_RESULT'),('NOTIF-72ffa2bd','client002','交易 trade002 的审批结果为【客户经理审批通过】。审批意见：无意见','2025-04-01 13:10:12.285000',_binary '\0','APPROVAL_RESULT'),('NOTIF-92ac292b','client003','交易 trade003 的审批结果为【客户经理审批通过】。审批意见：无意见','2025-04-01 01:02:54.674000',_binary '\0','APPROVAL_RESULT'),('NOTIF-9ad9792f','client001','交易 trade001 的审批结果为【合规人员审批通过】。审批意见：无意见','2025-04-01 14:55:23.828000',_binary '\0','APPROVAL_RESULT'),('NOTIF-a47b100c','client001','交易 trade001 的审批结果为【客户经理审批通过】。审批意见：无意见','2025-04-01 01:02:24.146000',_binary '\0','APPROVAL_RESULT'),('NOTIF-c1abd921','client001','交易 22b5beda1822 的审批结果为【风控人员审批通过】。审批意见：无意见','2025-04-01 15:28:17.064000',_binary '\0','APPROVAL_RESULT'),('NOTIF-c3f087bf','client001','交易 trade001 的审批结果为【风控人员审批通过】。审批意见：无意见','2025-04-01 01:03:56.442000',_binary '\0','APPROVAL_RESULT'),('NOTIF-e146bf74','client002','交易 trade002 的审批结果为【客户经理审批通过】。审批意见：无意见','2025-04-01 01:08:27.779000',_binary '\0','APPROVAL_RESULT'),('NOTIF-eab5bd3c','client001','交易 trade001 的审批结果为【风控人员审批通过】。审批意见：无意见','2025-04-01 01:09:04.562000',_binary '\0','APPROVAL_RESULT'),('NOTIF-efbba764','client001','您的交易 22b5beda1822 状态已更新为：订单创建成功！','2025-04-01 15:25:28.379000',_binary '\0','TRADE_STATUS'),('ntf001','client001','您的账户今日有一笔收益到账，请查收。','2024-01-01 10:00:00.000000',_binary '\0','收益通知'),('ntf002','client002','您关注的基金产品净值上涨，可及时关注。','2024-01-02 11:00:00.000000',_binary '\0','产品动态'),('ntf003','client003','市场行情波动较大，请注意投资风险。','2024-01-03 12:00:00.000000',_binary '\0','风险提示'),('ntf004','client004','您有一笔即将到期的理财产品，请及时处理。','2024-01-04 13:00:00.000000',_binary '\0','产品到期提醒'),('ntf005','client005','我们推出了新的投资活动，欢迎参与。','2024-01-05 14:00:00.000000',_binary '\0','活动通知'),('ntf006','client006','您的账户信息存在安全风险，请尽快修改密码。','2024-01-06 15:00:00.000000',_binary '\0','安全提醒'),('ntf007','client007','新的债券产品上线，收益稳定，值得关注。','2024-01-07 16:00:00.000000',_binary '\0','产品推荐'),('ntf008','client008','您投资的股票今日跌幅较大，建议关注。','2024-01-08 17:00:00.000000',_binary '\0','投资提醒'),('ntf009','client009','您的账户积分即将过期，请尽快使用。','2024-01-09 18:00:00.000000',_binary '\0','积分提醒'),('ntf010','client010','我们为您定制了专属的投资方案，点击查看。','2024-01-10 19:00:00.000000',_binary '\0','方案推荐'),('ntf011','client011','近期市场利率调整，可能影响您的投资收益。','2024-01-11 20:00:00.000000',_binary '\0','市场动态'),('ntf012','client012','您的投资组合需要调整，建议与顾问沟通。','2024-01-12 21:00:00.000000',_binary '\0','组合调整提醒'),('ntf013','client013','我们的客服团队随时为您服务，如有问题请联系。','2024-01-13 22:00:00.000000',_binary '\0','客服通知'),('ntf014','client014','您关注的投资产品即将开放申购，别错过机会。','2024-01-14 23:00:00.000000',_binary '\0','产品申购提醒'),('ntf015','client015','您的账户有一笔新的交易记录，可查看详情。','2024-01-15 00:00:00.000000',_binary '\0','交易通知'),('ntf016','client016','市场环境变化，建议重新评估您的风险承受能力。','2024-01-16 01:00:00.000000',_binary '\0','风险评估提醒'),('ntf017','client017','我们举办了线上投资讲座，欢迎报名参加。','2024-01-17 02:00:00.000000',_binary '\0','讲座通知'),('ntf018','client018','您的投资收益达到了预期目标，可考虑调整策略。','2024-01-18 03:00:00.000000',_binary '\0','收益达标提醒'),('ntf019','client019','新的保险产品推出，为您的资产提供保障。','2024-01-19 04:00:00.000000',_binary '\0','产品推荐'),('ntf020','client020','您的账户存在异常登录，请注意账户安全。','2024-01-20 05:00:00.000000',_binary '\0','安全预警');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portfolio_items`
--

DROP TABLE IF EXISTS `portfolio_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `portfolio_items` (
  `item_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '持仓项ID（短UUID）',
  `portfolio_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属组合',
  `product_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品编号',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品名称',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品类型',
  `amount` decimal(10,2) DEFAULT NULL,
  `unit_value` decimal(20,4) DEFAULT NULL COMMENT '单价/单位价值',
  `created_at` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`item_id`) USING BTREE,
  KEY `idx_portfolio_id` (`portfolio_id`) USING BTREE,
  KEY `idx_product_code` (`product_code`) USING BTREE,
  KEY `idx_created_at` (`created_at`) USING BTREE,
  CONSTRAINT `fk_portfolio_items_portfolios` FOREIGN KEY (`portfolio_id`) REFERENCES `portfolios` (`portfolio_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='投资组合明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portfolio_items`
--

LOCK TABLES `portfolio_items` WRITE;
/*!40000 ALTER TABLE `portfolio_items` DISABLE KEYS */;
INSERT INTO `portfolio_items` VALUES ('item001','portfolio001','P001','产品001','股票',100.00,10.2500,'2024-01-01 10:00:00'),('item002','portfolio001','P002','产品002','基金',200.00,5.5000,'2024-01-02 11:00:00'),('item003','portfolio001','P003','产品003','债券',150.00,8.7500,'2024-01-03 12:00:00'),('item004','portfolio004','P004','产品004','股票',300.00,3.2000,'2024-01-04 13:00:00'),('item005','portfolio005','P005','产品005','基金',250.00,6.8000,'2024-01-05 14:00:00'),('item006','portfolio006','P006','产品006','债券',120.00,9.9000,'2024-01-06 15:00:00'),('item007','portfolio007','P007','产品007','股票',180.00,7.3000,'2024-01-07 16:00:00'),('item008','portfolio008','P008','产品008','基金',220.00,4.1000,'2024-01-08 17:00:00'),('item009','portfolio009','P009','产品009','债券',160.00,8.4000,'2024-01-09 18:00:00'),('item010','portfolio010','P010','产品010','股票',280.00,2.9000,'2024-01-10 19:00:00'),('item011','portfolio011','P011','产品011','基金',230.00,5.7000,'2024-01-11 20:00:00'),('item012','portfolio012','P012','产品012','债券',140.00,9.2000,'2024-01-12 21:00:00'),('item013','portfolio013','P013','产品013','股票',210.00,7.6000,'2024-01-13 22:00:00'),('item014','portfolio014','P014','产品014','基金',260.00,4.4000,'2024-01-14 23:00:00'),('item015','portfolio015','P015','产品015','债券',170.00,8.8000,'2024-01-15 00:00:00'),('item016','portfolio016','P016','产品016','股票',320.00,2.6000,'2024-01-16 01:00:00'),('item017','portfolio017','P017','产品017','基金',240.00,6.1000,'2024-01-17 02:00:00'),('item018','portfolio018','P018','产品018','债券',130.00,9.5000,'2024-01-18 03:00:00'),('item019','portfolio019','P019','产品019','股票',190.00,7.1000,'2024-01-19 04:00:00'),('item020','portfolio020','P020','产品020','基金',270.00,4.7000,'2024-01-20 05:00:00');
/*!40000 ALTER TABLE `portfolio_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portfolios`
--

DROP TABLE IF EXISTS `portfolios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `portfolios` (
  `portfolio_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组合编号',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '客户编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组合名称',
  `total_value` decimal(20,2) DEFAULT NULL COMMENT '总价值',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`portfolio_id`) USING BTREE,
  KEY `idx_client_id` (`client_id`) USING BTREE,
  KEY `idx_created_at` (`created_at`) USING BTREE,
  CONSTRAINT `fk_clients_portfolios` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='投资组合表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portfolios`
--

LOCK TABLES `portfolios` WRITE;
/*!40000 ALTER TABLE `portfolios` DISABLE KEYS */;
INSERT INTO `portfolios` VALUES ('portfolio001','client001','组合001',10000.00,'2024-01-01 09:00:00','2024-01-01 09:00:00'),('portfolio002','client002','组合002',12000.00,'2024-01-02 10:00:00','2024-01-02 10:00:00'),('portfolio003','client003','组合003',15000.00,'2024-01-03 11:00:00','2024-01-03 11:00:00'),('portfolio004','client004','组合004',18000.00,'2024-01-04 12:00:00','2024-01-04 12:00:00'),('portfolio005','client005','组合005',20000.00,'2024-01-05 13:00:00','2024-01-05 13:00:00'),('portfolio006','client001','组合006',22000.00,'2024-01-06 14:00:00','2024-01-06 14:00:00'),('portfolio007','client002','组合007',25000.00,'2024-01-07 15:00:00','2024-01-07 15:00:00'),('portfolio008','client003','组合008',28000.00,'2024-01-08 16:00:00','2024-01-08 16:00:00'),('portfolio009','client004','组合009',30000.00,'2024-01-09 17:00:00','2024-01-09 17:00:00'),('portfolio010','client005','组合010',32000.00,'2024-01-10 18:00:00','2024-01-10 18:00:00'),('portfolio011','client001','组合011',35000.00,'2024-01-11 19:00:00','2024-01-11 19:00:00'),('portfolio012','client002','组合012',38000.00,'2024-01-12 20:00:00','2024-01-12 20:00:00'),('portfolio013','client003','组合013',40000.00,'2024-01-13 21:00:00','2024-01-13 21:00:00'),('portfolio014','client004','组合014',42000.00,'2024-01-14 22:00:00','2024-01-14 22:00:00'),('portfolio015','client005','组合015',45000.00,'2024-01-15 23:00:00','2024-01-15 23:00:00'),('portfolio016','client001','组合016',48000.00,'2024-01-16 00:00:00','2024-01-16 00:00:00'),('portfolio017','client002','组合017',50000.00,'2024-01-17 01:00:00','2024-01-17 01:00:00'),('portfolio018','client003','组合018',52000.00,'2024-01-18 02:00:00','2024-01-18 02:00:00'),('portfolio019','client004','组合019',55000.00,'2024-01-19 03:00:00','2024-01-19 03:00:00'),('portfolio020','client005','组合020',58000.00,'2024-01-20 04:00:00','2024-01-20 04:00:00');
/*!40000 ALTER TABLE `portfolios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productvaluehistory`
--

DROP TABLE IF EXISTS `productvaluehistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productvaluehistory` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `item_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品ID',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品类型',
  `date` datetime NOT NULL COMMENT '记录日期',
  `value` double DEFAULT NULL,
  `return_rate` decimal(38,2) DEFAULT NULL,
  `volatility` decimal(38,2) DEFAULT NULL,
  `market_index_value` decimal(38,2) DEFAULT NULL,
  `volume` bigint DEFAULT NULL COMMENT '交易量',
  `source` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_item_id` (`item_id`) USING BTREE,
  KEY `idx_date` (`date`) USING BTREE,
  CONSTRAINT `fk_productvaluehistory_item` FOREIGN KEY (`item_id`) REFERENCES `portfolio_items` (`item_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='产品价值历史表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productvaluehistory`
--

LOCK TABLES `productvaluehistory` WRITE;
/*!40000 ALTER TABLE `productvaluehistory` DISABLE KEYS */;
INSERT INTO `productvaluehistory` VALUES ('pv001','item001','股票','2025-03-22 12:00:00',10.5,0.02,0.05,1500.00,10000,'数据源1'),('pv002','item001','股票','2025-03-23 12:00:00',5.6,0.01,0.03,1550.00,15000,'数据源2'),('pv003','item001','股票','2025-03-24 12:00:00',8.8,0.01,0.02,1600.00,8000,'数据源1'),('pv004','item001','股票','2025-03-25 12:00:00',3.3,-0.01,0.06,1650.00,20000,'数据源2'),('pv005','item001','股票','2025-03-26 12:00:00',6.9,0.02,0.04,1700.00,12000,'数据源1'),('pv006','item001','股票','2025-03-27 12:00:00',10,0.01,0.02,1750.00,7000,'数据源2'),('pv007','item001','股票','2025-03-28 12:00:00',7.4,0.03,0.07,1800.00,22000,'数据源1'),('pv008','item001','股票','2025-03-29 12:00:00',4.2,-0.01,0.04,1850.00,13000,'数据源2'),('pv009','item001','股票','2025-03-30 12:00:00',8.5,0.01,0.03,1900.00,9000,'数据源1'),('pv010','item001','股票','2025-03-31 12:00:00',2.08,-0.02,0.08,1950.00,25000,'数据源2'),('pv011','item002','基金','2025-03-22 12:00:00',12.5,0.02,0.04,1520.00,8000,'数据源1'),('pv012','item002','基金','2025-03-23 12:00:00',6.8,0.01,0.03,1570.00,13000,'数据源2'),('pv013','item002','基金','2025-03-24 12:00:00',9.2,0.01,0.02,1620.00,7000,'数据源1'),('pv014','item002','基金','2025-03-25 12:00:00',3.8,-0.01,0.05,1670.00,18000,'数据源2'),('pv015','item002','基金','2025-03-26 12:00:00',7.5,0.01,0.04,1720.00,10000,'数据源1'),('pv016','item002','基金','2025-03-27 12:00:00',11,0.01,0.01,1770.00,6000,'数据源2'),('pv017','item002','基金','2025-03-28 12:00:00',8,0.02,0.06,1820.00,20000,'数据源1'),('pv018','item002','基金','2025-03-29 12:00:00',4.6,0.00,0.03,1870.00,12000,'数据源2'),('pv019','item002','基金','2025-03-30 12:00:00',9,0.01,0.02,1920.00,8500,'数据源1'),('pv020','item002','基金','2025-03-31 12:00:00',2.54,-0.02,0.07,1970.00,23000,'数据源2'),('pv021','item003','债券','2025-03-22 12:00:00',98.5,0.00,0.01,1480.00,9500,'数据源1'),('pv022','item003','债券','2025-03-23 12:00:00',98.7,0.00,0.01,1490.00,9600,'数据源2'),('pv023','item003','债券','2025-03-24 12:00:00',98.9,0.00,0.01,1500.00,9700,'数据源1'),('pv024','item003','债券','2025-03-25 12:00:00',98.4,0.00,0.01,1510.00,9400,'数据源2'),('pv025','item003','债券','2025-03-26 12:00:00',98.6,0.00,0.01,1520.00,9550,'数据源1'),('pv026','item003','债券','2025-03-27 12:00:00',98.8,0.00,0.01,1530.00,9650,'数据源2'),('pv027','item003','债券','2025-03-28 12:00:00',99,0.00,0.01,1540.00,9750,'数据源1'),('pv028','item003','债券','2025-03-29 12:00:00',98.3,0.00,0.01,1550.00,9350,'数据源2'),('pv029','item003','债券','2025-03-30 12:00:00',98.5,0.00,0.01,1560.00,9500,'数据源1'),('pv030','item003','债券','2025-03-31 12:00:00',69.98,0.00,0.02,1570.00,9200,'数据源2'),('pv031','item003','债券','2025-04-01 12:00:00',7.86,0.00,0.02,1570.00,9200,'数据源2'),('pv032','item001','股票','2025-04-01 12:00:00',0.37,-0.02,0.08,1950.00,25000,'数据源2'),('pv033','item002','基金','2025-04-01 12:00:00',16.71,0.01,0.04,1720.00,10000,'数据源1');
/*!40000 ALTER TABLE `productvaluehistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recommendations`
--

DROP TABLE IF EXISTS `recommendations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recommendations` (
  `recommendation_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `recommendation_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `risk_level` int NOT NULL COMMENT '风险等级',
  `yield_rate` int NOT NULL COMMENT '收益率',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户编号',
  `advisor_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_at` datetime NOT NULL COMMENT '推荐时间',
  `accepted` tinyint(1) DEFAULT '0' COMMENT '是否采纳',
  `feedback` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`recommendation_id`) USING BTREE,
  KEY `idx_client_id` (`client_id`) USING BTREE,
  KEY `idx_advisor_id` (`advisor_id`) USING BTREE,
  CONSTRAINT `fk_recommendations_advisor` FOREIGN KEY (`advisor_id`) REFERENCES `advisors` (`advisor_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_recommendations_client` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='推荐表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommendations`
--

LOCK TABLES `recommendations` WRITE;
/*!40000 ALTER TABLE `recommendations` DISABLE KEYS */;
INSERT INTO `recommendations` VALUES ('3fc7f230-d366-41f0-849a-e8c89ddef952','稳健型组合-20250401',2,37,'client001','advisor001','2025-04-01 01:39:49',0,NULL),('70256411-286d-47bc-9769-edac9261b716','稳健型组合-20250401',2,48,'client013','advisor001','2025-04-01 14:57:58',0,NULL),('rec001','稳健型基金推荐',2,5,'client001','advisor001','2024-01-01 09:00:00',0,NULL),('rec002','高收益股票组合推荐',4,15,'client002','advisor002','2024-01-02 10:00:00',0,NULL),('rec003','债券投资建议',1,3,'client003','advisor003','2024-01-03 11:00:00',0,NULL),('rec004','平衡型投资组合推荐',3,8,'client004','advisor001','2024-01-04 12:00:00',0,NULL),('rec005','低风险货币基金推荐',1,2,'client005','advisor002','2024-01-05 13:00:00',0,NULL),('rec006','新兴产业股票推荐',4,18,'client006','advisor003','2024-01-06 14:00:00',0,NULL),('rec007','定期存款建议',1,3,'client007','advisor001','2024-01-07 15:00:00',0,NULL),('rec008','蓝筹股投资组合推荐',3,10,'client008','advisor002','2024-01-08 16:00:00',0,NULL),('rec009','指数基金推荐',3,7,'client009','advisor003','2024-01-09 17:00:00',0,NULL),('rec010','保守型投资组合推荐',2,4,'client010','advisor001','2024-01-10 18:00:00',0,NULL),('rec011','成长型股票推荐',4,12,'client011','advisor002','2024-01-11 19:00:00',0,NULL),('rec012','债券基金组合推荐',2,6,'client012','advisor003','2024-01-12 20:00:00',0,NULL),('rec013','稳健增长型投资建议',3,9,'client013','advisor001','2024-01-13 21:00:00',0,NULL),('rec014','短期理财产品推荐',1,2,'client014','advisor002','2024-01-14 22:00:00',0,NULL),('rec015','科技股投资组合推荐',4,20,'client015','advisor003','2024-01-15 23:00:00',0,NULL),('rec016','混合型基金推荐',3,9,'client016','advisor001','2024-01-16 00:00:00',0,NULL),('rec017','分红型股票推荐',3,11,'client017','advisor002','2024-01-17 01:00:00',0,NULL),('rec018','保本型理财产品推荐',1,3,'client018','advisor003','2024-01-18 02:00:00',0,NULL),('rec019','价值型股票投资组合推荐',3,11,'client019','advisor001','2024-01-19 03:00:00',0,NULL),('rec020','债券与股票平衡组合推荐',3,10,'client020','advisor002','2024-01-20 04:00:00',0,NULL);
/*!40000 ALTER TABLE `recommendations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recommended_items`
--

DROP TABLE IF EXISTS `recommended_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recommended_items` (
  `item_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `recommendation_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `product_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `product_percent` decimal(38,2) DEFAULT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`item_id`) USING BTREE,
  KEY `idx_recommendation_id` (`recommendation_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='推荐项表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommended_items`
--

LOCK TABLES `recommended_items` WRITE;
/*!40000 ALTER TABLE `recommended_items` DISABLE KEYS */;
INSERT INTO `recommended_items` VALUES ('06087205-ea35-4936-b6e8-fb92e184cf0d','70256411-286d-47bc-9769-edac9261b716','item001','item001',23.33,2333.33),('45e9026f-b60f-40c7-b333-8d4ec8968325','3fc7f230-d366-41f0-849a-e8c89ddef952','item003','item003',70.00,7000.00),('4c6cf074-bbbf-4fa2-be1c-2437bac682fe','70256411-286d-47bc-9769-edac9261b716','item003','item003',35.00,3500.00),('58e92b24-863a-4fce-9356-2d36f8af5896','3fc7f230-d366-41f0-849a-e8c89ddef952','item002','item002',35.00,3500.00),('dcabbcaa-df03-4f69-8aae-e6c4264b71fd','70256411-286d-47bc-9769-edac9261b716','item002','item002',70.00,7000.00),('fdd92c16-6163-469e-a7d6-e3f48f564cae','3fc7f230-d366-41f0-849a-e8c89ddef952','item001','item001',23.33,2333.33),('item001','rec001','P001','稳健型基金 A',0.50,5000.00),('item002','rec001','P002','稳健型基金 B',0.50,5000.00),('item003','rec002','P003','高收益股票 X',0.30,3000.00),('item004','rec002','P004','高收益股票 Y',0.30,3000.00),('item005','rec002','P005','高收益股票 Z',0.40,4000.00),('item006','rec003','P006','债券产品 A',1.00,10000.00),('item007','rec001','P007','平衡型基金 A',0.60,6000.00),('item008','rec004','P008','平衡型基金 B',0.40,4000.00),('item009','rec002','P009','低风险货币基金 A',1.00,10000.00),('item010','rec006','P010','新兴产业股票 A',0.30,3000.00),('item011','rec006','P011','新兴产业股票 B',0.30,3000.00),('item012','rec006','P012','新兴产业股票 C',0.40,4000.00),('item013','rec007','P013','定期存款产品 A',1.00,10000.00),('item014','rec002','P014','蓝筹股 A',0.50,5000.00),('item015','rec008','P015','蓝筹股 B',0.50,5000.00),('item016','rec009','P016','指数基金 A',1.00,10000.00),('item017','rec004','P017','保守型基金 A',0.60,6000.00),('item018','rec010','P018','保守型基金 B',0.40,4000.00),('item019','rec011','P019','成长型股票 A',1.00,10000.00),('item020','rec012','P020','债券基金 A',1.00,10000.00);
/*!40000 ALTER TABLE `recommended_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `risk_assessment`
--

DROP TABLE IF EXISTS `risk_assessment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_assessment` (
  `risk_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `client_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `evaluator_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `remarks` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `result_level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `score` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`risk_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risk_assessment`
--

LOCK TABLES `risk_assessment` WRITE;
/*!40000 ALTER TABLE `risk_assessment` DISABLE KEYS */;
INSERT INTO `risk_assessment` VALUES ('risk001','client001','2023-12-31 16:00:00','advisor001','客户风险承受能力一般，投资经验较少','中风险',50),('risk002','client002','2024-01-01 16:00:00','advisor002','客户有一定的风险认知，投资较为谨慎','低风险',30),('risk003','client003','2024-01-02 16:00:00','advisor003','客户风险偏好较高，投资经验丰富','高风险',80),('risk004','client004','2024-01-03 16:00:00','advisor001','客户对市场波动较敏感，风险承受能力低','低风险',20),('risk005','client005','2024-01-04 16:00:00','advisor002','客户投资目标明确，风险承受适中','中风险',55),('risk006','client006','2024-01-05 16:00:00','advisor003','客户缺乏投资经验，风险偏好保守','低风险',35),('risk007','client007','2024-01-06 16:00:00','advisor001','客户对新投资产品接受度高，风险承受较强','高风险',75),('risk008','client008','2024-01-07 16:00:00','advisor002','客户投资风格稳健，风险承受能力中等','中风险',52),('risk009','client009','2024-01-08 16:00:00','advisor003','客户对风险有一定认识，但投资较为保守','低风险',40),('risk010','client010','2024-01-09 16:00:00','advisor001','客户风险承受能力较强，有长期投资计划','高风险',70),('risk011','client011','2024-01-10 16:00:00','advisor002','客户对市场变化反应较缓，风险偏好低','低风险',25),('risk012','client012','2024-01-11 16:00:00','advisor003','客户投资经验丰富，风险承受能力较高','高风险',85),('risk013','client013','2024-01-12 16:00:00','advisor001','客户风险意识较强，投资决策谨慎','中风险',60),('risk014','client014','2024-01-13 16:00:00','advisor002','客户对风险较为敏感，投资风格保守','低风险',32),('risk015','client015','2024-01-14 16:00:00','advisor003','客户有一定的投资经验，风险承受能力中等','中风险',58),('risk016','client016','2024-01-15 16:00:00','advisor001','客户风险偏好较高，追求较高收益','高风险',78),('risk017','client017','2024-01-16 16:00:00','advisor002','客户对投资风险有清晰认识，风险承受适中','中风险',62),('risk018','client018','2024-01-17 16:00:00','advisor003','客户投资较为谨慎，风险承受能力低','低风险',28),('risk019','client019','2024-01-18 16:00:00','advisor001','客户有较强的风险承受能力，投资策略灵活','高风险',82),('risk020','client020','2024-01-19 16:00:00','advisor002','客户风险偏好较低，注重资金安全','低风险',38);
/*!40000 ALTER TABLE `risk_assessment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `risk_assessment_history`
--

DROP TABLE IF EXISTS `risk_assessment_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_assessment_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `evaluator_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `remarks` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `result_level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `risk_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `score` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_client_id` (`client_id`) USING BTREE,
  KEY `idx_risk_id` (`risk_id`) USING BTREE,
  CONSTRAINT `fk_risk_assessment_history_client` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_risk_assessment_history_risk` FOREIGN KEY (`risk_id`) REFERENCES `risk_assessment` (`risk_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risk_assessment_history`
--

LOCK TABLES `risk_assessment_history` WRITE;
/*!40000 ALTER TABLE `risk_assessment_history` DISABLE KEYS */;
INSERT INTO `risk_assessment_history` VALUES (1,'client001','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor001','客户首次风险评估，风险承受能力一般，投资经验较少','中风险','risk001',50),(2,'client002','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor002','客户首次风险评估，有一定的风险认知，投资较为谨慎','低风险','risk002',30),(3,'client003','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor003','客户首次风险评估，风险偏好较高，投资经验丰富','高风险','risk003',80),(4,'client004','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor001','客户再次评估，对市场波动较敏感，风险承受能力低','低风险','risk004',20),(5,'client005','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor002','客户再次评估，投资目标明确，风险承受适中','中风险','risk005',55),(6,'client006','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor003','客户再次评估，缺乏投资经验，风险偏好保守','低风险','risk006',35),(7,'client007','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor001','客户再次评估，对新投资产品接受度高，风险承受较强','高风险','risk007',75),(8,'client008','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor002','客户再次评估，投资风格稳健，风险承受能力中等','中风险','risk008',52),(9,'client009','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor003','客户再次评估，对风险有一定认识，但投资较为保守','低风险','risk009',40),(10,'client010','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor001','客户再次评估，风险承受能力较强，有长期投资计划','高风险','risk010',70),(11,'client011','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor002','客户再次评估，对市场变化反应较缓，风险偏好低','低风险','risk011',25),(12,'client012','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor003','客户再次评估，投资经验丰富，风险承受能力较高','高风险','risk012',85),(13,'client013','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor001','客户再次评估，风险意识较强，投资决策谨慎','中风险','risk013',60),(14,'client014','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor002','客户再次评估，对风险较为敏感，投资风格保守','低风险','risk014',32),(15,'client015','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor003','客户再次评估，有一定的投资经验，风险承受能力中等','中风险','risk015',58),(16,'client016','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor001','客户再次评估，风险偏好较高，追求较高收益','高风险','risk016',78),(17,'client017','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor002','客户再次评估，对投资风险有清晰认识，风险承受适中','中风险','risk017',62),(18,'client018','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor003','客户再次评估，投资较为谨慎，风险承受能力低','低风险','risk018',28),(19,'client019','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor001','客户再次评估，有较强的风险承受能力，投资策略灵活','高风险','risk019',82),(20,'client020','2025-03-31 14:19:59','2025-03-31 14:19:59','advisor002','客户再次评估，风险偏好较低，注重资金安全','低风险','risk020',38);
/*!40000 ALTER TABLE `risk_assessment_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trades`
--

DROP TABLE IF EXISTS `trades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trades` (
  `trade_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户ID',
  `product_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` enum('申购','赎回','调仓') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `status` enum('待审批','审批中','已拒绝','已执行') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最新状态变更时间',
  PRIMARY KEY (`trade_id`) USING BTREE,
  KEY `client_id` (`client_id`) USING BTREE,
  CONSTRAINT `trades_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='交易表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trades`
--

LOCK TABLES `trades` WRITE;
/*!40000 ALTER TABLE `trades` DISABLE KEYS */;
INSERT INTO `trades` VALUES ('22b5beda1822','client001','P007','申购',6000.00,'待审批','2025-04-01 15:25:28','2025-04-01 15:25:28'),('325d7294bff6','client013','item002','申购',7000.00,'待审批','2025-04-01 14:58:12','2025-04-01 14:58:12'),('3978fe5e495f','client007','P013','申购',10000.00,'待审批','2025-04-01 15:05:30','2025-04-01 15:05:30'),('4885047932db','client001','item001','申购',2333.33,'待审批','2025-04-01 13:23:43','2025-04-01 13:23:43'),('4f37d3bb6ce5','client013','item003','申购',3500.00,'待审批','2025-04-01 14:58:12','2025-04-01 14:58:12'),('4f557efc9832','client001','item002','申购',3500.00,'待审批','2025-04-01 11:55:26','2025-04-01 11:55:26'),('6785358ce8c6','client001','item002','申购',3500.00,'待审批','2025-04-01 13:20:53','2025-04-01 13:20:53'),('6a722ca6f200','client001','item001','申购',2333.33,'待审批','2025-04-01 13:20:53','2025-04-01 13:20:53'),('769906c57a1d','client001','item001','申购',2333.33,'待审批','2025-04-01 11:55:26','2025-04-01 11:55:26'),('77e2dbc1b02d','client007','P013','申购',10000.00,'待审批','2025-04-01 15:07:51','2025-04-01 15:07:51'),('7e352cc1ddf1','client001','P001','申购',5000.00,'待审批','2025-04-01 13:23:04','2025-04-01 13:23:04'),('8641d6c58cff','client001','P002','申购',5000.00,'待审批','2025-04-01 13:23:04','2025-04-01 13:23:04'),('869fcce0f909','client001','P001','申购',5000.00,'待审批','2025-04-01 15:25:28','2025-04-01 15:25:28'),('9933176eaa11','client007','P013','申购',10000.00,'待审批','2025-04-01 15:07:04','2025-04-01 15:07:04'),('9d5b91b5e507','client013','item001','申购',2333.33,'待审批','2025-04-01 14:58:11','2025-04-01 14:58:11'),('aceba4897c9e','client001','P002','申购',5000.00,'待审批','2025-04-01 15:25:28','2025-04-01 15:25:28'),('af3612056648','client001','P007','申购',6000.00,'待审批','2025-04-01 13:23:04','2025-04-01 13:23:04'),('b04dcf8a3062','client001','item002','申购',3500.00,'待审批','2025-04-01 13:23:43','2025-04-01 13:23:43'),('cd2048b4282d','client001','item003','申购',7000.00,'待审批','2025-04-01 11:55:26','2025-04-01 11:55:26'),('cd52eb256d32','client007','P013','申购',10000.00,'待审批','2025-04-01 15:11:05','2025-04-01 15:11:05'),('eb899d027848','client001','item003','申购',7000.00,'待审批','2025-04-01 13:23:43','2025-04-01 13:23:43'),('ebab63838fc9','client007','P013','申购',10000.00,'待审批','2025-04-01 15:01:57','2025-04-01 15:01:57'),('fae96c1d2a8a','client001','item003','申购',7000.00,'待审批','2025-04-01 13:20:52','2025-04-01 13:20:52'),('trade001','client001','P001','申购',1000.00,'已执行','2024-01-01 09:00:00','2025-04-01 14:55:24'),('trade002','client002','P002','赎回',2000.00,'已执行','2024-01-02 10:00:00','2024-01-02 11:00:00'),('trade003','client003','P003','调仓',1500.00,'已执行','2024-01-03 11:00:00','2024-01-03 12:00:00'),('trade004','client004','P004','申购',3000.00,'已执行','2024-01-04 12:00:00','2024-01-04 13:00:00'),('trade005','client005','P005','赎回',2500.00,'已执行','2024-01-05 13:00:00','2024-01-05 14:00:00'),('trade006','client006','P006','调仓',1200.00,'已执行','2024-01-06 14:00:00','2024-01-06 15:00:00'),('trade007','client007','P007','申购',1800.00,'已执行','2024-01-07 15:00:00','2024-01-07 16:00:00'),('trade008','client008','P008','赎回',2200.00,'已执行','2024-01-08 16:00:00','2024-01-08 17:00:00'),('trade009','client009','P009','调仓',1600.00,'已执行','2024-01-09 17:00:00','2024-01-09 18:00:00'),('trade010','client010','P010','申购',2800.00,'已执行','2024-01-10 18:00:00','2024-01-10 19:00:00'),('trade011','client011','P011','赎回',2300.00,'已执行','2024-01-11 19:00:00','2024-01-11 20:00:00'),('trade012','client012','P012','调仓',1400.00,'已执行','2024-01-12 20:00:00','2024-01-12 21:00:00'),('trade013','client013','P013','申购',2100.00,'已执行','2024-01-13 21:00:00','2024-01-13 22:00:00'),('trade014','client014','P014','赎回',2600.00,'已执行','2024-01-14 22:00:00','2024-01-14 23:00:00'),('trade015','client015','P015','调仓',1700.00,'已执行','2024-01-15 23:00:00','2024-01-15 00:00:00'),('trade016','client016','P016','申购',3200.00,'已执行','2024-01-16 00:00:00','2024-01-16 01:00:00'),('trade017','client017','P017','赎回',2400.00,'已执行','2024-01-17 02:00:00','2024-01-17 03:00:00'),('trade018','client018','P018','调仓',1300.00,'已执行','2024-01-18 03:00:00','2024-01-18 04:00:00'),('trade019','client019','P019','申购',1900.00,'已执行','2024-01-19 04:00:00','2024-01-19 05:00:00'),('trade020','client020','P020','赎回',2700.00,'已执行','2024-01-20 05:00:00','2024-01-20 06:00:00');
/*!40000 ALTER TABLE `trades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码哈希（BCrypt 加密）',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `oauth2_provider` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'OAuth2 提供商（如 google, github）',
  `oauth2_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'OAuth2 用户ID',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `identity_level` int DEFAULT NULL COMMENT '身份等级',
  `oauth2id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `oauth2provider` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `phone_number` (`phone_number`) USING BTREE,
  UNIQUE KEY `email` (`email`,`oauth2_provider`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('21f028268ecd','11231322322','e10adc3949ba59abbe56e057f20f883e','11111','222222222@qq.com',NULL,NULL,'正常','2025-04-01 14:51:38','2025-04-01 17:55:45',0,NULL,NULL),('96ebfdeb454c','14543645645','3895e3bf343fe96459ae19272af4b41d','53','44444',NULL,NULL,'正常','2025-04-01 14:53:09','2025-04-01 14:53:09',1,NULL,NULL),('admin001','13800138300','e10adc3949ba59abbe56e057f20f883e','管理孙','admin1@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',5,NULL,NULL),('admin002','13800138301','e10adc3949ba59abbe56e057f20f883e','管理钱','admin2@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',5,NULL,NULL),('admin003','13800138302','e10adc3949ba59abbe56e057f20f883e','管理林','admin3@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',5,NULL,NULL),('admin004','13800138303','e10adc3949ba59abbe56e057f20f883e','管理黄','admin4@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',5,NULL,NULL),('admin005','13800138304','e10adc3949ba59abbe56e057f20f883e','管理高','admin5@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',5,NULL,NULL),('advisor001','13800138021','e10adc3949ba59abbe56e057f20f883e','郑二二','zhengerer@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',2,NULL,NULL),('advisor002','13800138022','e10adc3949ba59abbe56e057f20f883e','王三三','wangsansan@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',2,NULL,NULL),('advisor003','13800138023','e10adc3949ba59abbe56e057f20f883e','李四四','lisisi@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',2,NULL,NULL),('advisor004','13800138024','e10adc3949ba59abbe56e057f20f883e','赵五五','zhaowuwu@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',2,NULL,NULL),('advisor005','13800138025','e10adc3949ba59abbe56e057f20f883e','钱六六','qianliuliu@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',2,NULL,NULL),('c4ea0df91506','11111111111','7fa8282ad93047a4d6fe6111c93b308a','1',NULL,NULL,NULL,'正常','2025-04-01 10:52:14','2025-04-01 10:52:14',1,NULL,NULL),('client001','13800138001','e10adc3949ba59abbe56e057f20f883e','张三','zhangsan@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client002','13800138002','e10adc3949ba59abbe56e057f20f883e','李四','lisi@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client003','13800138003','e10adc3949ba59abbe56e057f20f883e','王五','wangwu@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client004','13800138004','e10adc3949ba59abbe56e057f20f883e','赵六','zhaoliu@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client005','13800138005','e10adc3949ba59abbe56e057f20f883e','孙七','sunqi@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client006','13800138006','e10adc3949ba59abbe56e057f20f883e','周八','zhouba@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client007','13800138007','e10adc3949ba59abbe56e057f20f883e','吴九','wujiu@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client008','13800138008','e10adc3949ba59abbe56e057f20f883e','郑十','zhengshi@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client009','13800138009','e10adc3949ba59abbe56e057f20f883e','王十一','wangshiyi@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client010','13800138010','e10adc3949ba59abbe56e057f20f883e','李十二','lishier@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client011','13800138011','e10adc3949ba59abbe56e057f20f883e','张十三','zhangshisan@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client012','13800138012','e10adc3949ba59abbe56e057f20f883e','刘十四','liushisi@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client013','13800138013','e10adc3949ba59abbe56e057f20f883e','陈十五','chenshiwu@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client014','13800138014','e10adc3949ba59abbe56e057f20f883e','杨十六','yangshiliu@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',2,NULL,NULL),('client015','13800138015','e10adc3949ba59abbe56e057f20f883e','黄十七','huangshiqi@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client016','13800138016','e10adc3949ba59abbe56e057f20f883e','赵十八','zhaoshiba@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client017','13800138017','e10adc3949ba59abbe56e057f20f883e','孙十九','sunshijiu@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client018','13800138018','e10adc3949ba59abbe56e057f20f883e','周二十','zhoushier@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client019','13800138019','e10adc3949ba59abbe56e057f20f883e','吴二一','wueryi@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('client020','13800138020','e10adc3949ba59abbe56e057f20f883e','郑二二','zhengerer@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',1,NULL,NULL),('compliance001','13800138200','e10adc3949ba59abbe56e057f20f883e','合规陈','hegui1@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',4,NULL,NULL),('compliance002','13800138201','e10adc3949ba59abbe56e057f20f883e','合规杨','hegui2@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',4,NULL,NULL),('compliance003','13800138202','e10adc3949ba59abbe56e057f20f883e','合规周','hegui3@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',4,NULL,NULL),('compliance004','13800138203','e10adc3949ba59abbe56e057f20f883e','合规吴','hegui4@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',4,NULL,NULL),('compliance005','13800138204','e10adc3949ba59abbe56e057f20f883e','合规郑','hegui5@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',4,NULL,NULL),('risk001','13800138100','e10adc3949ba59abbe56e057f20f883e','风控张','fengkong1@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',3,NULL,NULL),('risk002','13800138101','e10adc3949ba59abbe56e057f20f883e','风控李','fengkong2@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',3,NULL,NULL),('risk003','13800138102','e10adc3949ba59abbe56e057f20f883e','风控王','fengkong3@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',3,NULL,NULL),('risk004','13800138103','e10adc3949ba59abbe56e057f20f883e','风控赵','fengkong4@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',3,NULL,NULL),('risk005','13800138104','e10adc3949ba59abbe56e057f20f883e','风控刘','fengkong5@example.com',NULL,NULL,'active','2025-03-31 22:08:08','2025-03-31 22:08:08',3,NULL,NULL);
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

-- Dump completed on 2025-04-01 21:49:40
