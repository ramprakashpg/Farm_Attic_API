package com.farmAttic.Dtos;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@Introspected
@AllArgsConstructor
public class ProductRequest {
    private UUID productId;
    private Integer quantity;
    private Integer price;
}
