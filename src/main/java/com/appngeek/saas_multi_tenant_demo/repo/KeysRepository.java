package com.appngeek.saas_multi_tenant_demo.repo;

import com.appngeek.saas_multi_tenant_demo.domain.master.TenantKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Win10 on 10/19/20.
 */
@Repository
public interface KeysRepository extends JpaRepository<TenantKeys,Long> {



}
