package com.farmAttic.Dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequest {
    private Integer quantity;
}
