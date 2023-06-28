package com.farmAttic.services;

import com.farmAttic.Dtos.CartResponse;
import com.farmAttic.Dtos.OrderResponse;
import com.farmAttic.models.*;
import com.farmAttic.repositories.OrderRepository;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;
    private final OrderHistoryService orderHistoryService;

    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, ProductService productService, OrderHistoryService orderHistoryService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderHistoryService = orderHistoryService;
        this.cartService = cartService;
    }

    public OrderResponse placeOrder(UUID userId) {
        Order order=new Order();
        User user=productService.getUser(userId);
        order.setUser(user);
        order.setStatus("Ordered");
        orderRepository.save(order);
        return placeOrderHistory(order);
    }

    private OrderResponse placeOrderHistory(Order order) {
        Cart cart=cartService.getUserCart(order.getUser());
        List<CartDetails> cartDetailsList=cartService.getDetails(cart);
       cartDetailsList.forEach(cartDetail->{
           OrderHistory orderHistory = getOrderHistory(order, cartDetail);
           orderHistoryService.save(orderHistory);
           updateProduct(orderHistory);
           clearCart(cartDetail);
        });
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        return orderResponse;
    }

    private void clearCart(CartDetails cartDetail) {
        cartService.clearCart(cartDetail);
    }

    private void updateProduct(OrderHistory orderHistory) {
        Product product=productService.getProduct(orderHistory.getHistoryId().getProduct().getProductId());
        int quantity= orderHistory.getHistoryId().getProduct().getQuantity()-orderHistory.getQuantity();
        product.setQuantity(quantity);
        productService.updateProduct(product);
    }

    private OrderHistory getOrderHistory(Order order, CartDetails cartDetail) {
        OrderHistoryId orderHistoryId=new OrderHistoryId();
        orderHistoryId.setOrder(order);
        orderHistoryId.setProduct(cartDetail.getCartDetailsId().getProduct());
        OrderHistory orderHistory=new OrderHistory();
        orderHistory.setHistoryId(orderHistoryId);
        orderHistory.setQuantity(cartDetail.getQuantity());
        orderHistory.setPrice(cartDetail.getPrice());
        return orderHistory;
    }

    private OrderResponse getResponse(List<OrderHistory> orderHistoryList, Order order) {
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        List<CartResponse> cartResponseList=new ArrayList<>();
        int totalPrice = 0;
        for(OrderHistory orderHistory:orderHistoryList){
            CartResponse cartResponse=new CartResponse();
            cartResponse.setProduct(orderHistory.getHistoryId().getProduct());
            cartResponse.setQuantity(orderHistory.getQuantity());
            cartResponse.setPrice(orderHistory.getPrice());
            totalPrice += cartResponse.getPrice();
            cartResponseList.add(cartResponse);
        }
        orderResponse.setTotalPrice(totalPrice);
        orderResponse.setStatus(order.getStatus());
        orderResponse.setCartResponseList(cartResponseList);
        return orderResponse;
    }

    public List<OrderResponse> getOrderDetails(UUID userId) {
        List<Order> orders=orderRepository.findByUser(userId);
        List<OrderResponse> orderResponses=new ArrayList<>();
        orders.forEach(order -> {
            List<OrderHistory> orderHistoryList=orderHistoryService.getDetails(order);
            OrderResponse orderResponse=getResponse(orderHistoryList,order);
            orderResponses.add(orderResponse);
        });
        return orderResponses;
    }

}
