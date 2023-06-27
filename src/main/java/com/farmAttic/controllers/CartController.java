package com.farmAttic.controllers;

import com.farmAttic.Dtos.CartResponse;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.Dtos.UserCartResponse;
import com.farmAttic.models.User;
import com.farmAttic.services.CartService;
import com.farmAttic.services.UserAuthService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@Controller("v1/cart")
@Introspected
@Transactional
@AllArgsConstructor
public class CartController {
    private CartService cartService;
    private UserAuthService userAuthService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Get(value = "/{cartId}", produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<UserCartResponse> addProductToCart(Authentication authentication, @PathVariable("cartId") UUID cartId) {
        LOGGER.info("Get Cart info for user: {}", authentication.getName());
        UserCartResponse cartResponse = cartService.getUserCartDetails(cartId);
        return HttpResponse.ok(cartResponse);
    }

    @Patch(value = "/{cartId}/product/{productId}", produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<CartResponse> updateCart(Authentication authentication, @PathVariable("cartId") UUID cartId, @PathVariable("productId") UUID productId, @Body @Nullable Integer quantity) throws Throwable {
        LOGGER.info("Get Cart info for user: {}", authentication.getName());
        CartResponse response = cartService.updateCart(cartId, productId, quantity);
        return HttpResponse.ok(response);
    }

    @Post(value = "/{productId}", produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<ProductRequest> addProductToCart(Authentication authentication, @Valid @Body ProductRequest productRequest) {
        LOGGER.info("product :{} added to cart by {}", productRequest.getProductId(), authentication.getName());
        String loggedInUser = authentication.getName();
        ProductRequest productResponse = cartService.saveToCart(productRequest, loggedInUser);
        return HttpResponse.created(productResponse);
    }

}
