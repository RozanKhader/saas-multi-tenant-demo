package com.appngeek.saas_multi_tenant_demo.repo;

import com.appngeek.saas_multi_tenant_demo.domain.master.TenantKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Win10 on 10/19/20.
 */
@Repository
public interface TenantKeysRepository extends JpaRepository<TenantKeys,Long> {

    @Transactional
    @Query(value = "SELECT * FROM tenant_keys where pos_key = ? ", nativeQuery = true)
    TenantKeys getCompanyIdFromKey(String key);

}
