package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Introspected
@Entity
@Table(name = "tbm_product")
public class ProductInfo {

    @Id
    @Column(name="product_id",unique = true,nullable = false, length = 36)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID productId;

    @Column(name="product_name",nullable = false)
    private String productName;

    @Column(name=" product_description",nullable = false)
    private String productDescription;

    @Column(name="quantity",nullable = false)
    private int quantity;

    @Column(name="price",nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;
}
