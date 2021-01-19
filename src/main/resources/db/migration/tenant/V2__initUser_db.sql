

CREATE TABLE if not EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_name` varchar(255) DEFAULT NULL,
  `device_id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enable` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `pos_key` varchar(255) DEFAULT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `salt` varchar(255) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_type` varchar(255) DEFAULT NULL,
  `branch_id` bigint(20) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `device_id` (`device_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;