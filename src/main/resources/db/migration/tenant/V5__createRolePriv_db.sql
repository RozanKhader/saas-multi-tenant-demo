
 CREATE TABLE IF NOT  EXISTS `roles_privileges` (
   `role_id` bigint(20) NOT NULL,
   `privilege_id` bigint(20) NOT NULL,
   KEY `pRKey` (`privilege_id`),
   KEY `rPKey` (`role_id`),
   CONSTRAINT `pRF` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`),
   CONSTRAINT `rPF` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

