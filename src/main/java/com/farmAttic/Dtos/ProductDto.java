package com.farmAttic.Dtos;

import com.farmAttic.models.User;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
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
    private User user;
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
    private List<byte[]> imageList;

    @NotNull
    private String unit;
    @NotNull
    private Date expiryDate;
}
