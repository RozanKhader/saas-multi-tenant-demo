package com.appngeek.saas_multi_tenant_demo.servicess;

import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.config.DatabaseSessionManager;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.CompanyCredential;
import com.appngeek.saas_multi_tenant_demo.repo.CompanyCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Win10 on 12/1/20.
 */
@Service
public class CompanyCredentialService {
    @Autowired
    private CompanyCredentialRepository companyCredentialRepository;

    @Autowired
    private DatabaseSessionManager databaseSessionManager;



    @Transactional
    public List<CompanyCredential> listCompanyCredentials() {

        return companyCredentialRepository.findAllByCompositePK(1);
    }

    @Transactional
    public CompanyCredential createCompanyCredential(CompanyCredential companyCredential) {
        System.out.println("salt "+companyCredential.toString());

        //databaseSessionManager.unbindSession();

       // databaseSessionManager.bindSession();

        CompanyCredential c =  companyCredentialRepository.save(companyCredential);
        if(c.getId() != 0)
        {
           // companiesRepository.updateProfileStatus(c.getCoId());
          //  dashboardReportService.updateCompanyDashboard(companyCredential.getTax());
        }

        return c;
    }
}
