package com.appngeek.saas_multi_tenant_demo.repo;

import com.appngeek.saas_multi_tenant_demo.domain.master.Keys;
import com.appngeek.saas_multi_tenant_demo.domain.master.Tenant;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Created by Win10 on 11/16/20.
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant,Long> {

 public Tenant findByTenantName(String tenantName);
   // @Autowired
  //  final JdbcTemplate jdbcTemplate;

   /**  TenantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public  Tenant findByTenantName(String tenantName){
        return  (Tenant) jdbcTemplate.queryForObject(" select * from tenant where tenant_name =?;",
                new Object[]{tenantName}, new BeanPropertyRowMapper(Tenant.class));


    }**/

}
