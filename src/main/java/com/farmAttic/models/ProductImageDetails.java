package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Introspected
@Entity
@Table(name="tbl_product_image")
public class ProductImageDetails {

    @Id
    @Column(name="image_id",unique = true,nullable = false, length = 36)
    @GeneratedValue
    private UUID imageId;

    @Column(name="image_data")
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name="product",referencedColumnName = "product_id")
    private Product product;
}
