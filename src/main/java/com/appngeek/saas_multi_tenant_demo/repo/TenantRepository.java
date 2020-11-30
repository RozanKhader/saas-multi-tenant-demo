package com.appngeek.saas_multi_tenant_demo.repo;

import com.appngeek.saas_multi_tenant_demo.domain.master.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Win10 on 11/16/20.
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant,Long> {

 public Tenant findByTenantName(String tenantName);
 Tenant findByTenantId(long tenantId);
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
