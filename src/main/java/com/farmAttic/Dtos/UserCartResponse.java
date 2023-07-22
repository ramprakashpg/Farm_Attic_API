package com.farmAttic.Dtos;

import com.farmAttic.models.Cart;
import com.farmAttic.models.User;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCartResponse {
    private User user;
    private Cart cart;
    private List<CartResponse> userProduct;
}
