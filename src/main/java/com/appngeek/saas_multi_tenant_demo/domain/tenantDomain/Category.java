package com.appngeek.saas_multi_tenant_demo.domain.tenantDomain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Win10 on 1/13/21.
 */


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)

public class Category extends GeneralModel{
    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private long deviceId;
    @Column(columnDefinition="bigint(20) DEFAULT 0")
    private long branchId;
    @Column(nullable = false)
    private String categoryId;
    private String name;
    private long byUser;
    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")

    private boolean hide;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @JsonIgnore
    private Timestamp updatedAt;
}
