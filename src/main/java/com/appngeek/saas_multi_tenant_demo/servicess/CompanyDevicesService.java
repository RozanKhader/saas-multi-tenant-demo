package com.appngeek.saas_multi_tenant_demo.servicess;

import com.appngeek.saas_multi_tenant_demo.Util.Generators;
import com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage;
import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.config.DatabaseSessionManager;
import com.appngeek.saas_multi_tenant_demo.domain.master.TenantKeys;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.CompanyDevice;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Poses;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.repo.CompanyDeviceRepository;
import com.appngeek.saas_multi_tenant_demo.repo.KeysRepository;
import com.appngeek.saas_multi_tenant_demo.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.ResponseStatus.OK;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseFormat.responseMessage;
import static com.appngeek.saas_multi_tenant_demo.Util.ResponseMessage.COMPANIES_DEVICE_LOG_TAG;
import static com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User.UserType.POS;

/**
 * Created by Win10 on 11/25/20.
 */
@Service
public class CompanyDevicesService {
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private KeysRepository keysRepository;
    @Autowired
    private CompanyDeviceRepository companyDeviceRepository;
    @Autowired
    private   DatabaseSessionManager databaseSessionManager;

    public CompanyDevice save(CompanyDevice companyDevice){
        return  companyDeviceRepository.save(companyDevice);
    }
    public ResponseEntity<?> savePos(CompanyDevice companyDevice, HttpServletResponse response) throws SQLException, JsonProcessingException, InterruptedException {

        long deviceId = 0;
        TenantKeys tenantKeys = null;
        Poses pos=new Poses();
        Generators newKey = new Generators().setLength(6);
        pos.setKey(newKey.generate());
        tenantKeys = new TenantKeys(companyDevice.getCompanyId(), pos.getKey());




        Long maxPosId = usersRepository.getMaxPosId();
        if (maxPosId != null) {
            deviceId = maxPosId;
        }


        if (deviceId == 0) {//this mean that this poss is the first user with type poss
            deviceId = 1;

        } else {
            deviceId = deviceId + 1;
            //counter for every poss
        }


        pos.setDeviceId(deviceId);
        User users = new User(pos.getKey(), deviceId, POS, true, companyDevice.getBranchId());


        System.out.println(new ObjectMapper().writeValueAsString(users));


        User resultUser = usersRepository.save(users);
        //  inventoryService.updateInventoryAddDevice(inventoryId,deviceId,companyId,branchId);

        //usersRepository.post(resultUser.getId(),id);

        //SyncMessagesPosController syncMessagesPosController=new SyncMessagesPosController(syncPosMessageRepository);

        // syncMessagesPosController.insertNewSyncMessages(companyId,deviceId);



    //   TenantContext.setCurrentTenant(tenant.getTenantId());

        databaseSessionManager.unbindSession();
        TenantContext.setCurrentTenant((long) 0);
        databaseSessionManager.bindSession();
        keysRepository.save(tenantKeys);

/**
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            System.out.println("   Exception1  " + e.getMessage());
        }**/

        // asyncClass.asyncMethodWithVoidReturnType(environment, syncProcedureService, syncMongoDataService, companyId, deviceId, branchId);

        // out.write(aByteArray);
        return responseMessage(COMPANIES_DEVICE_LOG_TAG, ResponseMessage.SUCCESS_ADDED_RESPONSE, new ObjectMapper().writeValueAsString(resultUser), OK.getStatus());

    }

}