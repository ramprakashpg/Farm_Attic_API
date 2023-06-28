package com.farmAttic.controllers;

import com.farmAttic.Dtos.OrderResponse;
import com.farmAttic.services.OrderService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Controller("/v1/order")
@Introspected
@Transactional
public class OrderController {

    private final OrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Post(value="/{userId}",produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<OrderResponse> placeOrder(@PathVariable UUID userId, Authentication authentication){
        LOGGER.info("placing order for {} by {}",userId,authentication.getName());
        OrderResponse orderResponse=orderService.placeOrder(userId);
        return HttpResponse.ok(orderResponse);
    }

    @Get(value = "/{userId}",produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<List<OrderResponse>> getOrderDetails(@PathVariable UUID userId, Authentication authentication){
        LOGGER.info("getting order details for {} by {}",userId,authentication.getName());
        List<OrderResponse> orderResponse=orderService.getOrderDetails(userId);
        return HttpResponse.ok(orderResponse);
    }
}
