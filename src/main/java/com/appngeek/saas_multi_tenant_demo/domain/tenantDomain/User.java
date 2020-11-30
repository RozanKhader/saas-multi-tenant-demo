package com.appngeek.saas_multi_tenant_demo.domain.tenantDomain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Win10 on 10/18/20.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends Object {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private long deviceId;


    private String userName;
    private String phoneNumber;
    @Email(message = "Please enter correct email format.")
    private String email;
    private String posKey;
    @Column(columnDefinition="bigint(20) DEFAULT 0")
    private long branchId;

    @Enumerated(EnumType.STRING)
    @Column( columnDefinition = "VARCHAR(255) default 'USER'")
    private UserType userType;

    private String salt;
    private String password;
    private boolean enable;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    @JsonIgnore
    private Timestamp createdAt;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @JsonIgnore
    private Timestamp updatedAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;


    public enum UserType{
        USER("USER"),
        POS("POS"),
        ADMIN("ADMIN");

        private final String type;
        UserType(String type)
        {
            this.type = type;
        }
        public String getType() {
            return this.type;
        }
    }


    public User(String posKey, long deviceId,UserType userType, boolean enable,long branchId) {
        this.posKey = posKey;
        this.deviceId=deviceId;
        this.userType = userType;
        this.enable = enable;
        this.branchId=branchId;
    }

    public  String toString(){

        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  "";
    }


}
