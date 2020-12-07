package com.appngeek.saas_multi_tenant_demo.servicess;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Product;
import com.appngeek.saas_multi_tenant_demo.repo.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Win10 on 12/6/20.
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Transactional
    public Product createProduct(Product product) throws JsonProcessingException {
      return  productRepository.save(product);

    }
}
