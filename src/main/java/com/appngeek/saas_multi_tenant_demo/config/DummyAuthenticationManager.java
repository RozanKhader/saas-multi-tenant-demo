package com.appngeek.saas_multi_tenant_demo.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by Win10 on 11/16/20.
 */
public class DummyAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Dummy implementation. We don't check anything here.
        return authentication;
    }

}