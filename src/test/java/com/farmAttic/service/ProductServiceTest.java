package com.farmAttic.service;


import com.farmAttic.Dtos.ProductCategory;
import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.repositories.ProductRepository;
import com.farmAttic.services.ProductImageService;
import com.farmAttic.services.ProductService;
import com.farmAttic.services.UserAuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private final ProductImageService productImageService = mock(ProductImageService.class);

    private final UserAuthService userAuthService = mock(UserAuthService.class);

    private ProductRepository productRepository;

    private ProductService productService;

    UUID uuid = UUID.randomUUID();

    ProductDto productRequest = ProductDto.builder()
            .productDescription("description")
            .productName("productname")
            .productCategory(ProductCategory.FRUITS)
            .pricePerUnit(13)
            .quantity(13)
//            .userId(uuid)
            .build();
    User user = User.builder()
            .userId(uuid)
            .email("smssaddepalli@gmail.com")
            .firstName("Sahiti")
            .lastName("Priya")
            .build();

    Product product = new Product();


    @BeforeEach
    void beforeEach() {

        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository, productImageService, userAuthService);

        List<byte[]> productImageDtoList = new ArrayList<>();

        byte[] byteArray = new byte[36];
        productImageDtoList.add(byteArray);
        productRequest.setImageList(productImageDtoList);


        product = Product.builder()
                .productId(uuid)
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .quantity(productRequest.getQuantity())
                .pricePerUnit(productRequest.getPricePerUnit())
                .user(user)
                .build();

    }


    @Test
    void shouldSaveProductDetails() {

//        when(userAuthService.getUser(productRequest.getUserId())).thenReturn(user);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.saveProductInformation(productRequest);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldGetAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product = Product.builder().productId(uuid).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).pricePerUnit(productRequest.getPricePerUnit()).user(user).build();
        products.add(product);

        when(productRepository.findAll()).thenReturn(products);

        productService.getProducts();

        verify(productRepository).findAll();
    }

    @Test
    void shouldGetUserProducts() {
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepository.findAll()).thenReturn(products);

        List<ProductDto> productResponse = productService.getUserProducts(product.getUser().getUserId());

        Assertions.assertEquals(1, productResponse.size());

    }

    @Test
    void shouldUpdateProduct() {

        UUID productId = UUID.randomUUID();
        product.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));
//        when(userAuthService.getUser(productRequest.getUserId())).thenReturn(user);
        when(productRepository.update(product)).thenReturn(product);

        productService.updateProduct(productId, productRequest);

        verify(productRepository).update(any(Product.class));
    }

    @Test
    void shouldUpdateProductWithEmptyImageList() {

        UUID productId = UUID.randomUUID();
        product.setProductId(productId);
        productRequest.setImageList(new ArrayList<>());

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));
//        when(userAuthService.getUser(productRequest.getUserId())).thenReturn(user);
        when(productRepository.update(product)).thenReturn(product);

        productService.updateProduct(productId, productRequest);

        verify(productRepository).update(any(Product.class));
    }
}

