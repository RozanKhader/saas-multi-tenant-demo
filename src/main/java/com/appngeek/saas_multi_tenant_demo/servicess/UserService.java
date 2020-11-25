package com.appngeek.saas_multi_tenant_demo.servicess;

import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Privilege;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Role;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import com.appngeek.saas_multi_tenant_demo.dto.UserRegistrationDto;
import com.appngeek.saas_multi_tenant_demo.repo.RoleRepository;
import com.appngeek.saas_multi_tenant_demo.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

import static com.appngeek.saas_multi_tenant_demo.Util.Helper.compare;
import static com.appngeek.saas_multi_tenant_demo.Util.Helper.hashPassword;

/**
 * Created by Win10 on 11/16/20.
 */
@Component
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


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

    public User save(UserRegistrationDto registration) {
        User user = new User();
        user.setUserName(registration.getUserName());
        user.setEmail(registration.getEmail());
        user.setUserType(registration.getUserType());
        user.setRoles(Collections.singletonList(roleRepository.findByName(registration.getRoles().getName())));


        try {
            byte[] salt=getSalt();
            System.out.println("salt "+Arrays.toString(salt));

            user.setSalt(salt.toString());
            user.setPassword(hashPassword( registration.getPassword(),salt.toString()));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return userRepository.save(user);
    }

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

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }


}
