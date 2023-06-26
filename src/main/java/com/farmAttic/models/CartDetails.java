package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Introspected
@Table(name="tbl_cart_details")
public class CartDetails implements Serializable {

    @EmbeddedId
    @AttributeOverride(name="cart",column = @Column(name="cart_id"))
    @AttributeOverride(name="product",column=@Column(name="product_id"))
    private CartDetailsId cartDetailsId;

    @Column(name="quantity",nullable = false)
    private Integer quantity;
}
