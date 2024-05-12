package com.farmAttic.Dtos;

import com.farmAttic.models.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Product product;
    private Integer quantity;
    private Integer price;
}
