package com.farmAttic.Dtos;

import com.farmAttic.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Product product;
    private Integer quantity;
    private Integer price;
}
