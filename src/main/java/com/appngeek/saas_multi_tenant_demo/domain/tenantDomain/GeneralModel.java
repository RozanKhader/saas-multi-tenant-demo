package com.appngeek.saas_multi_tenant_demo.domain.tenantDomain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Win10 on 12/6/20.
 */
public class GeneralModel {
    public String toString(){


        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  "";
    }
}
