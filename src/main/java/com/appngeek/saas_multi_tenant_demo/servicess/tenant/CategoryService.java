package com.appngeek.saas_multi_tenant_demo.servicess.tenant;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Category;
import com.appngeek.saas_multi_tenant_demo.repo.tenant.CategoryRepository;
import com.appngeek.saas_multi_tenant_demo.servicess.AsyncService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by Win10 on 12/6/20.
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AsyncService asyncService;

    //@Transactional
    public Category createCategory(Category category) throws JsonProcessingException, SQLException {
        Category savedCategory=  categoryRepository.save(category);
       // asyncService.asyncMethodWithVoidReturnType(product.getProductId(),1,1);
        return savedCategory;

    }
}
