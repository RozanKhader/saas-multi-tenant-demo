package com.appngeek.saas_multi_tenant_demo.domain.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;

/**
 * Created by Win10 on 1/14/21.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Getter
@Setter
@Table(name = "support_user")
public class SupportUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String userName;

    @Email(message = "Please enter correct email format.")
    private String email;
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

}
