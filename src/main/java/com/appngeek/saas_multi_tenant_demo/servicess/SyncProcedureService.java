package com.appngeek.saas_multi_tenant_demo.servicess;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.sql.*;

/**
 * Created by Win8.1 on 29/05/2018.
 */
@Service
public class SyncProcedureService {
    @PersistenceContext
    private EntityManager entityManager;


    public void callProcedureCreate(Environment environment, String productId, long deviceId, long baranchId)
    {

        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NEW_sync_pos_product");

            //Declare the parameters in the same order
            query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(3, long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(4, long.class, ParameterMode.IN);

            //Pass the parameter values
            query.setParameter(1, "task");
            query.setParameter(2, productId);
            query.setParameter(3, deviceId);
            query.setParameter(4, baranchId);


            //Execute query
            query.execute();


        } catch (Exception e) {
           System.out.println(e.getMessage());
        }


    }
    public   void callSyncProcedure( long deviceId, long baranchId)
    {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sync_pos");

            //Declare the parameters in the same order
            query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(2, long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(3, long.class, ParameterMode.IN);

            //Pass the parameter values
            query.setParameter(1, "task");
            query.setParameter(2, deviceId);
            query.setParameter(3, baranchId);


            //Execute query
            query.execute();


    } catch (Exception e) {
        System.out.println(e.getMessage());
    }


}

