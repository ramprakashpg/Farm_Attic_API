package com.farmAttic.controllers;

import com.farmAttic.Dtos.ProductCategory;
import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.models.User;
import com.farmAttic.services.ProductService;
import com.farmAttic.services.UserAuthService;
import io.micronaut.http.HttpResponse;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    private final Authentication authentication = Mockito.mock(Authentication.class);

    private final ProductService productService = mock(ProductService.class);

    private final UserAuthService userAuthService=mock(UserAuthService.class);
    ProductController productController = new ProductController(productService);

    ProductDto product = new ProductDto();

    User user =new User();

    @BeforeEach
    public void beforeEach() {
        List<byte[]> productImageDtoList = new ArrayList<>();
        product.setProductDescription("description");
        product.setProductName("productName");
        product.setPricePerUnit(13);
        product.setQuantity(13);
        product.setProductCategory(ProductCategory.FRUITS);
        UUID uuid = UUID.randomUUID();
//        product.setUserId(uuid);
        byte[] byteArray = new byte[36];
        productImageDtoList.add(byteArray);
        product.setImageList(productImageDtoList);

        user.setUserId(uuid);
        user.setEmail("smssaddepalli@gmail.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");
    }

    @Test
    void shouldSaveProduct() {


        when(productService.saveProductInformation(product)).thenReturn(product);

        HttpResponse<ProductDto> actualResponse = productController.saveProduct(product, authentication);

        assertEquals(HttpResponse.created(actualResponse).getStatus(), actualResponse.getStatus());

    }

    @Test
    void shouldGetAllProducts() {
        List<ProductDto> productsResponseList = new ArrayList<>();
        productsResponseList.add(product);

        when(productService.getProducts()).thenReturn(productsResponseList);

        HttpResponse<List<ProductDto>> actualResponse = productController.getAllProducts(authentication);

        assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());
        assertEquals(1, Objects.requireNonNull(actualResponse.body()).size());
    }

    @Test
    void shouldGetAllProductsOfUser() {
        List<ProductDto> productsResponseList = new ArrayList<>();
        productsResponseList.add(product);
        UUID uuid = UUID.randomUUID();

        when(productService.getProducts()).thenReturn(productsResponseList);

        HttpResponse<List<ProductDto>> actualResponse = productController.getUserProducts(uuid,authentication);

        assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());
        assertEquals(0, Objects.requireNonNull(actualResponse.body()).size());
    }

    @Test
    void shouldUpdateProduct() {
        UUID uuid = UUID.randomUUID();

        when(productService.updateProduct(uuid, product)).thenReturn(product);
//        when(userAuthService.getUser(product.getUserId())).thenReturn(user);

        HttpResponse<ProductDto> actualResponse = productController.updateProduct(uuid, product,authentication);

        assertEquals(HttpResponse.ok().getStatus(),actualResponse.getStatus());
    }

}
