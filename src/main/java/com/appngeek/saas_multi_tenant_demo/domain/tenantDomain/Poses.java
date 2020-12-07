package com.appngeek.saas_multi_tenant_demo.domain.tenantDomain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Win8.1 on 27/05/2018.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate

public class Poses extends GeneralModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String posID;
    private  String companyName;
    private String posPass;
    private  String key;
    private long deviceId;





}
