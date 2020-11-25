package com.appngeek.saas_multi_tenant_demo.controller;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/Customer")

public class CustomerController {

 //   private final CustomerService customerService;
/**
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
**/


    // Get All Orders
@ApiOperation(value = "Return all Customers or based on customer type ")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "The resource not found"),
        @ApiResponse(code = 500, message = "Internal server Error")})
// Get All Orders
@GetMapping
@ApiImplicitParams(@ApiImplicitParam(name="Authorization", defaultValue="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODIiLCJleHAiOjE1MzMyMDAyNjd9.tbvoJNNc-oMK1a6NqqDvUPjOSY41jCP-bZQM0nXvMxs",dataType = "string", paramType = "header"))
@PreAuthorize(("@securityService.hasPrivilege('READ_CUSTOMER')"))
    public ResponseEntity<?> getAllCustomer() {
        System.out.println("Customer");
       return ResponseEntity.ok("hi");

    }

/**
    @ApiOperation(value = "Add new customer to specific company ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 500, message = "Internal server Error")})
    // Add a new Order
    @PostMapping
    @ApiImplicitParams(@ApiImplicitParam(name="Authorization", defaultValue="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODIiLCJleHAiOjE1MzMyMDAyNjd9.tbvoJNNc-oMK1a6NqqDvUPjOSY41jCP-bZQM0nXvMxs",dataType = "string", paramType = "header"))
    @PreAuthorize("hasRole('ADMIN') or hasRole('POS') or hasRole('USER')")

    public ResponseEntity<?> createCustomer(@Valid @RequestBody @Validated(Customer.class) Customer customer, BindingResult result, Model m) throws JsonProcessingException {

        if(result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            return ResponseFormat.responseMessage(CUSTOMER_LOG_TAG, ResponseMessage.FAILED_ADDED_RESPONSE,  fieldError.getDefaultMessage(), BAD_REQUEST.getStatus(),result.getFieldError().getField());
        }
      //Add customer per user with role customer and id =1235
        return ResponseFormat.responseMessage(CUSTOMER_LOG_TAG, ResponseMessage.SUCCESS_ADDED_RESPONSE, new ObjectMapper().writeValueAsString( customerService.createCustomer(customer)), OK.getStatus());


    }


    @ApiOperation(value = "Return  customer object based on id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 500, message = "Internal server Error")})

    @GetMapping("/{customerId}")
    @ApiImplicitParams(@ApiImplicitParam(name="Authorization", defaultValue="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODIiLCJleHAiOjE1MzMyMDAyNjd9.tbvoJNNc-oMK1a6NqqDvUPjOSY41jCP-bZQM0nXvMxs",dataType = "string", paramType = "header"))
    @PreAuthorize(("@securityService.hasPrivilege('READ_CUSTOMER')"))

    public ResponseEntity<?> getCustomerById(@PathVariable(value = "customerId") long customerId) throws JsonProcessingException {
        Customer customer= customerService.getCustomer(customerId);

        if (customer == null)
            throw new ResourceNotFoundException("Customer", "id", customerId);
        return ResponseFormat.responseMessage(CUSTOMER_LOG_TAG,ResponseMessage.SUCCESS_RETURN_RESPONSE_WITH_ID,new ObjectMapper().writeValueAsString( customer), OK.getStatus());

    }

    @ApiOperation(value = "Update  customer object based on id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 500, message = "Internal server Error")})
    // Update an Order
    @PutMapping("/{customerId}")
    @ApiImplicitParams(@ApiImplicitParam(name="Authorization", defaultValue="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODIiLCJleHAiOjE1MzMyMDAyNjd9.tbvoJNNc-oMK1a6NqqDvUPjOSY41jCP-bZQM0nXvMxs",dataType = "string", paramType = "header"))
    @PreAuthorize("hasRole('ADMIN') or hasRole('POS') or hasRole('USER')")

    public ResponseEntity<?> updateCustomer(@PathVariable(value = "customerId") long customerId,
                                            @Valid @RequestBody Customer customerDetails  ) throws JsonProcessingException {
        Customer customer = customerService.getCustomer(customerId);

        if (customer == null)
            throw new ResourceNotFoundException("Customer", "id", customerId);


       customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setGender(customerDetails.getGender());
        customer.setEmail(customerDetails.getEmail());
        customer.setJob(customerDetails.getJob());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());
        customer.setStreet(customerDetails.getStreet());
        customer.setCity(customerDetails.getCity());
        customer.setClub(customerDetails.getClub());
        customer.setHouseNumber(customerDetails.getHouseNumber());
        customer.setCountry(customerDetails.getCountry());
        customer.setCountryCode(customerDetails.getCountryCode());
        customer.setHide(customerDetails.isHide());
        customer.setCustomerType(customerDetails.getCustomerType());


        Customer updateCustomer = customerService.updateCustomer( customer);
        return   ResponseFormat.responseMessage(CUSTOMER_LOG_TAG,ResponseMessage.SUCCESS_UPDATE_RESPONSE,new ObjectMapper().writeValueAsString( updateCustomer), OK.getStatus());
    }

    @ApiOperation(value = "Delete  customer object based on id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 500, message = "Internal server Error")})
    // Delete a Payment
    @DeleteMapping("/{customerId}")
    @ApiImplicitParams(@ApiImplicitParam(name="Authorization", defaultValue="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODIiLCJleHAiOjE1MzMyMDAyNjd9.tbvoJNNc-oMK1a6NqqDvUPjOSY41jCP-bZQM0nXvMxs",dataType = "string", paramType = "header"))
    @PreAuthorize("hasRole('ADMIN') or hasRole('POS') or hasRole('USER')")

    public ResponseEntity<?> deleteCustomer(@PathVariable(value = "customerId") long customerId) throws JsonProcessingException {
        Customer customer=   customerService.getCustomer(customerId);
        if (customer == null)
            throw new ResourceNotFoundException("Customer", "id", customerId);

            customer.setHide(true);
        return ResponseFormat.responseMessage(CUSTOMER_LOG_TAG, ResponseMessage.SUCCESS_DELETE_RESPONSE,new ObjectMapper().writeValueAsString( customerService.deleteCustomer(customer )), OK.getStatus());

    }

    @GetMapping("/{offset}/{count}")
    @ApiImplicitParams(@ApiImplicitParam(name="Authorization", defaultValue="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODIiLCJleHAiOjE1MzMyMDAyNjd9.tbvoJNNc-oMK1a6NqqDvUPjOSY41jCP-bZQM0nXvMxs",dataType = "string", paramType = "header"))
    @PreAuthorize("hasRole('ADMIN') or hasRole('POS') or hasRole('USER')")

    public ResponseEntity<?> getCustomersByOffsetCount(@PathVariable(name = "offset") long offset, @PathVariable(name = "count") long count)
    {
        List<Customer> customers = customerService.getCustomersByOffsetCount(offset, count);
        long objectsCount = customerService.getTotalObjectsCount();
        return ResponseFormat.responseMessageArray(ResponseMessage.CUSTOMER_LOG_TAG, ResponseMessage.SUCCESS_RETURN_RESPONSE,customers.toArray(new Object[customers.size()]), OK.getStatus(), objectsCount);
    }

    @ApiOperation(value = "Add products from excel file")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 500, message = "Internal server Error")})
    //@Consumes("application/vnd.ms-excel")
    @PostMapping("/file")
    @ApiImplicitParams(@ApiImplicitParam(name="Authorization", defaultValue="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODIiLCJleHAiOjE1MzMyMDAyNjd9.tbvoJNNc-oMK1a6NqqDvUPjOSY41jCP-bZQM0nXvMxs",dataType = "string", paramType = "header"))
    //  @RequestMapping(value="/upload", method=RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN') or hasRole('POS') or hasRole('USER')")

    public @ResponseBody
    ResponseEntity<?> handleFileUpload(
            @RequestParam("file") MultipartFile file){

        if (!file.isEmpty()) {
            try {

                InputStream stream = file.getInputStream();
                List<Customer> list=readFile((FileInputStream) stream);
                //System.out.println(new ObjectMapper().writeValueAsString(list));
                //System.out.println("size 2: "+list.size());


                return ResponseFormat.responseMessage(CUSTOMER_LOG_TAG, ResponseMessage.SUCCESS_RETURN_RESPONSE, new ObjectMapper().writeValueAsString(customerService.addRange(list)), OK.getStatus());

            } catch (Exception e) {
                return ResponseFormat.responseMessage(CUSTOMER_LOG_TAG, ResponseMessage.SUCCESS_RETURN_RESPONSE, e.getMessage(), 400);
            }
        } else {
            return ResponseFormat.responseMessage(CUSTOMER_LOG_TAG, ResponseMessage.SUCCESS_RETURN_RESPONSE, "empity", 400);
        }
    }


    public static List<Customer> readFile(FileInputStream fileIn) throws IOException {

       // FileOutputStream fileOut = null;
        HSSFWorkbook wb = null;
        HSSFRow row;
        List<Customer> customerList=new ArrayList<>();
        Customer customer=new Customer();
        try
        {
            POIFSFileSystem fs = new POIFSFileSystem(fileIn);
            wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row0 = sheet.getRow(0);


            for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
                row=sheet.getRow(i);

                if(row0 != null && row != null){

                    if(row.getCell(1)==null){
                        Generators key = new Generators().setLength(9);
                        customer.setCustomerId("921"+key.generateId());
                    }
                    else {
                        customer.setCustomerId(String.valueOf(row.getCell(0)));
                    }

                    customer.setFirstName(String.valueOf(row.getCell(1)));

                    customer.setLastName(String.valueOf(row.getCell(2)));

                    customer.setPhoneNumber(String.valueOf( row.getCell(6)));

                    customer.setEmail(String.valueOf( row.getCell(4)));

                    customer.setStreet(String.valueOf( row.getCell(7)));

                    customer.setCity((int) Double.parseDouble(String.valueOf( row.getCell(9))));
                    customer.setClub((long) Double.parseDouble(String.valueOf( row.getCell(10))));
                    customer.setCountry(String.valueOf( row.getCell(13)));
                    customer.setCountryCode(String.valueOf( row.getCell(14)));
                    customer.setPostalCode(String.valueOf( row.getCell(12)));
                    customer.setHouseNumber(String.valueOf( row.getCell(11)));
                    customer.setGender(String.valueOf( row.getCell(3)));
                    customer.setJob(String.valueOf( row.getCell(5)));
                    customerList.add(customer);

                    customer=new Customer();


                }
                else{
                    System.out.println("Either of rows 0 or 1 is empty");
                }
            }
            return customerList;
        }

        catch (Exception e){

            System.out.println("Exception : "+ e);
        }

        return customerList;

    }**/
}
