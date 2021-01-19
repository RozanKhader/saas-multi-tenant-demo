package com.appngeek.saas_multi_tenant_demo.domain.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Win8.1 on 27/05/2018.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Company implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected long id;
    @NotNull(message = "Company name must be input")
    private String companyName;
    @NotNull(message = "Company ID must be input")
    private String companyID;
    @NotNull(message = "Custom ID must be input")
    private String customID;
    private String displayCompanyName;
    private String creditCardCompanyName;
    @Column( columnDefinition = "INT(11) default 0")
    private Status status;
    private String nickname;
    private String cin;


    private long profileStatus;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @JsonIgnore
    private Timestamp updatedAt;

    public enum Status{
        ACTIVE ("Active "),
        DELETED ("deleted "),PENDING("pending"),LOCKED ("locked ");

        private final String type;

        Status(String type)
        {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }
    }

}
