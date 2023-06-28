package com.farmAttic.Dtos;

import com.farmAttic.models.Cart;
import com.farmAttic.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCartResponse {
    private User user;
    private Cart cart;
    private List<CartResponse> userProduct;
}
