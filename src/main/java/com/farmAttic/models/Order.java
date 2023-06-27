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
@Table(name="tbl_order_details")
public class Order {
    @Id
    @Column(name = "order_id", unique = true, nullable = false, length = 36)
    @GeneratedValue
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;

    @Column(name = "status")
    private String status;


}
