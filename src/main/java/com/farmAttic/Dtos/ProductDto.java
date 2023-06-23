package com.farmAttic.Dtos;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Introspected
@AllArgsConstructor
public class ProductDto {
    private UUID productId;
    @NotNull
    private UUID userId;
    @NotNull
    private String productName;
    @NotNull
    private String productDescription;
    @NotNull
    private Integer quantity;
    @NotNull
    private Integer price;
    @NotNull
    private List<byte []> imageList;
}
