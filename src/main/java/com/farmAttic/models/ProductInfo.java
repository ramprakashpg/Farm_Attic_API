package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Introspected
@Entity
@Table(name = "tbl_product")
public class ProductInfo {

    @Id
    @Column(name="product_id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name="product_name")
    private String productName;

    @Column(name=" product_description")
    private String productDescription;

    @Column(name="quantity")
    private int quantity;

}
