package com.appngeek.saas_multi_tenant_demo.controller.tenant;

import com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Category;
import com.appngeek.saas_multi_tenant_demo.servicess.tenant.CategoryService;
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
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage.CATEGORY_LOG_TAG;

/**
 * Created by Win10 on 12/6/20.
 */

@RestController
@RequestMapping("/Category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    //@PreAuthorize(("@securityService.hasPrivilege('CREATE_PRODUCT')"))
    public ResponseEntity<?> createProduct(@Valid @RequestBody Category category) throws JsonProcessingException, SQLException {

        return  ResponseFormat.responseMessage(CATEGORY_LOG_TAG, ResponseMessage.SUCCESS_ADDED_RESPONSE, categoryService.createCategory(category).toString(), OK.getStatus());

    }

}
