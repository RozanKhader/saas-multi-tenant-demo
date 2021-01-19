package com.appngeek.saas_multi_tenant_demo.repo.tenant;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * Created by Win10 on 11/19/20.
 */

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Query(value = "SELECT * FROM role WHERE  name in(?) ", nativeQuery = true)
    Collection<Role> findByListRoles(Collection<Role> roleList);

    @Override
    void delete(Role role);

}
