package com.appngeek.saas_multi_tenant_demo.controller.tenant;

import com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.CompanyCredential;
import com.appngeek.saas_multi_tenant_demo.servicess.tenant.CompanyCredentialService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.OK;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage.COMPANY_CREDENTIALS_LOG_TAG;

/**
 * Created by Win10 on 12/1/20.
 */
@RestController

public class CompanyCredentialController {
    @Autowired
    private  CompanyCredentialService companyCredentialService;


    // Get All CompanyCredentials
    @GetMapping("/CompanyCredentials")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('POS') or hasRole('USER')")
    public ResponseEntity<?> getAllCompanyCredential() {
        List<CompanyCredential> lst=  companyCredentialService.listCompanyCredentials();
        return  ResponseFormat.responseMessageArray(COMPANY_CREDENTIALS_LOG_TAG, ResponseMessage.SUCCESS_RETURN_RESPONSE,lst.toArray(new Object[lst.size()]), OK.getStatus());

    }


    // Add a new CompanyCredential
    @PostMapping("/CompanyCredentials")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SuperAdmin') or hasRole('USER')")
    public  ResponseEntity<?>  createCompanyCredential(@Valid @RequestBody CompanyCredential CompanyCredential) throws JsonProcessingException {


        TenantContext.setCurrentTenant(CompanyCredential.getCoId());
        return ResponseFormat.responseMessage(COMPANY_CREDENTIALS_LOG_TAG, ResponseMessage.SUCCESS_RETURN_RESPONSE, new ObjectMapper() .writeValueAsString(companyCredentialService.createCompanyCredential(CompanyCredential)), OK.getStatus());


    }
}
