package com.appngeek.saas_multi_tenant_demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
//@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class UserUpdateDto {
    private long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;
}
