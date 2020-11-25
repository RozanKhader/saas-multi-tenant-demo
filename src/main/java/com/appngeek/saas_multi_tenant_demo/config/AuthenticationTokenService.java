package com.appngeek.saas_multi_tenant_demo.config;


import com.appngeek.saas_multi_tenant_demo.domain.Tokens;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.repo.UserRepository;
import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.stream.Collectors;

import static com.appngeek.saas_multi_tenant_demo.Util.Constants.SIGNING_KEY;


/**
 * Created by Win8.1 on 11/06/2018.
 */
@Service
public class AuthenticationTokenService {


    public static final String AUTHORITIES_KEY = "scopes";
    final static Key key = MacProvider.generateKey();

    //public final TokenRepository tokenRepository;
    @Autowired
    UserRepository userRepository;


    public Tokens issueToken(long userId, Authentication authentication, String... posPass) throws JsonProcessingException {

        java.util.Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        java.util.Date creationDate = cal.getTime();
        java.sql.Timestamp timestamp1 = new java.sql.Timestamp(creationDate.getTime());
        cal.add(Calendar.HOUR_OF_DAY, 30 * 24);

        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        java.util.Date expirationDate = cal.getTime();
        java.sql.Timestamp timestamp2 = new java.sql.Timestamp(expirationDate.getTime());

        System.out.println("AutharitiesIs   " + authorities);
        String compactJws = "";

        User user =userRepository.findById(userId);
        System.out.print( "roools "+new ObjectMapper().writeValueAsString( user.getRoles()));

        if (authorities.equalsIgnoreCase("ROLE_SuperAdmin")) {

            compactJws = Jwts.builder()
                    .setSubject(String.valueOf(userId)).claim("roles", "SuperAdmin").setExpiration(timestamp2).setIssuer("superAdmin")
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
        } else {


            compactJws = Jwts.builder()
                    .setSubject(String.valueOf(TenantContext.getCurrentTenant())).claim("roles", Iterables.get(user.getRoles(), 0).getName() ).claim("user", userId).setExpiration(timestamp2)
                    .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                    .compact();
        }
      //  System.out.println(compactJws);

        Tokens tok = new Tokens(compactJws, timestamp1, timestamp2);

        /**  Tokens prevToken = tokenRepository.previousToken(userId,companyId);

         if (prevToken != null) {

         tok.setId(prevToken.getId());
         tok.setCompanyId(companyId);

         tokenRepository.save(tok);
         } else {
         tok.setCompanyId(companyId);
         tokenRepository.save(tok);

         }**/

        //   System.out.println("before "+new ObjectMapper().writeValueAsString(tok));
        // return tokenRepository.getToken(compactJws);
        return tok;

    }

    public Tokens issueToken(long companyId, long userId, String... posPass) throws JsonProcessingException {

        java.util.Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        java.util.Date creationDate = cal.getTime();
        java.sql.Timestamp timestamp1 = new java.sql.Timestamp(creationDate.getTime());
        cal.add(Calendar.HOUR_OF_DAY, 30 * 24);

        java.util.Date expirationDate = cal.getTime();
        java.sql.Timestamp timestamp2 = new java.sql.Timestamp(expirationDate.getTime());

        String compactJws = "";

        compactJws = Jwts.builder()
                .setSubject(String.valueOf(companyId)).setExpiration(timestamp2)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        System.out.println();

        Tokens tok = new Tokens(compactJws, timestamp1, timestamp2);
/**
 Tokens prevToken = tokenRepository.previousToken(userId,companyId);

 if (prevToken != null) {

 tok.setId(prevToken.getId());
 tok.setCompanyId(companyId);

 tokenRepository.save(tok);
 } else {
 tok.setCompanyId(companyId);
 tokenRepository.save(tok);

 }**/

        //   System.out.println("before "+new ObjectMapper().writeValueAsString(tok));
        // return tokenRepository.getToken(compactJws);
        return tok;

    }
}