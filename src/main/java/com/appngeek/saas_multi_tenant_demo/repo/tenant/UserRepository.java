package com.appngeek.saas_multi_tenant_demo.repo.tenant;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Win10 on 10/22/20.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {


    @Query(value = "SELECT * FROM user WHERE  user_name=? ", nativeQuery = true)
    User findByUsername(String username);
    @Query(value = "SELECT * FROM user WHERE  id=? ", nativeQuery = true)
    User findById(long id);

    @Transactional
    @Query(value = "select MAX(device_id) FROM  user where user_type='POS'", nativeQuery = true)
    Long getMaxPosId();

    @Transactional
    @Query(value = " select * FROM  user where pos_key=?", nativeQuery = true)
    User getByKey(String posKey);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update user  set user_name=?,salt= ?,password= ? where pos_key =? ", nativeQuery = true)
    int updatePos(String name, String salt, String password, String posKey);


    @Transactional
    @Query(value = "SELECT * FROM user WHERE user_name= ? and  password=? ", nativeQuery = true)
    User validate(String posId, String posPass);



}
