package com.farmAttic.Dtos;

import com.farmAttic.models.Cart;
import com.farmAttic.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private Cart cart;
    private Product product;
    private Integer quantity;
    private Integer price;
    private Date createdAt;
    private Date updatedAt;

    public CartDto(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

}
