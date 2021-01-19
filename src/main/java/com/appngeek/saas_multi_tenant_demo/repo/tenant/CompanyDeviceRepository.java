package com.appngeek.saas_multi_tenant_demo.repo.tenant;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.CompanyDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Win10 on 11/25/20.
 */
@Repository
@Transactional
public interface CompanyDeviceRepository extends JpaRepository<CompanyDevice,Long> {

    @Query(value = "SELECT * FROM companydevice WHERE  company_id=? ", nativeQuery = true)
    List<CompanyDevice> findAllByCompositePK(long tenant_id);

    @Query(value = "SELECT * FROM companydevice ", nativeQuery = true)
    List<CompanyDevice> findAll();


}
