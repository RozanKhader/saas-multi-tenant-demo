package com.appngeek.saas_multi_tenant_demo.controller;

import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.config.DatabaseSessionManager;
import com.appngeek.saas_multi_tenant_demo.domain.master.Tenant;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.CompanyDevice;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.repo.CompanyDeviceRepository;
import com.appngeek.saas_multi_tenant_demo.servicess.CompanyDevicesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLException;

/**
 * Created by Win10 on 11/25/20.
 */
@RestController
@RequestMapping("/CompanyDevices")
public class CompanyDeviceController {
    @Autowired
    private  CompanyDevicesService companyDevicesService;
    @Autowired
    private   DatabaseSessionManager databaseSessionManager;

    @ApiOperation(value = "Add new  company device for a specific company")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 500, message = "Internal server Error")})
    @PostMapping
    public ResponseEntity<?> addCompanyDevice(@Valid @RequestBody CompanyDevice companyDevice, HttpServletResponse response) throws SQLException, InterruptedException, JsonProcessingException {

        if(companyDevice.getType().equalsIgnoreCase(User.UserType.POS.getType())){
            System.out.println(new ObjectMapper().writeValueAsString(companyDevice));

            TenantContext.setCurrentTenant(companyDevice.getCompanyId());
            companyDevicesService.save(companyDevice);


            return  companyDevicesService.savePos(companyDevice,response);

        }
        return  ResponseEntity.ok().body(companyDevicesService.save(companyDevice));

    }
}
