package com.farmAttic.service;


import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.repositories.ProductRepository;
import com.farmAttic.services.CartService;
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
    ProductDto productRequest = new ProductDto();
    UUID uuid = UUID.randomUUID();

    User user = new User();
    CartService cartService = mock(CartService.class);

    Product product=new Product();


    @BeforeEach
    void beforeEach() {

        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository, productImageService, userAuthService, cartService);

        List<byte[]> productImageDtoList = new ArrayList<>();
        productRequest.setProductDescription("description");
        productRequest.setProductName("productName");
        productRequest.setPrice(13);
        productRequest.setQuantity(13);
        productRequest.setUserId(uuid);
        byte[] byteArray = new byte[36];
        productImageDtoList.add(byteArray);
        productRequest.setImageList(productImageDtoList);

        user.setUserId(uuid);
        user.setEmail("smssaddepalli@gmail.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");

        product = Product.builder().productId(uuid).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).price(productRequest.getPrice()).user(user).build();

    }


    @Test
    void shouldSaveProductDetails() {

        when(userAuthService.getUser(productRequest.getUserId())).thenReturn(user);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.saveProductInformation(productRequest);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldGetAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product = Product.builder().productId(uuid).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).price(productRequest.getPrice()).user(user).build();
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
    void addProductToCart() {
        ProductRequest productRequest1 = new ProductRequest();
        productRequest1.setProductId(UUID.randomUUID());
        productRequest1.setQuantity(2);
        Product product = Product.builder().productId(uuid).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).price(productRequest.getPrice()).user(user).build();
        User currentUser = new User();
        currentUser.setEmail("dummy@gmail.com");
        currentUser.setFirstName("Dummy");
        currentUser.setLastName("Name");

        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(product));
        when(userAuthService.getCurrentUser("dummy@gmail.com")).thenReturn(user);
        when(cartService.addToCart(any(Product.class),any(User.class),any(ProductRequest.class))).thenReturn(productRequest1);

        productService.saveToCart(productRequest1,"dummy@gmail.com");

        verify(cartService).addToCart(any(Product.class),any(User.class),any(ProductRequest.class));


    }

    @Test
    void  shouldUpdateProduct() {

        UUID productId =UUID.randomUUID();
        product.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));
        when(userAuthService.getUser(productRequest.getUserId())).thenReturn(user);
        when(productRepository.update(product)).thenReturn(product);

        productService.updateProduct(productId,productRequest);

        verify(productRepository).update(product);
    }

    @Test
    void  shouldUpdateProductWithEmptyImageList() {

        UUID productId =UUID.randomUUID();
        product.setProductId(productId);
        productRequest.setImageList(new ArrayList<>());

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));
        when(userAuthService.getUser(productRequest.getUserId())).thenReturn(user);
        when(productRepository.update(product)).thenReturn(product);

        productService.updateProduct(productId,productRequest);

        verify(productRepository).update(product);
    }
}

