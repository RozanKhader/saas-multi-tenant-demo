CREATE TABLE  IF NOT EXISTS `tenant_keys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `current_tenant_id` bigint(20) DEFAULT NULL,
  `pos_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8