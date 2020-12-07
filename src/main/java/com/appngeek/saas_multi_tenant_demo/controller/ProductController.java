package com.appngeek.saas_multi_tenant_demo.controller;

import com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Product;
import com.appngeek.saas_multi_tenant_demo.servicess.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.OK;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage.PRODUCT_LOG_TAG;

/**
 * Created by Win10 on 12/6/20.
 */

@RestController
@RequestMapping("/Product")
public class ProductController {
    @Autowired
    private  ProductService productService;

    @PostMapping
    @PreAuthorize(("@securityService.hasPrivilege('CREATE_PRODUCT')"))
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) throws JsonProcessingException {

        return  ResponseFormat.responseMessage(PRODUCT_LOG_TAG, ResponseMessage.SUCCESS_ADDED_RESPONSE, productService.createProduct(product).toString(), OK.getStatus());

    }

}
