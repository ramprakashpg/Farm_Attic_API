package com.farmAttic.controllers;

import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.services.ProductService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@io.micronaut.http.annotation.Controller("v1/product")
@Introspected
@Transactional
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Post(produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<ProductDto> saveProduct( @Valid @Body ProductDto productRequest, Authentication authentication){
        LOGGER.info("{} : Save Product",authentication.getName());
        ProductDto productResponse= productService.saveProductInformation(productRequest);
        return HttpResponse.created(productResponse);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<List<ProductDto>> getAllProducts(Authentication authentication){
        LOGGER.info("Get all product details: {}",authentication.getName());
        List<ProductDto> productsResponseList=productService.getProducts();
        return HttpResponse.ok(productsResponseList);
    }

    @Get(value = "/{userId}",produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<List<ProductDto>> getUserProducts(@PathVariable UUID userId,Authentication authentication){
        LOGGER.info("Get Products for User id:{} - {}",userId ,authentication.getName());
        List<ProductDto> productsList = productService.getUserProducts(userId);
        return HttpResponse.ok(productsList);
    }

    @Put(value="/{productId}",produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<ProductDto> updateProduct(@PathVariable UUID productId, @Valid @Body ProductDto productRequest, Authentication authentication){
        LOGGER.info("update product :{} by {}",productId,authentication.getName());
        ProductDto productResponse = productService.updateProduct(productId,productRequest);
        return HttpResponse.ok(productResponse);
    }

}
