package com.appngeek.saas_multi_tenant_demo.domain.tenantDomain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Win10 on 12/1/20.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "company_credentials")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class CompanyCredential {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @Transient
    private long coId;
    private String companyName;
    private String companyID;
    private float tax;
    private String returnNote;
    private int endOfReturnNote;
    private String ccun;
    private String textInDocument;
    @Column(columnDefinition="bigint(20) DEFAULT 0")
    private int branchId;
    private int returnItemsTime;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private  boolean hide;

    private String ccpw;

    private String currencyCode;
    private String currencySymbol;
    private String country;


    @Column( columnDefinition = " default 0")
    private double currencyDifference;
    @Column(nullable = false, columnDefinition = " default null")
    private  String currencyDifferenceType;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Timestamp updatedAt;

    public  String toString(){

        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  "";
    }

}

