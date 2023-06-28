package com.farmAttic.repositories;

import com.farmAttic.models.Order;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {

    @Executable
    @Query("select order from Order order where order.user.userId=:userId")
    List<Order> findByUser(UUID userId);
}
