package com.farmAttic.controllers;

import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.services.ProductService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.List;

@io.micronaut.http.annotation.Controller("v1/")
@Introspected
@Transactional
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Post(value ="/products",produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<ProductDto> saveProduct(@Body ProductDto productRequest, Authentication authentication){
        LOGGER.info("{} : Save Product",authentication.getName());
        ProductDto productResponse= productService.saveProductInformation(productRequest);
        return HttpResponse.ok(productResponse);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<List<ProductDto>> getProducts(){
        LOGGER.info("Get all the products");
        List<ProductDto> productResponseList=productService.getProducts();
        return HttpResponse.ok(productResponseList);
    }


}
