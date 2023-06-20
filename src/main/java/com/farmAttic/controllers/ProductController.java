package com.farmAttic.controllers;

import com.farmAttic.Dtos.ProductDetails;
import com.farmAttic.models.ProductInfo;
import com.farmAttic.services.ProductImageService;
import com.farmAttic.services.ProductInformationService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

import javax.transaction.Transactional;

@io.micronaut.http.annotation.Controller("v1/")
@Introspected
@Transactional
public class ProductController {

    private final ProductImageService productImageService;
    private final ProductInformationService productInformationService;

    public ProductController(ProductImageService productImageService, ProductInformationService productInformationService) {
        this.productImageService = productImageService;
        this.productInformationService = productInformationService;
    }


    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse check(Authentication authentication){
        return HttpResponse.ok();
    }


    @Post(value ="product/image")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public MutableHttpResponse<ProductDetails> saveProductDetails(@Body ProductDetails productRequest, Authentication authentication){
        ProductInfo productInformationResponse=productInformationService.saveProductInformation(productRequest);
        ProductDetails productDetailsResponse = productImageService.saveProductImage(productRequest,productInformationResponse);
        return HttpResponse.ok(productDetailsResponse);
    }

}
