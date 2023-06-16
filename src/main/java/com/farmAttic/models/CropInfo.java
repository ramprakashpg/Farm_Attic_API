package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;

import javax.persistence.*;

@Introspected
@Entity
@Table(name = "tbl_product")
public class CropInfo {

    @Id
    @Column(name="product_id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cropId;
}
