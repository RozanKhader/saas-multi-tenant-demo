package com.appngeek.saas_multi_tenant_demo.controller;

import com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.config.AuthenticationTokenService;
import com.appngeek.saas_multi_tenant_demo.domain.Tokens;
import com.appngeek.saas_multi_tenant_demo.domain.master.Tenant;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Poses;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.dto.LoginDto;
import com.appngeek.saas_multi_tenant_demo.servicess.AuthenticationEndpointService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static com.appngeek.saas_multi_tenant_demo.Util.Helper.hashPassword;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.EXCEPTION;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.OK;

/**
 * Created by Win10 on 11/30/20.
 */

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationEndpoint {

    private  final AuthenticationEndpointService authenticationEndpointService;
    private final AuthenticationManager authManager;
    private final AuthenticationTokenService authenticationTokenService;


    @PostMapping
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Poses pos) {
        try {

            // System.out.println(companyId);
            User user = authenticationEndpointService.authenticatePos(pos);

            System.out.println(hashPassword(pos.getKey(),"[B@29f2b9ac"));
            System.out.println( new UsernamePasswordAuthenticationToken(
                    pos.getPosID(),hashPassword("fzexbppib","[B@29f2b9ac")

            ));

            final Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            pos.getPosID(), hashPassword(user.getPosKey(),user.getSalt())
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Tokens token = authenticationTokenService.issueToken( user.getId(),authentication, pos.getPosPass());


            return ResponseFormat.responseMessage(ResponseMessage.AUTHENTICATION_LOG_TAG, "Token Is Added", token.toString(), OK.getStatus());
        } catch (Exception e) {
            return ResponseFormat.responseMessage(ResponseMessage.AUTHENTICATION_LOG_TAG, e.getMessage(), "", EXCEPTION.getStatus(), pos.getCompanyName(), 0);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> home(@Valid @RequestBody LoginDto loginDto) throws Exception {

        User user = authenticationEndpointService.authenticate(loginDto);


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

