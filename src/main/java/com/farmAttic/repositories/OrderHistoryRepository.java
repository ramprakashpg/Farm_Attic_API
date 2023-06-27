package com.farmAttic.repositories;

import com.farmAttic.models.OrderHistory;
import com.farmAttic.models.OrderHistoryId;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface OrderHistoryRepository extends CrudRepository<OrderHistory, OrderHistoryId> {
}
