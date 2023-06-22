package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import javax.persistence.*;
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

    @Column(name="quantity",nullable = false)
    private int quantity;

    @Column(name="price",nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;
}
