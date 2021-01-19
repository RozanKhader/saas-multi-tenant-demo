package com.appngeek.saas_multi_tenant_demo.repo.master;

import com.appngeek.saas_multi_tenant_demo.domain.master.SupportUser;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Win10 on 10/22/20.
 */
@Repository
@Transactional
public interface SupportUserRepository extends JpaRepository<SupportUser,Long> {


}

