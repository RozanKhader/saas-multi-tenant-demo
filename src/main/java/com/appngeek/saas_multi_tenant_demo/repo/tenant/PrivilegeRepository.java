package com.appngeek.saas_multi_tenant_demo.repo.tenant;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Win10 on 11/19/20.
 */

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
