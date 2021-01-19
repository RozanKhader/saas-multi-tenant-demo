CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) DEFAULT '1',
  `by_user` bigint(20) NOT NULL,
  `category_id` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `device_id` bigint(20) NOT NULL,
  `hide` tinyint(1) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u` (`category_id`,`branch_id`),
  UNIQUE KEY `categoryUniqueName` (`branch_id`,`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `category` (`branch_id`,`by_user`,`category_id`,`device_id`,`hide`,`name`) VALUES (0,1,'1',0,0,'general');
