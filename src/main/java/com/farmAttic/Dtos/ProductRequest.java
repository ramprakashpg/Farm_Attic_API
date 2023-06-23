package com.farmAttic.Dtos;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Introspected
@AllArgsConstructor
public class ProductRequest {
    private UUID productId;
    private Integer quantity;
}
