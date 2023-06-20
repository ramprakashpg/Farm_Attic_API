package com.farmAttic.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInformationDto {
    private String productName;
    private String productDescription;
    private Integer quantity;
}
