

CREATE TABLE `privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `privilege` (`id`,`name`) VALUES (1,'CREATE_CUSTOMER');
INSERT INTO `privilege` (`id`,`name`) VALUES (2,'READ_CUSTOMER');
INSERT INTO `privilege` (`id`,`name`) VALUES (3,'CREATE_PRODUCT');
INSERT INTO `privilege` (`id`,`name`) VALUES (4,'READ_PRODUCT');