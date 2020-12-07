CREATE TABLE `task` (
   `ak` bigint(40) NOT NULL AUTO_INCREMENT,
   `data` longtext,
   `message_type` varchar(255) DEFAULT NULL,
   `branch_id` bigint(20) DEFAULT '1',
   `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `pos_name` bigint(20) NOT NULL,
   `status` int(11) DEFAULT '0',
   `tracking_id` bigint(20) NOT NULL DEFAULT '0',
   `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`ak`),
   KEY `pos_id` (`pos_name`),
   KEY `branch_id` (`branch_id`),
   KEY `staus` (`status`),
   KEY `tracking` (`tracking_id`),
   KEY `syncMess` (`branch_id`,`pos_name`,`message_type`),
   KEY `sync` (`branch_id`,`pos_name`,`tracking_id`,`ak`)
 ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


CREATE TRIGGER After_model_insert
    AFTER INSERT ON products
    FOR EACH ROW
 INSERT INTO task
 SET
     pos_name = NEW.device_id,
     branch_id = OLD.branch_id,
     staus = 0,
      tracking_id=0,
      message_type='AddProduct',
      created_at= NOW(),
      updated_at= NOW();
