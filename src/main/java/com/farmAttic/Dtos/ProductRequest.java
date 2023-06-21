package com.farmAttic.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private UUID userId;
    private String productName;
    private String productDescription;
    private Integer quantity;
    private Integer price;
    private byte[] imageData;
}
