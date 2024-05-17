package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import jakarta.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Introspected
@Entity
@Table(name="tbl_cart")
public class Cart {
    @Id
    @Column(name = "cart_id", unique = true, nullable = false, length = 36)
    @GeneratedValue
    private UUID cartId;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User userInfo;
}
