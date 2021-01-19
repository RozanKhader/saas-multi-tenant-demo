package com.appngeek.saas_multi_tenant_demo.controller.tenant;

import com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.config.AuthenticationTokenService;
import com.appngeek.saas_multi_tenant_demo.config.DatabaseSessionManager;
import com.appngeek.saas_multi_tenant_demo.domain.Tokens;
import com.appngeek.saas_multi_tenant_demo.domain.master.Tenant;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.dto.LoginDto;
import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.servicess.master.TenantService;
import com.appngeek.saas_multi_tenant_demo.servicess.tenant.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static com.appngeek.saas_multi_tenant_demo.Util.Helper.hashPassword;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.OK;

/**
 * Created by Win10 on 11/16/20.
 */@RestController
@RequiredArgsConstructor

public class LoginController {
    private final AuthenticationManager authManager;
    private final UserService service;
    private final AuthenticationTokenService authenticationTokenService;
    private final TenantService tenantService;
    private  final DatabaseSessionManager databaseSessionManager;
    @PostMapping("/login")
    public ResponseEntity<?> home(@Valid @RequestBody LoginDto loginDto) throws JsonProcessingException, UnsupportedEncodingException, NoSuchAlgorithmException {

            TenantContext.setCurrentTenant((long) 0);

        Tenant tenant=tenantService.findByTenantName(loginDto.getTenantName());

        databaseSessionManager.unbindSession();
        TenantContext.setCurrentTenant(tenant.getTenantId());
        databaseSessionManager.bindSession();


        final User user= service.validateUserAndPassword(loginDto.getUserName(),loginDto.getPassword());


        final Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUserName(),hashPassword(  loginDto.getPassword(),user.getSalt()),new ArrayList<>()

                )

        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Tokens token = authenticationTokenService.issueToken(user.getId(),authentication);


        return ResponseFormat.responseMessage(ResponseMessage.AUTHENTICATION_LOG_TAG, ResponseMessage.VALID_USER, new ObjectMapper().writeValueAsString(token), OK.getStatus());

    }


    }
