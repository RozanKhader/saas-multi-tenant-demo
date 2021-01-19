package com.appngeek.saas_multi_tenant_demo.repo.master;

import com.appngeek.saas_multi_tenant_demo.domain.master.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Win8.1 on 27/05/2018.
 */
@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "SELECT company_name FROM companies WHERE id= ? ", nativeQuery = true)
    String getCompanyNameFromId(long id);

    @Query(value = "SELECT nickname FROM companies WHERE id= ? ", nativeQuery = true)
    String getCompanyNickNameFromId(long id);

    @Query(value = "SELECT company_name FROM companies WHERE company_name= ? ", nativeQuery = true)
    String validateCompany(String companyName);

    @Query(value = "SELECT id FROM companies WHERE company_name= ? ", nativeQuery = true)
    long getCompanyIdFromName(String companyName);


    @Query(value = "SELECT * FROM companies WHERE companyid = ? ", nativeQuery = true)
    Company getCompanyByCompanyID(long companyid);

    @Query(value = "SELECT * FROM companies WHERE profile_status < 4", nativeQuery = true)
    List<Company> getInCompleteProfileStatusCompanies();

    @Query(value = "select * from companies order by id desc limit :offset, :count", nativeQuery = true)
    List<Company> getCompaniesByOffsetCount(@Param("offset") long offset, @Param("count") long count);

    @Query(value = "select count(*) from companies", nativeQuery = true)
    long getTotalObjectsCount();
    @Transactional
    @Modifying
    @Query(value = "update companies set profile_status = profile_status + 1 where id = ?", nativeQuery = true)
    int updateProfileStatus(long id);

    @Query(value = "SELECT (SELECT COUNT(company_name) FROM companies WHERE company_name = ?) AS name,(SELECT COUNT(companyid) FROM companies WHERE companyid = ?) AS companyId ,(SELECT COUNT(customid) FROM companies WHERE customid = ?) AS custumId", nativeQuery = true)
    List<Object[]> findCompanyConstraints(String name, String companyId, String customId);


    @Query(value = "select max(id) from companies", nativeQuery = true)
    long findLastCompanyId();


}
