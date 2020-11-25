package com.appngeek.saas_multi_tenant_demo.dto;


import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.Role;
import com.appngeek.saas_multi_tenant_demo.domain.tenantDomain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

/**
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})**/
@Getter
@Setter
public class UserRegistrationDto {


    private long tenantId;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    @Email
    @NotEmpty
    private String email;
    private User.UserType userType;
    private Role roles;





}
