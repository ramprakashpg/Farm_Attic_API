package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Introspected
@Entity
@Table(name="tbl_cart")
public class Cart {
    @Id
    @Column(name = "cart_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID cart_id;

    @ManyToOne
    @MapsId
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User userInfo;
}
