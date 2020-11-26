package com.appngeek.saas_multi_tenant_demo.domain.tenantDomain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Win10 on 11/25/20.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Getter
@Setter
@Table(name = "company_device")
public class CompanyDevice {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private long companyId;
    private String deviceName;
    @Column(columnDefinition="bigint(20) DEFAULT 1")
    private int branchId;
    private String type;
    private String serialNumber;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp purchaseDate;
    private String status;
    private boolean runningStatus;
    private String manufacturer;
    private int warrantyLong;
    private  String inventoryId;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Timestamp updatedAt;

}
