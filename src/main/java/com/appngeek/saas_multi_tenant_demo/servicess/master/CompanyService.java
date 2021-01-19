package com.appngeek.saas_multi_tenant_demo.servicess.master;


import com.appngeek.saas_multi_tenant_demo.domain.master.Company;
import com.appngeek.saas_multi_tenant_demo.repo.master.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService implements ApplicationRunner {


    private CompanyRepository companyRepository;


    @Autowired
    CompanyService(CompanyRepository companyRepository)
    {
        this.companyRepository = companyRepository;
    }

    @Transactional
    public List<String> findCompanyConstraints(Company company)
    {
        List<String> result=new ArrayList<>();
        Object[]  list=companyRepository.findCompanyConstraints(company.getCompanyName(),company.getCompanyID(),company.getCustomID()).get(0);
        if(!list[0].toString().equals("0") ){

            result.add("companyName");
            result.add("Company name is already used.");

        }
        else if(!list[1].toString().equals("0") ){

            result.add("companyId");
            result.add(  "Company ID  is already used.");
        }
        else if(!list[2].toString().equals("0") ){
            result.add("customId");
            result.add(  "Company customID  is already used.");

        }
        return result;
    }


    @Transactional
    public Company CreateCompany(Company company)
    {
        if (company.getNickname()==null)
            company.setNickname(company.getCompanyName().substring(0,2));
      // long maxId= this.companyRepository.findLastCompanyId();
       //company.setId(maxId+1);
        return this.companyRepository.save(company);
    }

    @Transactional
    public List<Company> listAll()
    {
        return  this.companyRepository.findAll();
    }

    @Transactional
    public Company getCompanyByCompanyId(long companyId)
    {
        return this.companyRepository.getCompanyByCompanyID(companyId);
    }
    @Transactional
    public String getCompanyNameByCompanyId(long companyId)
    {
        return companyRepository.getCompanyNameFromId(companyId);
    }
    @Transactional
    public List<Company> getIncompleteProfileStatusCompanies()
    {
        return companyRepository.getInCompleteProfileStatusCompanies();
    }

    @Transactional
    public List<Company> getCompaniesByOffsetCount(long offset, long count)
    {
        return  companyRepository.getCompaniesByOffsetCount(offset, count);
    }

    @Transactional
    public long getTotalObjectsCount()
    {
        return companyRepository.getTotalObjectsCount();
    }


    @Override
    public void run(ApplicationArguments args) {

    }
}
