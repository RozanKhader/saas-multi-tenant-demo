package com.appngeek.saas_multi_tenant_demo.servicess.master;

import com.appngeek.saas_multi_tenant_demo.domain.master.SupportUser;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Privilege;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Role;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.dto.UserRegistrationDto;
import com.appngeek.saas_multi_tenant_demo.repo.master.SupportUserRepository;
import com.appngeek.saas_multi_tenant_demo.repo.tenant.RoleRepository;
import com.appngeek.saas_multi_tenant_demo.repo.tenant.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import static com.appngeek.saas_multi_tenant_demo.Util.Helper.*;

/**
 * Created by Win10 on 11/16/20.
 */
@Component
@Service
@Transactional
@RequiredArgsConstructor
public class SupportUserService   {
    private final SupportUserRepository supportUserRepository;
   // private final RoleRepository roleRepository;
/**
    public User validateUserPos(String posId, String posPass){
        return userRepository.validate(posId,posPass);
    }
    public User validateUserAndPassword(String userName, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User u = userRepository.findByUsername(userName);

        if (u != null) {
                if (compare(password, u.getSalt(), u.getPassword())) {
                 //   this.userRole = lst.get(i).getRoleId();
                    return u;

                }

        }

        return null;
    }
    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }
**/
    public SupportUser save(SupportUser supportUser) {

        try {
            byte[] salt=getSalt();
            System.out.println("salt "+Arrays.toString(salt));

            supportUser.setSalt(salt.toString());
            supportUser.setPassword(hashPassword( supportUser.getPassword(),salt.toString()));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return supportUserRepository.save(supportUser);
    }
/**
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        return collection.stream()
                .map(Privilege::getName)
                .collect(Collectors.toList());
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        return privileges.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


**/

}
