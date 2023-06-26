package com.farmAttic.Dtos;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
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
    private Integer pricePerUnit;
    @NotNull
    private List<byte []> imageList;

    @NotNull
    private String unit;
    @NotNull
    private Date expiryDate;
}
