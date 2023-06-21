package com.farmAttic.controllers;

import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.Product;
import com.farmAttic.services.ProductImageService;
import com.farmAttic.services.ProductService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

@io.micronaut.http.annotation.Controller("v1/")
@Introspected
@Transactional
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductImageService productImageService;
    private final ProductService productService;

    public ProductController(ProductImageService productImageService, ProductService productService) {
        this.productImageService = productImageService;
        this.productService = productService;
    }


    @Post(value ="/products",produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<ProductRequest> saveProduct(@Body ProductRequest productRequest, Authentication authentication){
        LOGGER.info("{} : Save Product",authentication.getName());
        Product product= productService.saveProductInformation(productRequest);
        ProductRequest productResponse = productImageService.saveProductImage(productRequest,product);
        return HttpResponse.ok(productResponse);
    }

}
