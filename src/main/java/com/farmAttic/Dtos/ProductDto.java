package com.farmAttic.Dtos;

import lombok.*;
import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
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
    private ProductCategory productCategory;
    @NotNull
    private Integer pricePerUnit;
    @NotNull
    private List<byte []> imageList;

    @NotNull
    private String unit;
    @NotNull
    private Date expiryDate;
}
