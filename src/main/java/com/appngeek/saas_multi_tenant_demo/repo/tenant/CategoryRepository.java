package com.appngeek.saas_multi_tenant_demo.repo.tenant;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Category;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Win10 on 12/6/20.
 */
@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
