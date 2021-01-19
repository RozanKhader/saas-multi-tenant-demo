package com.appngeek.saas_multi_tenant_demo.repo.tenant;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.CompanyCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Win10 on 12/1/20.
 */
    @Repository
    @Transactional
    public interface CompanyCredentialRepository extends JpaRepository<CompanyCredential, Long> {

        @Query(value = "SELECT * FROM company_credentials WHERE   ( branch_id=? or branch_id=0) ", nativeQuery = true)
        List<CompanyCredential> findAllByCompositePK( long branchId);

        @Query(value = "SELECT * FROM company_credentials WHERE  ( branch_id=? or branch_id=0)", nativeQuery = true)
        CompanyCredential findByCompositePK(long branchId);


        @Transactional
        @Modifying(clearAutomatically = true)
        @Query(value = "update company_credentials  set hide=1 where id= ?  ", nativeQuery = true)
        void deleteByCompositePK(long id);

        @Query(value = "SELECT * FROM company_credentials WHERE  (branch_id=? or branch_id=0) ", nativeQuery = true)
        CompanyCredential getCompanyCredentialByCompanyIDAndBranchId(long branch);

    }
