package com.appngeek.saas_multi_tenant_demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginDto {

    @NotEmpty
    private String tenantName;
    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;


}
