package com.appngeek.saas_multi_tenant_demo.config;

import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.servicess.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.appngeek.saas_multi_tenant_demo.Util.Constants.SIGNING_KEY;
import static com.appngeek.saas_multi_tenant_demo.Util.Constants.TOKEN_PREFIX;

/**
 * Created by Win10 on 11/16/20.
 */
@Configuration
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {


        // 1. get the authentication header. Tokens are supposed to be passed in the authentication header
        String header = request.getHeader("Authorization");
        String path = (request).getRequestURI();


        if (!( path.contains("/Users") || path.contains("/CompanyDevices") || path.contains("/initConnection") || path.contains("/authentication/") || path.contains("/authentication/users"))) {
            // 2. validate the header and check the prefix
            if (header == null) {
                chain.doFilter(request, response);        // If not valid, go to the next filter.
                return;
            }

            String token = header.replace(TOKEN_PREFIX, "");

            try {
                Claims claims = getAllClaimsFromToken(token);
                String userId = claims.get("user").toString();

                DecodedJWT jwt = JWT.decode(String.valueOf(token));
                String tenantId = jwt.getSubject();
                String roles = claims.get("roles").toString();

                TenantContext.setCurrentTenant(Long.parseLong(tenantId));
                TenantContext.setCurrentRole(roles);

                String username = userService.findById(Long.parseLong(userId)).getUserName();
                UserDetails userDetails = userService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(header, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
                System.out.println("Exception "+e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

    // go to the next filter in the filter chain
                chain.doFilter(request, response);


}
    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            System.out.println(e.getMessage());

            //LOGGER.error("Could not get all claims Token from passed token");
            claims = null;
        }
        return claims;
    }
    public static long getCompanyIdByToken(String token) {

        DecodedJWT jwt = JWT.decode(String.valueOf(token));
        String id = jwt.getSubject();
        return Long.parseLong(id);

    }


}
