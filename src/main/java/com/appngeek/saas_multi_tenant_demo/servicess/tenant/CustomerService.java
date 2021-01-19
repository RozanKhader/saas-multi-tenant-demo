package com.appngeek.saas_multi_tenant_demo.servicess.tenant;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Customer;
import com.appngeek.saas_multi_tenant_demo.repo.tenant.CustomerRepository;
import com.appngeek.saas_multi_tenant_demo.servicess.AsyncService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by Win10 on 12/6/20.
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AsyncService asyncService;

    //@Transactional
    public Customer createProduct(Customer customer) throws JsonProcessingException, SQLException {
        Customer savedProduct=  customerRepository.save(customer);
       // asyncService.asyncMethodWithVoidReturnType(product.getProductId(),1,1);
        return savedProduct;

    }
}
