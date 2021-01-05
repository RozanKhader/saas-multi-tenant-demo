package com.appngeek.saas_multi_tenant_demo.servicess;

import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Product;
import com.appngeek.saas_multi_tenant_demo.repo.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * Created by Win10 on 12/6/20.
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AsyncService asyncService;

    @Transactional
    public Product createProduct(Product product) throws JsonProcessingException, SQLException {
      Product savedProduct=  productRepository.save(product);
        asyncService.asyncMethodWithVoidReturnType(product.getProductId(),1,1);
        return savedProduct;

    }
}
