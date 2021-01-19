package com.appngeek.saas_multi_tenant_demo.controller.tenant;


import com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.dto.UserRegistrationDto;
import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.servicess.tenant.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.OK;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage.LEAD_POS_USERS_LOG_TAG;

/**
 * Created by Win10 on 10/28/20.
**/

    @RestController
    @RequestMapping("/Users")

    public class UsersController {

    @Autowired
        private UserService userService;
    @PostMapping()
       // @PreAuthorize(("@securityService.hasPrivilege('CREATE_USERS')"))
        public ResponseEntity<?> createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto, BindingResult result, Model m) throws JsonProcessingException {
            TenantContext.setCurrentTenant(userRegistrationDto.getTenantId());
            System.out.println(TenantContext.getCurrentTenant());

            User user= userService.save(userRegistrationDto);
            return  ResponseFormat.responseMessage(LEAD_POS_USERS_LOG_TAG, ResponseMessage.SUCCESS_RETURN_RESPONSE,new ObjectMapper().writeValueAsString(user), OK.getStatus());
        }

    }
