DELIMITER //

 CREATE DEFINER=`root`@`localhost` PROCEDURE `sync_pos`(IN pos_table CHAR(20),IN posNum long,IN branchId long)
BEGIN
  DECLARE n INT DEFAULT 0;
  DECLARE i INT DEFAULT 0;
  SELECT COUNT(*)
 FROM `products` WHERE ( branch_id=branchId or branch_id=0 ) INTO n;
  SET i = 0;

  WHILE i < n DO

  select  product_code,with_point_system,with_pos,with_tax,bar_code,by_employee, cost_price,category_id, description,product_id, price_with_tax,display_name,in_stock,manage_stock,regular_price,sale_price,sku,stock_quantity,status,unit,branch_id,offer_id,last_cost_price_inventory ,manual_cost_price,with_serial_number,currency_type,price_with_out_tax from `products` WHERE  (branch_id=branchId  or branch_id=0  ) order by product_id limit i, 1
 into @product_code,@with_point_system,@with_pos,@with_tax, @bar_code, @by_employee , @cost,  @category_id, @description, @product_id, @price,@display_name,@in_stock,@manage_stock,@regular_price,@sale_price,@sku,@stock_quantity,@status,@unit,@branch_id,@offer_id,@last_cost_price_inventory,@manual_cost_price,@with_serial_number,@currency_type,@priceWithOutTax;
   set @dataVar = JSON_OBJECT('productId',@product_id,'withPointSystem',@with_point_system,'withPos',@with_pos,'withTax',@with_tax,'barCode', @bar_code, 'byEmployee',  @by_employee, 'costPrice', @cost, 'categoryId', @category_id, 'description', @description, 'productCode', @product_code, 'priceWithTax', @price, 'displayName',@display_name, 'inStock',@in_stock, 'manageStock',@manage_stock, 'regularPrice',@regular_price, 'salePrice',@sale_price, 'sku',@sku, 'stockQuantity',@stock_quantity, 'status',@status ,'unit',@unit,'branchId',@branch_id,'offerId',@offer_id,'lastCostPriceInventory',@last_cost_price_inventory,'manualCostPrice',@manual_cost_price,'withSerialNumber',@with_serial_number,'currencyType',@currency_type,'priceWithOutTax',@priceWithOutTax);

  set @sql_stmt = concat('insert into ', pos_table, '(data, message_type, pos_name,branch_id) VALUES (?, ?, ?,?)');


  set @posNum=posNum;
      set @branchId=branchId;

  set @op = 'AddProduct';


  prepare stmt from @sql_stmt;

  execute stmt using   @dataVar,@op,@posNum,@branchId;

  set i = i + 1;


 end while;


END //
DELIMITER ;


DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `NEW_sync_pos_product`(IN pos_table CHAR(20),IN productId long,IN branchId long,IN posId long)
BEGIN
  DECLARE n INT DEFAULT 0;
  DECLARE i INT DEFAULT 0;

DECLARE k INT DEFAULT 0;
  DECLARE j INT DEFAULT 0;
  select COUNT(*) FROM  user where user_type='POS'  into k ;
  set j=1;
  loop4:  WHILE j <= k DO
   select j;
  if (j=posId) THEN
  set j=j+1;
  iterate loop4 ;
  end if;




  select  product_code,with_point_system,with_pos,with_tax,bar_code,by_employee, cost_price,category_id, description,product_id, price_with_tax,display_name,in_stock,manage_stock,regular_price,sale_price,sku,stock_quantity,status,unit,branch_id,offer_id,last_cost_price_inventory ,manual_cost_price,with_serial_number,currency_type,price_with_out_tax from `products` WHERE product_id=productId  and (branch_id=branchId  or branch_id=0  )
 into @product_code,@with_point_system,@with_pos,@with_tax, @bar_code, @by_employee , @cost,  @category_id, @description, @product_id, @price,@display_name,@in_stock,@manage_stock,@regular_price,@sale_price,@sku,@stock_quantity,@status,@unit,@branch_id,@offer_id,@last_cost_price_inventory,@manual_cost_price,@with_serial_number,@currency_type,@priceWithOutTax;
   set @dataVar = JSON_OBJECT('productId',@product_id,'withPointSystem',@with_point_system,'withPos',@with_pos,'withTax',@with_tax,'barCode', @bar_code, 'byEmployee',  @by_employee, 'costPrice', @cost, 'categoryId', @category_id, 'description', @description, 'productCode', @product_code, 'priceWithTax', @price, 'displayName',@display_name, 'inStock',@in_stock, 'manageStock',@manage_stock, 'regularPrice',@regular_price, 'salePrice',@sale_price, 'sku',@sku, 'stockQuantity',@stock_quantity, 'status',@status ,'unit',@unit,'branchId',@branch_id,'offerId',@offer_id,'lastCostPriceInventory',@last_cost_price_inventory,'manualCostPrice',@manual_cost_price,'withSerialNumber',@with_serial_number,'currencyType',@currency_type,'priceWithOutTax',@priceWithOutTax);

  set @sql_stmt = concat('insert into ', pos_table, '(data, message_type, pos_name,branch_id) VALUES (?, ?, ?,?)');


  set @posNum=j;
      set @branchId=branchId;

  set @op = 'AddProduct';


  prepare stmt from @sql_stmt;

  execute stmt using   @dataVar,@op,@posNum,@branchId;



  set j = j + 1;
   end while;
END //
DELIMITER ;

