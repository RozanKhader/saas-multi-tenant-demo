package com.appngeek.saas_multi_tenant_demo.domain.tenantDomain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)

public class Product extends GeneralModel{
    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    @JsonIgnore
    private long deviceId;
    @Column(columnDefinition="bigint(20) DEFAULT 0")
    private long branchId;
    @NotBlank
    private String productCode;

    private String displayName;
    @NotBlank
    private String barCode;
    @NotNull
    private String sku;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column( columnDefinition = "VARCHAR(255) default 'QUANTITY'")
    private ProductUnit unit;
    private Double priceWithTax;
    private Double costPrice;
    private Double priceWithOutTax ;

    private Long categoryId;
    @JsonIgnoreProperties
    @Enumerated(EnumType.STRING)
    @Column( columnDefinition = "VARCHAR(255) default 'ACTIVE'")
    private ProductStatus status;
    private Long byEmployee;
    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean inStock;
    private double regularPrice;
    private int stockQuantity;
    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean manageStock;
    private double salePrice;
    @Column(columnDefinition="DOUBLE DEFAULT 0")
    private double lastCostPriceInventory;
    @Column(columnDefinition="DOUBLE DEFAULT 0")
    private double manualCostPrice;


    private  double weight;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean withTax= true;

    private int withPos;
    private int withPointSystem;

    private String offerId;

    @Column( columnDefinition = "VARCHAR(255) default 'ILS'")
    private String currencyType;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @JsonIgnore
    private Timestamp updatedAt;
    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean hide;

    @Column(columnDefinition="BIT(1) DEFAULT 0")
    private boolean  withSerialNumber;


    public enum ProductStatus{
        ACTIVE("ACTIVE"),PUBLISHED("published "),DRAFT("Draft"),
        PENDING("pending"),PRIVATE("private"),DELETED("deleted"),OUT_OF_STOCKS("out_of_stocks");

        private final String status;

        ProductStatus(String status)
        {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }
    public enum ProductUnit{
        QUANTITY("quantity"),WEIGHT("weight"),
        LENGTH("length"),BARCODEWITHWEIGHT("BARCODEWITHWEIGHT")

        ,BARCODEWITHPRICE("BARCODEWITHPRICE");


        private final String unit;

        ProductUnit(String unit)
        {
            this.unit = unit;
        }

        public String getUnit() {
            return this.unit;
        }
    }

    public Product(ProductStatus status, String displayName, Timestamp createdAt, String productId, long deviceId, @NotBlank String productCode, @NotBlank String barCode, @NotNull String sku, String description, double priceWithTax, double costPrice, long categoryId) {
        this.status=status;
       this.displayName=displayName;
        this.createdAt=createdAt;
        this.productId = productId;

        this.deviceId = deviceId;
        this.productCode = productCode;
        this.barCode = barCode;
        this.sku = sku;
        this.description = description;
        this.priceWithTax = priceWithTax;
        this.costPrice = costPrice;
        this.categoryId = categoryId;
    }
}



