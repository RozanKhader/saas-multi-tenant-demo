
CREATE TABLE  IF NOT  EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT  EXISTS `users_roles` (
   `user_id` bigint(20) NOT NULL,
   `role_id` bigint(20) NOT NULL,
   KEY `rKey` (`role_id`),
   KEY `uKey` (`user_id`),
   CONSTRAINT `uF` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
   CONSTRAINT `rF` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




