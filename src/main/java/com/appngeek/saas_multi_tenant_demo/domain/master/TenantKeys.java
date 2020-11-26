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
@Table(name = "tenant_keys")
public class TenantKeys {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "current_tenant_id")
    private Long currentTenantId;

    private String posKey;


    public TenantKeys(Long currentTenantId, String posKey) {
        this.currentTenantId = currentTenantId;
        this.posKey = posKey;
    }
}
