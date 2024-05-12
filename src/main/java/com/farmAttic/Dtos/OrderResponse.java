package com.farmAttic.Dtos;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private UUID orderId;
    private String status;
    private Integer totalPrice;
    private List<CartResponse> cartResponseList;
}
