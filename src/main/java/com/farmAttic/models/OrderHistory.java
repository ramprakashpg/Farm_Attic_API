package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Introspected
@Entity
@Table(name = "tbl_order_history")
public class OrderHistory {

    @EmbeddedId
    @AttributeOverride(name = "order", column = @Column(name = "order_id"))
    @AttributeOverride(name = "product", column = @Column(name = "product_id"))
    private OrderHistoryId historyId;


    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Integer price;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;


}
