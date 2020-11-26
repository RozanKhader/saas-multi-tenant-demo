CREATE TABLE  if NOT EXISTS `company_device` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `company_id` bigint(20) DEFAULT '1',

   `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `device_name` varchar(255) DEFAULT NULL,
   `manufacturer` varchar(255) DEFAULT NULL,
   `purchase_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `running_status` tinyint(1) NOT NULL,
   `serial_number` varchar(255) DEFAULT NULL,
   `status` varchar(255) DEFAULT NULL,
   `type` varchar(255) DEFAULT NULL,
   `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `warranty_long` int(11) NOT NULL,
   `branch_id` bigint(20) DEFAULT '1',
   `inventory_id` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=451381947 DEFAULT CHARSET=utf8