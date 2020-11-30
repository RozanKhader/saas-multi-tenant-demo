package com.appngeek.saas_multi_tenant_demo.controller;

import com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.config.DatabaseSessionManager;
import com.appngeek.saas_multi_tenant_demo.domain.master.TenantKeys;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Credentials;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Poses;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.repo.TenantKeysRepository;
import com.appngeek.saas_multi_tenant_demo.repo.TenantRepository;
import com.appngeek.saas_multi_tenant_demo.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;

import static com.appngeek.saas_multi_tenant_demo.Util.Helper.getSalt;
import static com.appngeek.saas_multi_tenant_demo.Util.Helper.hashPassword;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.INVALID;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.OK;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage.*;

/**
 * Created by Win10 on 11/29/20.
 */
@RestController
@RequiredArgsConstructor
public class InitialConnectionController
{

    private final TenantKeysRepository tenantKeysRepository;
    private final TenantRepository tenantRepository;
    private  final UserRepository userRepository;
    private  final DatabaseSessionManager databaseSessionManager;

    @PostMapping("/initConnection")
    public ResponseEntity<?> validateKey(@Valid @RequestBody Poses pos) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException, SQLException {

        TenantContext.setCurrentTenant((long) 0);

        byte[] salt = getSalt();
        String key=pos.getKey();

        TenantKeys companY = tenantKeysRepository.getCompanyIdFromKey(key);
        System.out.println("companyId "+companY);

        Long companyId=companY.getCurrentTenantId();

        if(companyId!=null){
            String companyName = tenantRepository.findByTenantId(companyId).getTenantName();
            System.out.println("companyName "+companyName);


            databaseSessionManager.unbindSession();
            TenantContext.setCurrentTenant(companyId);
            databaseSessionManager.bindSession();

            System.out.println(pos.toString());
            User user= userRepository.getByKey(key);
            System.out.println(user);

            long deviceId=user.getDeviceId();

            if (companyName == null)
                return ResponseFormat.responseMessage(ResponseMessage.LOG_TAG, NOT_VALID_KEY, pos.toString(),INVALID.getStatus());

            //if pos key is used for more than one time so counter will increase
            if (user.getUserName()!=null) {

                return ResponseFormat.responseMessage(ResponseMessage.LOG_TAG, KEY_ONLY_ONE_TIME,pos.toString(),INVALID.getStatus());

            }

            long posId = user.getId();
            pos.setId(posId);
            System.out.println(Arrays.toString(salt));
            System.out.println(pos.getKey());
            String posPas =  hashPassword(pos.getKey(), salt.toString());
            pos.setPosPass(posPas);


            if( userRepository.updatePos(pos.getPosID(),salt.toString(),pos.getPosPass(),pos.getKey())>=1) {

                Credentials credentials = new Credentials();
                credentials.setCompanyName(companyName);
                credentials.setPosPass(pos.getPosPass());
                credentials.setPosID(pos.getPosID());
                credentials.setSyncNumber(deviceId);
                return ResponseFormat.responseMessage(ResponseMessage.LOG_TAG, VALID_KEY, credentials.toString(),OK.getStatus());
            }
        }
        return ResponseFormat.responseMessage(ResponseMessage.LOG_TAG, NOT_VALID_KEY, pos.toString(),INVALID.getStatus());

    }
}
