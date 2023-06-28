package com.farmAttic.Dtos;

import com.farmAttic.models.CartDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private UUID orderId;
    private String status;
    private Integer totalPrice;
    private List<CartResponse> cartResponseList;
}
