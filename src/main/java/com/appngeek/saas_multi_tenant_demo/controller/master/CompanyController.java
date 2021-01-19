package com.appngeek.saas_multi_tenant_demo.controller.master;


import com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.domain.master.Company;
import com.appngeek.saas_multi_tenant_demo.servicess.master.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.EXCEPTION;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.NOT_FOUND;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage.COMPANIES_LOG_TAG;


@RestController
@RequestMapping("/Companies")

public class CompanyController {

    @Autowired
    private CompanyService companyService;





    @PostMapping
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization", defaultValue = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODIiLCJleHAiOjE1MzMyMDAyNjd9.tbvoJNNc-oMK1a6NqqDvUPjOSY41jCP-bZQM0nXvMxs", dataType = "string", paramType = "header"))
    @Secured({"ROLE_SuperAdmin"})
    public ResponseEntity<?> CreateCompany(@RequestBody @Valid Company company, BindingResult bindingResult, Model m) throws JsonProcessingException {


        if (bindingResult.hasErrors())
        {

            FieldError fieldError = bindingResult.getFieldError();
            return ResponseFormat.responseMessage(COMPANIES_LOG_TAG, ResponseMessage.EXCEPTION_MESSAGE, String.valueOf(fieldError.getDefaultMessage()), EXCEPTION.getStatus());

        }
        company.setProfileStatus(1);

        List<String> result=companyService.findCompanyConstraints(company);
        if (result.size()==0){
            Company co = companyService.CreateCompany(company);
           // SyncProcedureService.createProcedure(environment);
            //SyncProcedureService.createAZProcedure(environment);
          //  Generators key = new Generators().setLength(9);
           // companyLicenseService. createDefaultLIcesnse(co.getId());
            //customerRepository.addGuestCustomer(co.getId(), "921" + key.generateId());
            //productService.createGeneralProduct(co.getId());
            //categoryService.createGeneralCategory(co.getId());
            //branchRepository.addDefaultBranch(co.getId());
          //inventoryService.createDefaultInventory(co.getId());
          //dashboardReportService.createCompanyDashboard(co.getId());
       //   Employee masterUser=employeeService.createMasterUser(co.getId());
          //employeePermissionService.createMasterEmployeePermission(masterUser.getEmployeeId(),co.getId());

            return ResponseEntity.ok(co);
        }
        if(result.size() !=0) {
            return ResponseFormat.responseMessage(COMPANIES_LOG_TAG, ResponseMessage.FAILED_ADDED_RESPONSE, result.get(1), NOT_FOUND.getStatus(), result.get(0));
        }
        else
            return ResponseFormat.responseMessage(COMPANIES_LOG_TAG, ResponseMessage.FAILED_ADDED_RESPONSE, result.get(1), EXCEPTION.getStatus(), result.get(0));

    }
/**
    @ApiOperation(value = "get all company to the system by superAdmin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 500, message = "Internal server Error")})
    @GetMapping
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization", defaultValue = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODIiLCJleHAiOjE1MzMyMDAyNjd9.tbvoJNNc-oMK1a6NqqDvUPjOSY41jCP-bZQM0nXvMxs", dataType = "string", paramType = "header"))

    @Secured({"ROLE_SuperAdmin"})
    public ResponseEntity<?> getAllCompanies() {
        List<Company> lst = companyService.listAll();
        return ResponseFormat.responseMessageArray(COMPANIES_LOG_TAG, ResponseMessage.SUCCESS_RETURN_RESPONSE, lst.toArray(new Object[lst.size()]), OK.getStatus());
    }

    @ApiOperation(value = "get  company  by companyId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 500, message = "Internal server Error")})
    @GetMapping("companyId/{companyId}")
    @ApiImplicitParams(@ApiImplicitParam(name = "Authorization", defaultValue = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODIiLCJleHAiOjE1MzMyMDAyNjd9.tbvoJNNc-oMK1a6NqqDvUPjOSY41jCP-bZQM0nXvMxs", dataType = "string", paramType = "header"))

    @Secured({"ROLE_SuperAdmin"})
    public ResponseEntity<?> getCompanyByCompanyId(@PathVariable(name = "companyId") long companyId) {
        return ResponseEntity.ok().body(companyService.getCompanyByCompanyId(companyId));
    }

    @GetMapping("/inCompleteProfileStatus")

    @Secured({"ROLE_SuperAdmin"})
    public ResponseEntity<?> getInCompleteProfileStatusCompanies()
    {
        List<Company> companies =  companyService.getIncompleteProfileStatusCompanies();
        return ResponseFormat.responseMessageArray(COMPANIES_LOG_TAG, ResponseMessage.SUCCESS_RETURN_RESPONSE,companies.toArray(new Object[companies.size()]), OK.getStatus());
    }

    @GetMapping("/{offset}/{count}")
    @Secured({"ROLE_SuperAdmin"})
    public ResponseEntity<?> getCompaniesByOffsetCount(@PathVariable(name = "offset") long offset, @PathVariable(name = "count") long count)
    {
        List<Company> companies = companyService.getCompaniesByOffsetCount(offset, count);
        long objectsCount = companyService.getTotalObjectsCount();
        return ResponseFormat.responseMessageArray(COMPANIES_LOG_TAG, ResponseMessage.SUCCESS_RETURN_RESPONSE,companies.toArray(new Object[companies.size()]), OK.getStatus(), objectsCount);
    }



**/
}
