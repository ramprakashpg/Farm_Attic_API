package com.farmAttic.models;

import com.farmAttic.Dtos.ProductCategory;
import io.micronaut.core.annotation.Introspected;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Introspected
@Entity
@Table(name = "tbm_product")
public class Product {

    @Id
    @Column(name="product_id",unique = true,nullable = false, length = 36)
    @GeneratedValue
    private UUID productId;

    @Column(name="product_name",nullable = false)
    private String productName;

    @Column(name=" product_description",nullable = false)
    private String productDescription;

    @Column(name = "category", nullable = false)
    private ProductCategory productCategory;

    @Column(name="quantity",nullable = false)
    private int quantity;

    @Column(name="price_per_unit",nullable = false)
    private int pricePerUnit;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;

    @Column(name = "unit", nullable = false)
    private String unit;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;
}
