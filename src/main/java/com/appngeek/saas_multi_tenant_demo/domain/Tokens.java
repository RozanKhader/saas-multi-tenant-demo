package com.appngeek.saas_multi_tenant_demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Win8.1 on 23/05/2018.
 */

@Getter
@Setter


public class Tokens {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String token;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp creationDate;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp expirationDate;
    private long userId;


    public Tokens(String token, Timestamp creationDate, Timestamp expirationDate) {
        this.token=token;
        this.creationDate=creationDate;
        this.expirationDate=expirationDate;

    }
}
