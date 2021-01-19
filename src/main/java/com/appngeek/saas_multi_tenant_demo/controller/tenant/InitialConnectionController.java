package com.appngeek.saas_multi_tenant_demo.controller.tenant;

import com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.config.DatabaseSessionManager;
import com.appngeek.saas_multi_tenant_demo.domain.master.TenantKeys;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.CompanyDevice;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Credentials;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Poses;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.repo.tenant.TenantKeysRepository;
import com.appngeek.saas_multi_tenant_demo.repo.tenant.TenantRepository;
import com.appngeek.saas_multi_tenant_demo.repo.tenant.UserRepository;
import com.appngeek.saas_multi_tenant_demo.servicess.tenant.CompanyDevicesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * Created by Win10 on 11/25/20.
     */
    @RestController
    @RequestMapping("/CompanyDevices")
    public static class CompanyDeviceController {
        @Autowired
        private CompanyDevicesService companyDevicesService;
        @Autowired
        private   DatabaseSessionManager databaseSessionManager;

        @ApiOperation(value = "Add new  company device for a specific company")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "OK"),
                @ApiResponse(code = 404, message = "The resource not found"),
                @ApiResponse(code = 500, message = "Internal server Error")})
        @PostMapping
        public ResponseEntity<?> addCompanyDevice(@Valid @RequestBody CompanyDevice companyDevice, HttpServletResponse response) throws SQLException, InterruptedException, JsonProcessingException {

            if(companyDevice.getType().equalsIgnoreCase(User.UserType.POS.getType())){
                System.out.println(new ObjectMapper().writeValueAsString(companyDevice));

                TenantContext.setCurrentTenant(companyDevice.getCompanyId());
                companyDevicesService.save(companyDevice);


                return  companyDevicesService.savePos(companyDevice,response);

            }
            return  ResponseEntity.ok().body(companyDevicesService.save(companyDevice));

        }
    }
}
