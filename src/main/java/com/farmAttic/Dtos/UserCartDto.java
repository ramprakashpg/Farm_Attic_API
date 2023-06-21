package com.farmAttic.Dtos;

import com.farmAttic.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCartDto {
    private User userInfo;
    private UUID cartId;
}
