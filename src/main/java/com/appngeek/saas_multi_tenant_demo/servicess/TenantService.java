package com.appngeek.saas_multi_tenant_demo.servicess;

import com.appngeek.saas_multi_tenant_demo.domain.master.Tenant;
import com.appngeek.saas_multi_tenant_demo.repo.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Win10 on 11/16/20.
 */
@Component
@Service
@Transactional
@RequiredArgsConstructor
public class TenantService {
    @Autowired
    TenantRepository tenantRepository;

    public  Tenant findByTenantName(String tenantName){
        System.out.println("tenantName "+tenantName);

        Tenant tenant= (Tenant) tenantRepository.findByTenantName(tenantName);
        return tenant;
    }
}
