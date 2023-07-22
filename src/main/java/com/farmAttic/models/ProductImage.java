package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Introspected
@Entity
@Table(name="tbl_product_image")
public class ProductImage {

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
