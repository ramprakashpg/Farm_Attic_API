package com.farmAttic.controllers;

import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.Dtos.ProductImageDto;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.services.ProductImageService;
import com.farmAttic.services.ProductService;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import io.micronaut.http.HttpResponse;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class ProductControllerTest {

    private final Authentication authentication = Mockito.mock(Authentication.class);

    private final ProductService productService=Mockito.mock(ProductService.class);

    private final ProductImageService productImageService =Mockito.mock(ProductImageService.class);

    ProductController productController=new ProductController(productImageService,productService);


    @Test
    void shouldSaveProduct() {
        List<ProductImageDto> productImageDtoList=new ArrayList<>();
        ProductDto productRequest=new ProductDto();
        productRequest.setProductDescription("description");
        productRequest.setProductName("productName");
        productRequest.setPrice(13);
        productRequest.setQuantity(13);
        UUID uuid = UUID.randomUUID();
        productRequest.setUserId(uuid);
        byte[] byteArray = new byte[36];
        ProductImageDto  productImageDto=new ProductImageDto();
        productImageDto.setImageData(byteArray);
        productImageDtoList.add(productImageDto);
        productRequest.setImageList(productImageDtoList);

        User user=new User(uuid,"sahiti@gmail.com","Sahiti","Priya");
        Product product=Product.builder().productName("name").productDescription("dfsf").price(123).quantity(134).user(user).build();

        when(productService.saveProductInformation(productRequest)).thenReturn(product);
        when(productImageService.saveProductImage(productRequest,product)).thenReturn(productRequest);

        HttpResponse<ProductDto> actualResponse=productController.saveProduct(productRequest,authentication);

        Assertions.assertEquals(HttpResponse.ok().getStatus(),actualResponse.getStatus());


    }
}
