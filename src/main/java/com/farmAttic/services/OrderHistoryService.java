package com.farmAttic.services;

import com.farmAttic.models.Order;
import com.farmAttic.models.OrderHistory;
import com.farmAttic.repositories.OrderHistoryRepository;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;

    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    public OrderHistory save(OrderHistory orderHistory) {
        return orderHistoryRepository.save(orderHistory);

    }

    public List<OrderHistory> getDetails(Order order) {
        return orderHistoryRepository.findByOrder(order);
    }
}
