package com.farmAttic.controllers;

import com.farmAttic.Dtos.CartResponse;
import com.farmAttic.Dtos.CartUpdateRequest;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.Dtos.UserCartResponse;
import com.farmAttic.services.CartService;
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

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller("v1/cart")
@Introspected
@Transactional
@AllArgsConstructor
public class CartController {
    private CartService cartService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Get(value = "user/{userId}", produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<UserCartResponse> getUserCartDetails(Authentication authentication, @PathVariable("userId") UUID userId) {
        LOGGER.info("Get Cart info for user: {}", authentication.getName());
        UserCartResponse cartResponse = cartService.getUserCartDetails(userId);
        return HttpResponse.ok(cartResponse);
    }

    //Can remove this for now.
    @Patch(value = "/{cartId}/product/{productId}", produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<CartResponse> updateCart(Authentication authentication, @PathVariable("cartId") UUID cartId, @PathVariable("productId") UUID productId, @Body CartUpdateRequest updateRequest) throws Throwable {
        LOGGER.info("Get Cart info for user: {}", authentication.getName());
        CartResponse response = cartService.updateCart(cartId, productId, updateRequest.getQuantity());
        return HttpResponse.ok(response);
    }

    @Post(value = "/product", produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<ProductRequest> addProductToCart(Authentication authentication, @Valid @Body ProductRequest productRequest) {
        LOGGER.info("product :{} added to cart by {}", productRequest.getProductId(), authentication.getName());
        String loggedInUser = authentication.getName();
        try {
            ProductRequest productResponse = cartService.saveToCart(productRequest, loggedInUser);
            return HttpResponse.created(productResponse);
        }catch(NoSuchElementException e){
            return HttpResponse.badRequest(new ProductRequest());
        }
    }
    @Delete(value = "/{cartId}/product/{productId}", produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse deleteProductFromCart(@PathVariable("cartId") UUID cartId, @PathVariable("productId") UUID productId, Authentication authentication) throws Throwable {
        cartService.deleteProductFromCart(cartId, productId);
        return HttpResponse.ok();
    }

}
