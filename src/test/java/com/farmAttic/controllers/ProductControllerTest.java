package com.farmAttic.controllers;

import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.models.User;
import com.farmAttic.services.ProductService;
import com.farmAttic.services.UserAuthService;
import io.micronaut.http.HttpResponse;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    private final Authentication authentication = Mockito.mock(Authentication.class);

    private final ProductService productService = mock(ProductService.class);

    private final UserAuthService userAuthService=mock(UserAuthService.class);
    ProductController productController = new ProductController(productService);

    ProductDto productRequest = new ProductDto();

    User user =new User();

    @BeforeEach
    public void beforeEach() {
        List<byte[]> productImageDtoList = new ArrayList<>();
        productRequest.setProductDescription("description");
        productRequest.setProductName("productName");
        productRequest.setPrice(13);
        productRequest.setQuantity(13);
        UUID uuid = UUID.randomUUID();
        productRequest.setUserId(uuid);
        byte[] byteArray = new byte[36];
        productImageDtoList.add(byteArray);
        productRequest.setImageList(productImageDtoList);

        user.setUserId(uuid);
        user.setEmail("smssaddepalli@gmail.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");
    }

    @Test
    void shouldSaveProduct() {


        when(productService.saveProductInformation(productRequest)).thenReturn(productRequest);

        HttpResponse<ProductDto> actualResponse = productController.saveProduct(productRequest, authentication);

        Assertions.assertEquals(HttpResponse.created(actualResponse).getStatus(), actualResponse.getStatus());

    }

    @Test
    void shouldGetAllProducts() {
        List<ProductDto> productsResponseList = new ArrayList<>();
        productsResponseList.add(productRequest);

        when(productService.getProducts()).thenReturn(productsResponseList);

        HttpResponse<List<ProductDto>> actualResponse = productController.getAllProducts(authentication);

        Assertions.assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());
        Assertions.assertEquals(1, Objects.requireNonNull(actualResponse.body()).size());
    }

    @Test
    void shouldGetAllProductsOfUser() {
        List<ProductDto> productsResponseList = new ArrayList<>();
        productsResponseList.add(productRequest);
        UUID uuid = UUID.randomUUID();

        when(productService.getProducts()).thenReturn(productsResponseList);

        HttpResponse<List<ProductDto>> actualResponse = productController.getUserProducts(uuid,authentication);

        Assertions.assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());
        Assertions.assertEquals(0, Objects.requireNonNull(actualResponse.body()).size());
    }

    @Test
    void shouldUpdateProduct() {
        UUID uuid = UUID.randomUUID();

        when(productService.updateProduct(uuid,productRequest)).thenReturn(productRequest);
        when(userAuthService.getUser(productRequest.getUserId())).thenReturn(user);

        HttpResponse<ProductDto> actualResponse = productController.updateProduct(uuid,productRequest,authentication);

        Assertions.assertEquals(HttpResponse.ok().getStatus(),actualResponse.getStatus());
    }
}
