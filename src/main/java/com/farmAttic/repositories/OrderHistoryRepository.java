package com.farmAttic.repositories;

import com.farmAttic.models.Order;
import com.farmAttic.models.OrderHistory;
import com.farmAttic.models.OrderHistoryId;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends CrudRepository<OrderHistory, OrderHistoryId> {

    @Executable
    @Query("select oh from OrderHistory oh where oh.historyId.order=:order")
    List<OrderHistory> findByOrder(Order order);
}
