package com.farmAttic.Dtos;

import com.farmAttic.models.Cart;
import com.farmAttic.models.Product;
import lombok.*;

import java.util.Date;

@Data
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
