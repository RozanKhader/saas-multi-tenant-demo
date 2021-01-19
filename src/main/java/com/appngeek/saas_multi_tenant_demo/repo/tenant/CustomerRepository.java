package com.appngeek.saas_multi_tenant_demo.repo.tenant;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Customer;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Win10 on 12/6/20.
 */
@Repository
@Transactional
public interface CustomerRepository extends MongoRepository<Customer, String> {

}
