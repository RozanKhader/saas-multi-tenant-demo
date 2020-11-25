package com.appngeek.saas_multi_tenant_demo.domain.master;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Win10 on 10/18/20.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long tenantId;

    @Column(name = "database_name")
    private  String databaseName;

    @Column(name = "tenant_name")
    private  String tenantName;

    @Column(name = "enabled")
    private  boolean enabled;

    public Tenant(long tenantId,String databaseName, String tenantName) {
        this.tenantId=tenantId;
        this.databaseName = databaseName;
        this.tenantName = tenantName;
    }
}
