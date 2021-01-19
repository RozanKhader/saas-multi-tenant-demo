package com.appngeek.saas_multi_tenant_demo.servicess.tenant;

import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.config.DatabaseSessionManager;
import com.appngeek.saas_multi_tenant_demo.domain.master.Tenant;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Poses;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.dto.LoginDto;
import com.appngeek.saas_multi_tenant_demo.repo.tenant.TenantRepository;
import com.appngeek.saas_multi_tenant_demo.servicess.tenant.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

/**
 * Created by Win10 on 11/30/20.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationEndpointService {

    private final TenantRepository tenantRepository;
    private  final UserService userService;
    private  final DatabaseSessionManager databaseSessionManager;


    public User authenticatePos(Poses pos) throws Exception {

        TenantContext.setCurrentTenant((long)0);
        long companyId = tenantRepository.findByTenantName(pos.getCompanyName()).getTenantId();


        databaseSessionManager.unbindSession();
        TenantContext.setCurrentTenant(companyId);
        databaseSessionManager.bindSession();

        User user=userService.validateUserPos(pos.getPosID(),  pos.getPosPass());
        if (user == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return user;
    }

    public User authenticate(LoginDto loginDto) throws Exception {

        TenantContext.setCurrentTenant((long) 0);

        Tenant tenant=tenantRepository.findByTenantName(loginDto.getTenantName());

        databaseSessionManager.unbindSession();
        TenantContext.setCurrentTenant(tenant.getTenantId());
        databaseSessionManager.bindSession();


        final User user= userService.validateUserAndPassword(loginDto.getUserName(),loginDto.getPassword());

        if (user == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return user;
    }
}
