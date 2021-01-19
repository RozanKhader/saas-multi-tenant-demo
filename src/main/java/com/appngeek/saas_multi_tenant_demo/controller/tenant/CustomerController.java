package com.appngeek.saas_multi_tenant_demo.controller.tenant;

import com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Customer;
import com.appngeek.saas_multi_tenant_demo.servicess.tenant.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.SQLException;

import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.OK;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage.PRODUCT_LOG_TAG;

/**
 * Created by Win10 on 12/6/20.
 */

@RestController
@RequestMapping("/Customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
  //  @PreAuthorize(("@securityService.hasPrivilege('CREATE_CUSTOMER')"))
    public ResponseEntity<?> createProduct(@Valid @RequestBody Customer customer) throws JsonProcessingException, SQLException {

        return  ResponseFormat.responseMessage(PRODUCT_LOG_TAG, ResponseMessage.SUCCESS_ADDED_RESPONSE, customerService.createProduct(customer).toString(), OK.getStatus());

    }

}
