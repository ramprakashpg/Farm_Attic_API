package com.farmAttic.models;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderHistoryId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDetails order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductInfo product;

}