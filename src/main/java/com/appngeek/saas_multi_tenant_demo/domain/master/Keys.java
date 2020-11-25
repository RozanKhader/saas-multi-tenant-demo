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
@Table(name = "keys")
public class Keys {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "current_tenant_id")
    private Long currentTenantId;

    private String key;


    public Keys(Long currentTenantId, String key) {
        this.currentTenantId = currentTenantId;
        this.key = key;
    }
}
