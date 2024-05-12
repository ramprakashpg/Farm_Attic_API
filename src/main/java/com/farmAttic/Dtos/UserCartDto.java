package com.farmAttic.Dtos;

import com.farmAttic.models.User;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCartDto {
    private User userInfo;
    private UUID cartId;
}
