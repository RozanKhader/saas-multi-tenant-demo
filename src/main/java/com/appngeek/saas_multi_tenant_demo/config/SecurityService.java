package com.appngeek.saas_multi_tenant_demo.config;

import com.appngeek.saas_multi_tenant_demo.Util.TenantContext;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Privilege;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Role;
import com.appngeek.saas_multi_tenant_demo.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Win10 on 11/19/20.
 */
@Service
@RequiredArgsConstructor
public class SecurityService {
    private final RoleRepository roleRepository;

    public boolean hasPrivilege(String privilege) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        //final HttpSession session = attr.getRequest().getSession(false);
        if (auth != null && auth.isAuthenticated() ) {
            final String currentRole =  TenantContext.getCurrentRole();
            if (currentRole != null) {
                final Role role = roleRepository.findByName(currentRole);
                System.out.println("role "+role.getName());
                return role.getPrivileges()
                        .stream()
                        .map(Privilege::getName)
                        .anyMatch(p -> p.equals(privilege));
            }
        }
        return false;
    }
}
