package com.appngeek.saas_multi_tenant_demo.Util;

public class TenantContext {
	
    private static ThreadLocal<Long> currentTenant = new ThreadLocal<>();
    private static ThreadLocal<String> currentRole = new ThreadLocal<>();

    public static void setCurrentTenant(Long tenantId) {
        currentTenant.set(tenantId);
    }

    public static Long getCurrentTenant() {
        return currentTenant.get();
    }

    public static String getCurrentRole() {
         return currentRole.get();
    }

    public static void setCurrentRole(String role) {
        currentRole.set(role);
    }

    public static void clear() {
        currentTenant.set(null);
    }
}