    public  static void createProcedure(Environment environment)
    {

        String url = environment.getProperty("spring.datasource.url");
        String user =  environment.getProperty("spring.datasource.username");
        String pass =  environment.getProperty("spring.datasource.password");

        try {
            Connection conn = DriverManager.getConnection(url + "&user="+user+"&password="+pass);

            String dropProcedure="DROP PROCEDURE IF EXISTS sync_pos;";
            Statement stmt1 = conn.createStatement();
            stmt1.executeUpdate(dropProcedure);


            String storedProc ="CREATE DEFINER=`root`@`localhost` PROCEDURE `sync_pos`(IN pos_table CHAR(20),IN companyId long,IN posNum long,IN branchId long)\n" +
                    "BEGIN\n" +
                    "  DECLARE n INT DEFAULT 0;\n" +
                    "  DECLARE i INT DEFAULT 0;\n" +
                    "  \n" +
                    " SELECT COUNT(*)\n" +
                    " FROM `permission` WHERE company_id=companyId and branch_id=branchId INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select permission_id, name from `permission` WHERE company_id=companyId and branch_id=branchId order by permission_id limit i, 1 into @permission_id, @name;\n" +
                    "      set @dataVar = JSON_OBJECT( 'permissionId',@permission_id ,'name', @name,'branchId',branchId);\n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "    set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "  set @branchId=branchId;\n" +
                    "  set @op = 'AddPermission';\n" +
                    "  \n" +
                    "  \n" +
                    "  prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "  set i = i + 1;\n" +
                    "    end while;\n" +
                    "\n" +
                    "  \n" +
                    "  SELECT COUNT(*)\n" +
                    " FROM `customers` WHERE company_id=companyId and branch_id=branchId INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select customer_id,balance, first_name, last_name , gender, email, job, phone_number, street, city, club, house_number, postal_code, country, country_code,customer_type,customer_code,customer_identity from `customers`  WHERE company_id=companyId and branch_id=branchId order by customer_id limit i, 1\n" +
                    "  into @customerId,@ba, @b, @c , @d, @e, @f, @g, @h, @i, @j, @k, @l, @m, @n,@customerType,@customer_code,@customerIdentity;\n" +
                    "                             \n" +
                    "                             \n" +
                    "    set @dataVar = JSON_OBJECT('customerId',@customerId,'balance',@ba, 'firstName', @b, 'lastName', @c, 'gender',  @d, 'email', @e, 'job', @f, 'phoneNumber', @g ,'street',@h,'city',@i,'club',@j,'houseNumber',@k,'postalCode',@l,'country',@m,'countryCode',@n,'customerType',@customerType,'customerCode',@customer_code,'customerIdentity',@customerIdentity,'branchId',branchId);\n" +
                    "                             \n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "    set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "    set @branchId=branchId;\n" +
                    "\n" +
                    "   set @op = 'AddCustomer';\n" +
                    "   \n" +
                    "   prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "   set i = i + 1;\n" +
                    "  \n" +
                    "  \n" +
                    "   end while;\n" +
                    "   \n" +
                    "\n" +
                    "   SELECT COUNT(*)\n" +
                    " FROM `products` WHERE company_id=companyId and branch_id=branchId INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select  product_code,with_point_system,with_pos,with_tax,bar_code,by_employee, cost_price,category_id, description,product_id, price,display_name,in_stock,manage_stock,regular_price,sale_price,sku,stock_quantity,status,unit from `products` WHERE company_id=companyId and branch_id=branchId order by product_id limit i, 1\n" +
                    " into @product_code,@with_point_system,@with_pos,@with_tax, @bar_code, @by_employee , @cost,  @category_id, @description, @product_id, @price,@display_name,@in_stock,@manage_stock,@regular_price,@sale_price,@sku,@stock_quantity,@status,@unit;\n" +
                    "   set @dataVar = JSON_OBJECT('productId',@product_id,'withPointSystem',@with_point_system,'withPos',@with_pos,'withTax',@with_tax,'barCode', @bar_code, 'byEmployee',  @by_employee, 'costPrice', @cost, 'categoryId', @category_id, 'description', @description, 'productCode', @product_code, 'price', @price, 'displayName',@display_name, 'inStock',@in_stock, 'manageStock',@manage_stock, 'regularPrice',@regular_price, 'salePrice',@sale_price, 'sku',@sku, 'stockQuantity',@stock_quantity, 'status',@status ,'unit',@unit,'branchId',branchId);\n" +
                    " \n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "    set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "      set @branchId=branchId;\n" +
                    "\n" +
                    "  set @op = 'AddProduct';\n" +
                    "  \n" +
                    "  \n" +
                    "  prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "  set i = i + 1;\n" +
                    "  \n" +
                    "  \n" +
                    " end while;\n" +
                    " \n" +
                    " \n" +
                    "  SELECT COUNT(*)\n" +
                    " FROM `club` WHERE company_id=companyId and branch_id=branchId INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select club_id,amount,description,name,percent,point,type from `club` WHERE company_id=companyId and branch_id=branchId order by club_id limit i, 1\n" +
                    "  into @club_id, @c, @e, @f, @g,@w,@e;\n" +
                    "set @dataVar = JSON_OBJECT('clubId',@club_id, 'amount', @c ,'description', @e, 'name', @f, 'percent', @g ,'point',@w,'type',@e,'branchId',branchId);\n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "    set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "        set @branchId=branchId;\n" +
                    "\n" +
                    "  set @op = 'AddClub';\n" +
                    "  \n" +
                    "  \n" +
                    "  prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "  set i = i + 1;\n" +
                    "  \n" +
                    "  \n" +
                    " end while;\n" +
                    " \n" +
                    "  \n" +
                    "  \n" +
                    "  SELECT COUNT(*)\n" +
                    " FROM `employee` WHERE company_id=companyId and branch_id=branchId INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select employee_id,employee_name,present,phone_number,permissions_name,password,last_name,hourly_wage,hide,first_name from `employee` WHERE company_id=companyId and branch_id=branchId order by employee_id limit i, 1\n" +
                    "  into @employee_id, @employee_name, @present, @phone_number, @permissions_name,@password,@last_name,@hourly_wage,@hide,@first_name;\n" +
                    "set @dataVar = JSON_OBJECT('employeeId',@employee_id, 'employeeName', @employee_name ,'present', @present, 'phoneNumber', @phone_number, 'permissionsName', @permissions_name ,'password',@password,'lastName',@last_name,'hourlyWage',@hourly_wage,'hide',@hide,'firstName',@first_name,'branchId',branchId);\n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "    set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "          set @branchId=branchId;\n" +
                    "\n" +
                    "  set @op = 'AddEmployee';\n" +
                    "  \n" +
                    "  \n" +
                    "  prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "  set i = i + 1;\n" +
                    "  \n" +
                    "  \n" +
                    " end while;\n" +
                    "  \n" +
                    "  \n" +
                    " \n" +
                    "  \n" +
                    "  SELECT COUNT(*)\n" +
                    " FROM `category` WHERE company_id=companyId and branch_id=branchId INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select  category_id,by_user,created_at,hide, name  from `category` WHERE company_id=companyId and branch_id=branchId order by  category_id limit i, 1\n" +
                    "  into @category_id, @c , @e,@t,@v;\n" +
                    "  set @dataVar = JSON_OBJECT('categoryId',@category_id, \"byUser\", @c,  'createdAt', @e ,'hide', @t,'name', @v,'branchId',branchId );\n" +
                    "                \n" +
                    "                            \n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "  set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "    set @branchId=branchId;\n" +
                    "  set @op = 'AddCategory';\n" +
                    " \n" +
                    "  \n" +
                    "  prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "  set i = i + 1;\n" +
                    "  \n" +
                    "  \n" +
                    " end while;\n" +
                    " \n" +
                    " \n" +
                    " \n" +
                    " \n" +
                    " SELECT COUNT(*)\n" +
                    " FROM `employee_permission` WHERE company_id=companyId and branch_id=branchId INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select employee_id,employee_permission_id,permission_id  from `employee_permission` WHERE company_id=companyId and branch_id=branchId order by employee_id limit i, 1\n" +
                    "  into @employee_id, @employee_permission_id , @permission_id;\n" +
                    "  set @dataVar = JSON_OBJECT('employeeId',@employee_id, 'employeePermissionId',@employee_permission_id,  'permissionId', @permission_id ,'branchId',branchId);\n" +
                    "                \n" +
                    "                            \n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "  set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "      set @branchId=branchId;\n" +
                    "\n" +
                    "  set @op = 'AddEmployeePermission';\n" +
                    " \n" +
                    "  \n" +
                    "  prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "  set i = i + 1;\n" +
                    "  \n" +
                    "  \n" +
                    " end while;\n" +
                    " \n" +
                    "        # order\n" +
                    " \n" +
                    " \n" +
                    "  \n" +
                    " \n" +
                    "  SELECT COUNT(*)\n" +
                    " FROM `orders` WHERE company_id=companyId and branch_id=branchId INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select order_id,by_user,created_at,customer_id, replacement_note,status,total_paid_amount,total_price, hide,cart_discount  from `orders` WHERE company_id=companyId and branch_id=branchId order by order_id limit i, 1\n" +
                    "  into @order_id,@by_user,@created_at,@customer_id, @replacement_note,@status,@total_paid_amount,@total_price,  @hide,@cart_discount;\n" +
                    "  set @dataVar = JSON_OBJECT('orderId', @order_id, 'byUser', @by_user, 'replacementNote' ,@replacement_note, 'status', @status, 'totalPrice' , @total_price, 'totalPaidAmount', @total_paid_amount, 'customerId', @customer_id,'createdAt',@created_at , 'hide', @hide,'cartDiscount',@cart_discount);\n" +
                    "                \n" +
                    "                            \n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "  set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "      set @branchId=branchId;\n" +
                    "\n" +
                    "  set @op = 'AddOrder';\n" +
                    " \n" +
                    "  \n" +
                    "  prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "  set i = i + 1;\n" +
                    "  \n" +
                    " end while;\n" +
                    " \n" +
                    " \n" +
                    " \n" +
                    " #order details\n" +
                    " \n" +
                    "  \n" +
                    "  SELECT COUNT(*)\n" +
                    " FROM `order_details` WHERE company_id=companyId and branch_id=branchId INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select order_id,order_details_id, discount, paid_amount, product_id, quantity, unit_price, user_offer, hide  from `order_details` WHERE company_id=companyId and branch_id=branchId order by order_details_id limit i, 1\n" +
                    "  into @order_id,@order_details_id, @discount, @paid_amount, @product_id, @quantity, @unit_price, @user_offer, @hide;\n" +
                    "  set @dataVar = JSON_OBJECT('orderId', @order_id, 'orderDetailsId', @order_details_id, 'quantity' ,@quantity, 'userOffer', @user_offer, 'productId' , @product_id, 'unitPrice', @unit_price, 'paidAmount', @paid_amount,'discount',@discount , 'hide', @hide);\n" +
                    "                \n" +
                    "                            \n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "  set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "      set @branchId=branchId;\n" +
                    "\n" +
                    "  set @op = 'AddOrderDetails';\n" +
                    " \n" +
                    "  \n" +
                    "  prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "  set i = i + 1;\n" +
                    "  \n" +
                    " end while;\n" +
                    " \n" +
                    "\n" +
                    " \n" +
                    "\n" +
                    " \n" +
                    "END";


            Statement stmt = conn.createStatement();
            stmt.executeUpdate(storedProc);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public  static void createAZProcedure(Environment environment)
    {

        String url = environment.getProperty("spring.datasource.url");
        String user =  environment.getProperty("spring.datasource.username");
        String pass =  environment.getProperty("spring.datasource.password");

        try {
            Connection conn = DriverManager.getConnection(url + "&user="+user+"&password="+pass);

            String dropProcedure="DROP PROCEDURE IF EXISTS sync_AZposReport;";
            Statement stmt1 = conn.createStatement();
            stmt1.executeUpdate(dropProcedure);


            String storedProc ="CREATE DEFINER=`root`@`localhost` PROCEDURE `sync_AZposReport`(IN pos_table CHAR(20),IN companyId long,IN posNum long,IN branchId long)\n" +
                    "BEGIN\n" +
                    "  DECLARE n INT DEFAULT 0;\n" +
                    "  DECLARE i INT DEFAULT 0;\n" +
                    "  \n" +
                    " \n" +
                    "  \n" +
                    " \n" +
                    " \n" +
                    " \n" +
                    " SELECT COUNT(*)\n" +
                    " FROM `z_report` WHERE company_id=companyId and branch_id=branchId and device_id=posNum INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select amount,by_user,end_order_id,hide,start_order_id,total_amount,z_report_id from `z_report` WHERE company_id=companyId and branch_id=branchId and device_id=posNum order by z_report_id  limit i, 1\n" +
                    "  into @amount, @by_user , @end_order_id,@hide,@start_order_id,@total_amount,@z_report_id ;\n" +
                    "  \n" +
                    "  \n" +
                    "  set @dataVar = JSON_OBJECT('amount',@amount, 'byUser',@by_user,  'endOrderId',  @end_order_id,'hide', @hide,'startOrderId', @start_order_id,'totalAmount',@total_amount,'zReportId',@z_report_id );\n" +
                    "                \n" +
                    "                            \n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "  set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "      set @branchId=branchId;\n" +
                    "  set @op = 'AddZReport';\n" +
                    " \n" +
                    "  \n" +
                    "  prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "  set i = i + 1;\n" +
                    "  \n" +
                    "  \n" +
                    " end while;\n" +
                    "  SELECT COUNT(*)\n" +
                    " FROM `a_report` WHERE company_id=companyId and branch_id=branchId and device_id=posNum INTO n;\n" +
                    "  SET i = 0;\n" +
                    "  \n" +
                    "  WHILE i < n DO\n" +
                    "  \n" +
                    "  select a_report_id,amount,by_userid,hide,last_order_id from `a_report` WHERE company_id=companyId and branch_id=branchId and device_id=posNum order by a_report_id limit i, 1\n" +
                    "  into @a_report_id, @amount , @by_userid,@hide,@last_order_id ;\n" +
                    "  \n" +
                    "  \n" +
                    "  set @dataVar = JSON_OBJECT('aReportId',@a_report_id, 'amount',@amount,  'byUserID', @by_userid,'hide', @hide,'lastOrderId', @last_order_id);\n" +
                    "                \n" +
                    "                            \n" +
                    "  set @sql_stmt = concat('insert into ', pos_table, '(company_id,data, message_type, pos_name,branch_id) VALUES (?,?, ?, ?,?)'); \n" +
                    "  \n" +
                    "  set @companyId=companyId;\n" +
                    "  set @posNum=posNum;\n" +
                    "      set @branchId=branchId;\n" +
                    "  set @op = 'AddAReport';\n" +
                    " \n" +
                    "  \n" +
                    "  prepare stmt from @sql_stmt; \n" +
                    "  \n" +
                    "  execute stmt using  @companyId, @dataVar,@op,@posNum,@branchId;\n" +
                    "  \n" +
                    "  set i = i + 1;\n" +
                    "  \n" +
                    "  \n" +
                    " end while;\n" +
                    "\n" +
                    " \n" +
                    " \n" +
                    " \n" +
                    "END";


            Statement stmt = conn.createStatement();
            stmt.executeUpdate(storedProc);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
