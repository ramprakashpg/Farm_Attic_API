package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Introspected
@Entity
@Table(name="tbl_product_image")
public class ProductImageDetails {

    @Id
    @Column(name="image_id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @Column(name="image_data")
    private byte[] imageData;

    @ManyToOne
    @MapsId
    @JoinColumn(name="product",referencedColumnName = "product_id")
    private ProductInfo productInfo;
}
