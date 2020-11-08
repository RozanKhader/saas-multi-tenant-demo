package com.appngeek.saas_multi_tenant_demo.config.database;

import javax.sql.DataSource;

import com.appngeek.saas_multi_tenant_demo.repo.util.TenantContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TenantDatabaseConfig {

	@Bean(name = "tenantAwareDataSource")
	@Primary
	public DataSource tenantAwareDataSource() {
		System.out.println("hi3 "+ TenantContext.getCurrentTenant());

		return new TenantAwareDataSource();
	}

}